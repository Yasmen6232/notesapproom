package com.example.notesapproom

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapproom.databinding.NotesViewBinding
import io.github.muddz.styleabletoast.StyleableToast

class RVAdapter (private val context: Context,private val list: ArrayList<Data>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    private val connection= NotesDatabase.getInstance(context)

    class ItemViewHolder(val binding: NotesViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NotesViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note= list[position]
        holder.binding.noteTV.text= note.note
        holder.binding.editImage.setOnClickListener{
            val input = EditText(context)
            input.hint = "Enter New Note Here"
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Please Enter New Note:")
            .setCancelable(false)
            .setPositiveButton("Save") {
                _,_ -> val str =input.text.toString()
                if(str.isNotBlank()) {
                    connection.notesDoa().updateNote(Data(note.pk,str))
                    StyleableToast.makeText(context, "Update Successfully!!", R.style.mytoast).show()
                    list.clear()
                    list.addAll(connection.notesDoa().gettingData())
                    notifyDataSetChanged()
                }
                else
                    StyleableToast.makeText(context,"Please Enter Valid Values!!",R.style.mytoast).show()
            }
            .setNegativeButton("Cancel") {dialog,_ -> dialog.cancel()
            }
            val alert = dialogBuilder.create()
            alert.setTitle("Update Note")
            alert.setView(input)
            alert.show()
        }
        holder.binding.deleteImage.setOnClickListener{
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes") {
                        _,_ ->
                    connection.notesDoa().deleteNote(Data(note.pk,note.note))
                    StyleableToast.makeText(context, "Deleted Successfully!!", R.style.mytoast).show()
                    list.clear()
                    list.addAll(connection.notesDoa().gettingData())
                    notifyDataSetChanged()
                }
                .setNegativeButton("Cancel") {dialog,_ -> dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Are You Sure You Want to Delete Note")
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}