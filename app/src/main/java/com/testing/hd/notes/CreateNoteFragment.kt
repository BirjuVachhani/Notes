package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
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
import android.R.id.edit
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity


class CreateNoteFragement : Fragment()
{
    lateinit var edNoteText: EditText
    lateinit var btnAdd: Button
    var index: Int = 0
    lateinit var key : String

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

        var args = getArguments()
        index = args!!.getInt("Edit", 0)
        var edNoteText : EditText = view.findViewById(R.id.note)
        var btnAdd : Button = view.findViewById(R.id.button)

        if(index == 0)
        {
            Toast.makeText(this.activity,"soryy",Toast.LENGTH_LONG).show()
        }
        else
        {
            var str : String = args!!.getString("Note")
            key = args!!.getString("key")
            edNoteText.setText(str)
           // var note : String = activity!!.getIntent().getExtras().getString("note");
            //var key : String = activity!!.getIntent().getExtras().getString("key");
            Toast.makeText(this.activity,str,Toast.LENGTH_LONG).show()
        }

        btnAdd.setOnClickListener(View.OnClickListener {
            var notes : String = edNoteText.text.toString()
            if(index==1)
            {
                var mynote = this.activity!!.getSharedPreferences("notepref",Context.MODE_PRIVATE)
                var editor = mynote.edit()
                editor.putString(key,notes)
                editor.apply()
                val intent = Intent(this.activity,MainActivity::class.java)
                startActivity(intent)
            }else
            {
                AddNotes(notes,view)
            }
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
        Toast.makeText(this.activity,"Note Added",Toast.LENGTH_LONG).show()
        val intent = Intent(this.activity,MainActivity::class.java)
        startActivity(intent)
    }
}