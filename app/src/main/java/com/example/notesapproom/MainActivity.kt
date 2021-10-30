package com.example.notesapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainRV: RecyclerView
    private lateinit var noteEntry: EditText
    private lateinit var saveButton: Button
    private lateinit var adapter: RVAdapter
    private lateinit var notes: ArrayList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRV= findViewById(R.id.rvMain)
        noteEntry= findViewById(R.id.noteEntry)
        saveButton= findViewById(R.id.saveButton)

        val connection= NotesDatabase.getInstance(applicationContext)

        notes= arrayListOf()
        notes.addAll(connection.notesDoa().gettingData())
        adapter= RVAdapter(this,notes)
        mainRV.adapter= adapter
        mainRV.layoutManager= LinearLayoutManager(this)

        saveButton.setOnClickListener{
            if (noteEntry.text.isNotBlank()){
                CoroutineScope(IO).launch {
                    connection.notesDoa().addNewNote(Data(0,noteEntry.text.toString()))
                }
                StyleableToast.makeText(this@MainActivity, "Saved Successfully!!", R.style.mytoast).show()
                noteEntry.text.clear()
                notes.clear()
                notes.addAll(connection.notesDoa().gettingData())
                adapter.notifyDataSetChanged()
            }
            else
                StyleableToast.makeText(this,"Please Enter Valid Values!!",R.style.mytoast).show()
        }
    }
}