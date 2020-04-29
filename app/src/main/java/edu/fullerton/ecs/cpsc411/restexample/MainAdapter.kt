package edu.fullerton.ecs.cpsc411.restexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book.view.*

class MainAdapter(private val booklist: List<Book>): RecyclerView.Adapter<CustomViewHolder>(){

    //private val bookTitle = listOf("first", "second","third")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bookCell = layoutInflater.inflate(R.layout.book,parent,false)
        return CustomViewHolder(bookCell)
    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    @SuppressLint("SetTextI18n")//needed i guess
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val bookId = booklist.get(position).id
        val bookPublished = booklist.get(position).published
        val bookAuthor = booklist.get(position).author
        val bookTitle = booklist.get(position).title
        val bookFirstSentence = booklist.get(position).first_sentence
        holder.view.textViewBookId.text = bookId.toString()
        holder.view.textViewPublished.text = bookPublished.toString()
        holder.view.textViewAuthor.text = bookAuthor
        holder.view.textViewTitle.text = bookTitle
        holder.view.textViewFirstSentence.text = bookFirstSentence


    }


}
class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view){

}