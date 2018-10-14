package com.testing.hd.notes

import android.content.Context
import android.os.Bundle
import android.view.*
import com.example.mobiuso.noteapp.R
import kotlinx.android.synthetic.main.list_fragment.view.*

/**
 * Created by ADMIN on 25-06-2018.
 */
class ListFragment : androidx.fragment.app.Fragment() {

    lateinit var adapterObj: adapter
    var actionmenuMode: Boolean = false
    var sizeOfSharedpref : Int = 0
    lateinit var allNoteMaps: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        val btnAddnote = view?.addNote
        val rv = view?.rvnotes

        btnAddnote?.setOnClickListener(View.OnClickListener {
            OpenCreateNoteFragment()
        })

        allNoteMaps = getDataFromPref() //Arraylist get all data key and value
        sizeOfSharedpref = allNoteMaps.size
        adapterObj = adapter(activity as MainActivity, allNoteMaps)
        rv?.layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(2, 1)
        rv?.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv?.adapter = adapterObj
        return view
    }

    fun getDataFromPref(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        val prefrences = (activity as MainActivity).getSharedPreferences("notepref", Context.MODE_PRIVATE)
        val retrieveAllkey: MutableMap<String, *>? = prefrences.all
        val allKeyInSortedSequence: Sequence<String>? = retrieveAllkey?.keys?.iterator()?.asSequence()?.sortedDescending()
        if (allKeyInSortedSequence != null) {
            for (keyname in allKeyInSortedSequence) {
                val noteOfkey: String = prefrences.getString(keyname, "")
                val noteObj = Note(keyname, noteOfkey)
                noteList.add(noteObj)
            }
        }
        return noteList
    }

    fun OpenCreateNoteFragment() {
        val transaction = (context as androidx.fragment.app.FragmentActivity).supportFragmentManager.beginTransaction()
        val createNoteFragement = CreateNoteFragement()
        val mArgs = Bundle()
        mArgs.putInt("Edit", 0)
        createNoteFragement.arguments = mArgs
        transaction.replace(R.id.fragment_create_note, createNoteFragement).addToBackStack("frag_new_note")
        transaction.commit()
    }

    fun onFragClosed() {
        if (sizeOfSharedpref == getDataFromPref().size) adapterObj.updateData(getDataFromPref()) else adapterObj.setData(getDataFromPref())
    }

    override fun onCreateOptionsMenu(menu: Menu?,menuInflater: MenuInflater) {
        if(actionmenuMode){
            menuInflater.inflate(R.menu.toolbar_menu, menu)
            super.onCreateOptionsMenu(menu, menuInflater)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                (context as androidx.fragment.app.FragmentActivity).supportFragmentManager.popBackStackImmediate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).finish()
    }
}