package com.testing.hd.notes

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.mobiuso.noteapp.R

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    lateinit var btnAddNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        OpenCreateNoteFragment()

        btnAddNote = findViewById(R.id.addNote)

       val rv = findViewById<RecyclerView>(R.id.rvnotes)

        //var map = ArrayList<String>()
        var map = HashMap<String,String>()

        val prefrences = getSharedPreferences("notepref",Context.MODE_PRIVATE)
        val mapkey : MutableMap<String, *>? = prefrences.getAll()
        if (mapkey != null) {
            for (keyname in mapkey.keys) {
                var note : String =  prefrences.getString(keyname,"")
                map.put(keyname,note)
                Log.d("maps",keyname+ " " + note);
            }
        }

        rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.itemAnimator =  DefaultItemAnimator()
        rv.adapter = adapter(this,map)

        btnAddNote.setOnClickListener(View.OnClickListener {
            Log.d("hey","1")
            btnAddNote.visibility = View.GONE
            rv.visibility = View.GONE
            OpenCreateNoteFragment()

        })
    }


    fun OpenCreateNoteFragment()
    {
        Log.d("hey","2")
        val transaction = manager.beginTransaction()
        val createNoteFragement = CreateNoteFragement()
        transaction.replace(R.id.fragment_holder,createNoteFragement)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
