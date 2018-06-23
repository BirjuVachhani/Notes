package com.testing.hd.notes

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mobiuso.noteapp.R
import java.util.concurrent.TimeUnit
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import java.sql.Timestamp


class CreateNoteFragement : Fragment() {
    lateinit var edNoteText: EditText
    var index: Int = 0
    lateinit var key: String

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    private fun showHideOptionsMenu() {
        (activity as MainActivity).isNoteMode = !(activity as MainActivity).isNoteMode
        (activity as MainActivity).invalidateOptionsMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.createnote_fragment, container, false)

        var args = getArguments()
        index = args!!.getInt("Edit", 0)
        edNoteText = view.findViewById(R.id.note)
        edNoteText.requestFocus()
        if (index == 1) {
            var str: String = args!!.getString("Note")
            key = args!!.getString("key")
            edNoteText.setText(str)
            showHideOptionsMenu()
        }
        edNoteText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (activity as MainActivity).isNoteMode = p0?.length != 0
                (activity as MainActivity).invalidateOptionsMenu()
            }
        })
        return view
    }

    fun addNotes() {
        var notes: String = edNoteText.text.toString()
        if (index == 1) {
            var mynote = this.activity!!.getSharedPreferences("notepref", Context.MODE_PRIVATE)
            var editor = mynote.edit()
            editor.putString(key, notes)
            editor.apply()
        } else {
//            var timestamp: String = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
            val currentTime = System.currentTimeMillis().toLong()
            val timestamp: String = Timestamp(currentTime).time.toString()
            var mynote = this.activity!!.getSharedPreferences("notepref", Context.MODE_PRIVATE)
            var editor = mynote.edit()
            editor.putString(timestamp, notes)
            editor.apply()
            Toast.makeText(this.activity, "Note Added", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (!edNoteText.text.toString().equals("")) {
            addNotes()
            showHideOptionsMenu()
        }
        edNoteText.clearFocus()
        (activity as MainActivity).onFragClosed()
    }
}
