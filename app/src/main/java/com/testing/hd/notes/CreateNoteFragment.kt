package com.testing.hd.notes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobiuso.noteapp.R
import kotlinx.android.synthetic.main.createnote_fragment.view.*
import java.sql.Timestamp


class CreateNoteFragment : Fragment() {
    lateinit var etNoteText: EditText
    var flagForCreateEdit: Int = 0
    lateinit var editKey: String
    lateinit var editNote: String
    lateinit var listfragment: ListFragment

    private fun showHideOptionsMenu() {
        listfragment.actionmenuMode = !listfragment.actionmenuMode
        (listfragment).activity?.invalidateOptionsMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.createnote_fragment, container, false)

        listfragment = fragmentManager!!.findFragmentById(R.id.fragment_recycler) as ListFragment
        etNoteText = view?.note!!

        etNoteText.requestFocus()

        val args = arguments
        flagForCreateEdit = args!!.getInt("Edit", 0)

        if (flagForCreateEdit == 1) {
            editNote = args.getString("Note")
            editKey = args.getString("key")
            etNoteText.setText(editNote)
            showHideOptionsMenu()
        }
        etNoteText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (listfragment).actionmenuMode = p0?.length != 0
                (listfragment).activity?.invalidateOptionsMenu()
            }
        })

        val ivSave = view.findViewById<ImageView>(R.id.ivSave)
        ivSave.setOnClickListener {
            (context as AppCompatActivity).supportFragmentManager.popBackStackImmediate()

        }
        return view
    }

    fun addNotes(notes: String) {
        if (flagForCreateEdit == 1) {
            addToSharedPrefrence(editKey, notes, "Note Updated")
        } else {
            listfragment = fragmentManager!!.findFragmentById(R.id.fragment_recycler) as ListFragment
            (listfragment).sizeOfSharedpref += 1
            // val currentTime = System.currentTimeMillis().toLong()
            val timestampAsKey: String = Timestamp(System.currentTimeMillis()).time.toString()
            addToSharedPrefrence(timestampAsKey, notes, "Note Added")
        }
    }

    fun addToSharedPrefrence(key: String, note: String, stateMsg: String) {
        val myPrefObj = this.activity!!.getSharedPreferences("notepref", Context.MODE_PRIVATE)
        val editorOfSharePref = myPrefObj.edit()
        editorOfSharePref.putString(key, note)
        editorOfSharePref.apply()
        Toast.makeText(this.activity, stateMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        val notes: String = etNoteText.text.toString()
        if (!etNoteText.text.toString().equals("")) {
            addNotes(notes)
            showHideOptionsMenu()
        }
        etNoteText.clearFocus()
        listfragment.onFragClosed()
    }
}
