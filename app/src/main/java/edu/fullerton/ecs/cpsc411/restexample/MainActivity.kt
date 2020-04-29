/*
Jimmy Phong
Application 3

I was able to get the recycler view to work.
I got the json object to print with okhttp. Look at log cat to see the system print out.
I was able to get the gson converter to work.
This application only prints out the json objects in the recycler view. It does not look pretty.
 */

package edu.fullerton.ecs.cpsc411.restexample

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adding floating activity button here
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{ //once clicked, create new activty and go to it
            Toast.makeText(this@MainActivity, "Fab is clicked...", Toast.LENGTH_LONG).show()
            createNewActivity()
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        getJsonObject()

    }

    private fun getJsonObject() {
        println("getting json object")

        val url:String = "http://10.0.2.2:7000/api/v1/resources/books/all"
        //http://localhost:7000/api/v1/resources/books/all
        println("this is the url "+ url)
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException?) {
                println("FAILED TO EXECUTE REQUEST")
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)

                //body is now a string
                //next step is to convert wit gson
                val gson = GsonBuilder().create()

                //bookList is now a class with these objects
                val bookList:List<Book> = gson.fromJson(body, Array<Book>::class.java).toList()

                recycler_view.adapter = MainAdapter(bookList)
            }
        })
    }
    private fun createNewActivity(){
        val intent = Intent(this, BookDetailActivity::class.java)
        startActivity(intent)
        println("New activity")

    }
}

