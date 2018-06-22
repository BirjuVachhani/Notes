package com.testing.hd.notes

import android.content.Context
import android.content.Intent
import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobiuso.noteapp.R

class adapter(context: Context,private var items:HashMap<String,String>): RecyclerView.Adapter<adapter.ViewHolder>()
{
    var context: Context
    init {
        this.context = context
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.note_card,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         //holder?.txtnote?.text = items.get(position)

        var str : String = ArrayList<String>(items.values).get(position)
            holder?.txtnote?.text = str
            holder?.txtnote?.setOnClickListener {
            var intent = Intent(context,CreateNoteFragement::class.java)
                intent.putExtra("Edit","1")
                intent.putExtra("note",ArrayList<String>(items.values).get(position))
                intent.putExtra("key",ArrayList<String>(items.keys).get(position))
                context.startActivity(intent)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var txtnote : TextView? = null
        init {
            this.txtnote = view?.findViewById<TextView>(R.id.notedesc)
        }
    }
}