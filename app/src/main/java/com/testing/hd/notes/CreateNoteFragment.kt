package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mobiuso.noteapp.R
import java.util.concurrent.TimeUnit

class CreateNoteFragement : Fragment()
{
    lateinit var edNoteText: EditText
    lateinit var btnAdd: Button

    override fun onAttach(context: Context?) {
        Log.d("hey","4")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("hey","3")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("hey","5")
        val view =  inflater?.inflate(R.layout.createnote_fragment,container,false)

        var edNoteText : EditText = view.findViewById(R.id.note)
        var btnAdd : Button = view.findViewById(R.id.button)

        btnAdd.setOnClickListener(View.OnClickListener {
            var notes : String = edNoteText.text.toString()
            AddNotes(notes,view)
        })
        return view
    }

    fun AddNotes(note : String,view : View)
    {
        var timestamp : String = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
        var mynote = this.activity!!.getSharedPreferences("notepref",Context.MODE_PRIVATE)
        var editor = mynote.edit()
        editor.putString(timestamp,note)
        editor.apply()
        Toast.makeText(this.activity,timestamp + " done",Toast.LENGTH_LONG).show()
        val intent = Intent(this.activity,MainActivity::class.java)
        startActivity(intent)
    }


}