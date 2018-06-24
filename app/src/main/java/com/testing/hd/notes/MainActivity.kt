package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mobiuso.noteapp.R
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    lateinit var rv: RecyclerView
    lateinit var btnAddNote: ImageView
    lateinit var adap: adapter
    var isNoteMode: Boolean = false
    lateinit var createNoteFragement: CreateNoteFragement
    var size : Int = 0
    lateinit var map: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddNote = findViewById(R.id.addNote)
        rv = findViewById<RecyclerView>(R.id.rvnotes)

        btnAddNote.setOnClickListener(View.OnClickListener {
            Log.d("hey", "1")
            OpenCreateNoteFragment()
        })

        map = getDataFromPref(); //Arraylist get all data key and value
        size = map.size
        adap = adapter(this, map)
        //rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = StaggeredGridLayoutManager(2, 1)
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = adap
    }

    fun getDataFromPref(): ArrayList<Note> {
        var noteList = ArrayList<Note>()
        val prefrences = getSharedPreferences("notepref", Context.MODE_PRIVATE)
        var mapkey: MutableMap<String, *>? = prefrences.getAll()
        var mapSequence: Sequence<String>? = mapkey?.keys?.iterator()?.asSequence()?.sortedDescending()
        if (mapSequence != null) {
            for (keyname in mapSequence) {
                var str: String = prefrences.getString(keyname, "")
                var note = Note(keyname, str)
                noteList.add(note)
                Log.d("maps", keyname + " " + note);
            }
        }
        return noteList
    }

    fun OpenCreateNoteFragment() {
        val transaction = manager.beginTransaction()
        createNoteFragement = CreateNoteFragement()
        val mArgs = Bundle()
        mArgs.putInt("Edit", 0)
        createNoteFragement.setArguments(mArgs)
        transaction.replace(R.id.fragment_holder, createNoteFragement).addToBackStack("frag_new_note")
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return isNoteMode
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                Toast.makeText(this, "save button clicked", Toast.LENGTH_SHORT).show()
                manager.popBackStackImmediate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onFragClosed() {
        Log.d("sizess"," " + size + " " + map.size + " " +getDataFromPref().size)
        if(size == getDataFromPref().size)
        {
            adap.updateData(getDataFromPref());
        }
        else
        {
            adap.setData(getDataFromPref())
        }
    }
}
