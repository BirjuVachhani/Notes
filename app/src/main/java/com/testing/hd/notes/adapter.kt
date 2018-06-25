package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobiuso.noteapp.R
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.util.*
import android.R.id.edit
import android.content.SharedPreferences
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.system.Os.bind
import android.widget.ImageButton
import kotlinx.android.synthetic.main.note_card.view.*
import kotlin.collections.ArrayList

class adapter(context: Context, private var items: ArrayList<Note>) : RecyclerView.Adapter<adapter.ViewHolder>() {
    var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.note_card, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notedesc: String = items.get(position).note
        val notekey: String = items.get(position).key

        holder.notedescription?.text = notedesc
        holder.notedescription?.setOnClickListener {
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            val createNoteFragement = CreateNoteFragement()
            val mArgs = Bundle()
            mArgs.putInt("Edit", 1)
            mArgs.putString("Note", notedesc)
            mArgs.putString("key", notekey)
            createNoteFragement.setArguments(mArgs)
            transaction.replace(R.id.fragment_create_note, createNoteFragement)
            transaction.addToBackStack("frag_new_note")
            transaction.commit()
        }

        holder.deleteNoteButton?.setOnClickListener {
            val manaagerofListFragment = (context as FragmentActivity).supportFragmentManager
            val  fragment = manaagerofListFragment.findFragmentById(R.id.fragment_recycler) as ListFragment
            fragment.sizeOfSharedpref -= 1
            val mynote = context.getSharedPreferences("notepref", Context.MODE_PRIVATE)
            mynote.edit().remove(notekey).apply()
            items.remove(items.get(holder.adapterPosition))
            notifyDataSetChanged()
        }
    }

    fun setData(notes: ArrayList<Note>) {
        items = notes
        notifyItemInserted(0)
    }

    fun updateData(notes: ArrayList<Note>) {
        items = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var notedescription = view.notedesc
        var deleteNoteButton = view.delNote
    }
}

