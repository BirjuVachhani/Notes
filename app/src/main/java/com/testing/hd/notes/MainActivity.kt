package com.testing.hd.notes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mobiuso.noteapp.R

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenRecyclerListFragment()
    }

    fun OpenRecyclerListFragment() {
        val transaction = manager.beginTransaction()
        val listFragment = ListFragment()
        transaction.replace(R.id.fragment_recycler, listFragment).addToBackStack("frag_recycle")
        transaction.commit()
    }
}
