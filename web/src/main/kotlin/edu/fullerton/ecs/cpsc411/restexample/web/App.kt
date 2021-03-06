/*
 * This Kotlin source file was originally generated by the Gradle 'init' task.
 *
 * ../gradlew init --type kotlin-application --dsl groovy \
 *     --project-name 'REST Example Web' \
 *     --package edu.fullerton.ecs.cpsc411.restexample.web
 *
 */
package edu.fullerton.ecs.cpsc411.restexample.web

/*
 * Science Fiction Novel API originally from "Creating Web APIs with Python and Flask"
 * <https://programminghistorian.org/en/lessons/creating-apis-with-python-and-flask>.
 *
 */

import java.io.File

import org.jdbi.v3.core.*
import org.jdbi.v3.core.kotlin.*

import org.slf4j.LoggerFactory

import io.javalin.Javalin
import io.javalin.http.*

// Leave this class in place so that src/test/.../web/AppTest.kt will pass.
class App {
    val greeting: String
        get() {
            return """
            <h1>Distant Reading Archive</h1>
            <p>A prototype API for distant reading of science fiction novels.</p>

            """
        }
}

data class BookId(val id: Int)

data class Book(
    val id: Int,
    val published: Int,
    val author: String,
    val title: String,
    val first_sentence: String
)

fun main(args: Array<String>) {
    val jdbi = Jdbi.create("jdbc:hsqldb:mem:web", "SA", "")
    jdbi.installPlugins()

    val script = File("books.sql").readText()

    jdbi.useHandleUnchecked { handle ->
        handle.createScript(script).execute()
    }

    val logger = LoggerFactory.getLogger(jdbi::class.java)
    logger.info("Loaded books.sql")
            
    val app = Javalin.create().start(7000)

    app.get("/") { ctx -> ctx.html(App().greeting) }

    app.get("/api/v1/resources/books/all") { ctx ->
        val books = jdbi.withHandleUnchecked { handle ->
            handle.select("SELECT * FROM books;").mapToMap().list()
        }

        ctx.json(books)
    }

    app.get("/api/v1/resources/books/:id") { ctx ->
        val bookId = ctx.pathParam("id").toInt()

        val book = jdbi.withHandleUnchecked { handle ->
            handle.select("""
                SELECT * FROM books
                WHERE id = ?;
            """, bookId).mapToMap().findOne()
        }

        if (book.isPresent) { 
            ctx.json(book.get())
        } else {
            throw NotFoundResponse()
        }
    }

    app.post("/api/v1/resources/books/") { ctx ->
        val book = ctx.body<Book>()

        try {
            val result = jdbi.withHandleUnchecked { handle ->
                handle.createUpdate("""
                    INSERT INTO books(published, author, title, first_sentence)
                    VALUES(:published, :author, :title, :first_sentence)

                """).bindBean(book).executeAndReturnGeneratedKeys()
                    .mapTo(BookId::class.java).one()
            }

            ctx.redirect("/api/v1/resources/books/${result.id}", 201)
            ctx.json(book.copy(id = result.id))
        } catch (e: JdbiException) {
            ctx.contentType("application/json")
            throw ConflictResponse(e.message!!)
        }
    }

}
