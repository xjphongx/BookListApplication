package edu.fullerton.ecs.cpsc411.restexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class BookDetailActivity : AppCompatActivity(){
    //variables
    lateinit var textInputYearPublished : TextInputLayout
    lateinit var textInputAuthor : TextInputLayout
    lateinit var textInputBookTitle : TextInputLayout
    lateinit var textInputFirstSentence : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail) //change the layout to its own layout

        textInputYearPublished = findViewById(R.id.text_input_year_published)
        textInputAuthor = findViewById(R.id.text_input_Author)
        textInputBookTitle = findViewById(R.id.text_input_book_title)
        textInputFirstSentence = findViewById(R.id.text_input_first_sentence)

        //user will click on the button
        val button : Button = findViewById(R.id.submitButton)
        button.setOnClickListener{
            confirmInput()
        }
    }

    /*
         functions below check if the inputs are valid
     */
    private fun confirmInput() {
        if(!validateYear() || !validateAuthor() || !validateBookTitle() || !validateFirstSentence()){
            return;//ends
        }
        //output the results and show it on toast
        val input = "Year Published: "+ textInputYearPublished.editText?.text.toString() +
                "\nAuthor: "+ textInputAuthor.editText?.text.toString() +
                "\nBook Title: "+ textInputBookTitle.editText?.text.toString() +
                "\nFirst Sentence: "+ textInputFirstSentence.editText?.text.toString()

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show()

    }
    private fun validateYear(): Boolean {
         val yearInput = textInputYearPublished.editText?.text.toString()
        return if(yearInput.isEmpty()){
            textInputYearPublished.error = "Field cannot be empty"
            false
        }else {
            textInputYearPublished.error = null
            true
        }
    }
    private fun validateAuthor():Boolean {
        val authorInput = textInputAuthor.editText?.text.toString()
        return if(authorInput.isEmpty()){
            textInputAuthor.error = "Field cannot be empty"
            false
        }else {
            textInputAuthor.error = null
            true
        }
    }
    private fun validateBookTitle(): Boolean{
        val bookTitleInput = textInputBookTitle.editText?.text.toString()
        return if(bookTitleInput.isEmpty()){
            textInputBookTitle.error = "Field cannot be empty"
            false
        }else {
            textInputBookTitle.error = null
            true
        }
    }
    private fun validateFirstSentence(): Boolean {
        val firstSentenceInput = textInputFirstSentence.editText?.text.toString()
        return if(firstSentenceInput.isEmpty()){
            textInputFirstSentence.error = "Field cannot be empty"
            false
        }else {
            textInputFirstSentence.error = null
            true
        }
    }
}