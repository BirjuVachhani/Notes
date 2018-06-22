package com.testing.hd.notes

import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobiuso.noteapp.R

class adapter(private var items:ArrayList<String>): RecyclerView.Adapter<adapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


        val v = LayoutInflater.from(p0?.context).inflate(R.layout.note_card,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder?.txtnote?.text = items.get(position)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var txtnote : TextView? = null
        init {
            this.txtnote = view?.findViewById<TextView>(R.id.notedesc)
        }
    }
}