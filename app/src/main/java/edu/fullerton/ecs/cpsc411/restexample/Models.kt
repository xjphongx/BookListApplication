package edu.fullerton.ecs.cpsc411.restexample

/*
    Contain the book objects
 */
class BookList (val books: Array<Book>) //BookList is a list of Book objects
class Book (val id:Int,
            val published:Int,
            val author:String,
            val title:String,
            val first_sentence:String
)
