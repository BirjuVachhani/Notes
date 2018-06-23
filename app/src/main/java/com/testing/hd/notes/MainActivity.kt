package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.mobiuso.noteapp.R
import java.util.*

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    lateinit var rv : RecyclerView
    lateinit var btnAddNote: FloatingActionButton
    lateinit var adap : adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddNote = findViewById(R.id.addNote)
        rv = findViewById<RecyclerView>(R.id.rvnotes)

        rv.visibility = View.VISIBLE
        btnAddNote.visibility = View.VISIBLE

        btnAddNote.setOnClickListener(View.OnClickListener {
            Log.d("hey","1")
            btnAddNote.visibility = View.GONE
            rv.visibility = View.GONE
            OpenCreateNoteFragment()
        })

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
        adap = adapter(this,map,rv,btnAddNote)
        //rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = StaggeredGridLayoutManager(2,1)
        rv.itemAnimator =  DefaultItemAnimator()
        rv.adapter = adap

    }

    fun OpenCreateNoteFragment()
    {
        Log.d("hey","2")
        val transaction = manager.beginTransaction()
        val createNoteFragement = CreateNoteFragement()
        val mArgs = Bundle()
        mArgs.putInt("Edit", 0)
        createNoteFragement.setArguments(mArgs)
        transaction.replace(R.id.fragment_holder,createNoteFragement)
       // transaction.addToBackStack(   null)
        transaction.commit()
    }


}
