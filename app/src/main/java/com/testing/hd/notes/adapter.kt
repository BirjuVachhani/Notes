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
import android.widget.ImageButton
import kotlin.collections.ArrayList

class adapter(context: Context, private var items: ArrayList<Note>) : RecyclerView.Adapter<adapter.ViewHolder>() {
    var context: Context
    lateinit var adap: adapter

    init {
        this.context = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.note_card, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.txtnote?.text = items.get(position)

//        var str: String = ArrayList<String>(items.values).get(position)
//        var key: String = ArrayList<String>(items.keys).get(position)
        var str: String = items.get(position).note
        var key: String = items.get(position).key
        holder?.txtnote?.text = str

        holder?.txtnote?.setOnClickListener {
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            val createNoteFragement = CreateNoteFragement()
            val mArgs = Bundle()
            mArgs.putInt("Edit", 1)
            mArgs.putString("Note", str)
            mArgs.putString("key", key)
            createNoteFragement.setArguments(mArgs)
            transaction.replace(R.id.fragment_holder, createNoteFragement)
            transaction.addToBackStack("frag_new_note")
            transaction.commit()

            /*var intent = Intent(context,CreateNoteFragement::class.java)
                intent.putExtra("Edit",1)
                intent.putExtra("note",ArrayList<String>(items.values).get(position))
                intent.putExtra("key",ArrayList<String>(items.keys).get(position))
                context.startActivity(intent)*/
        }


        holder?.delbtn?.setOnClickListener {
            var mynote = context.getSharedPreferences("notepref", Context.MODE_PRIVATE)
            mynote.edit().remove(key).commit()
            items.remove(items.get(holder.adapterPosition))
            notifyDataSetChanged()
            /*var intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)*/
        }

    }

    fun setData(notes: ArrayList<Note>) {
        items = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtnote: TextView? = null
        var delbtn: ImageButton? = null

        init {
            this.txtnote = view?.findViewById<TextView>(R.id.notedesc)
            this.delbtn = view?.findViewById<ImageButton>(R.id.delNote)
        }
    }
}

