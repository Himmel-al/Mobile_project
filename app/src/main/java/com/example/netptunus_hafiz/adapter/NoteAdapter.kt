package com.example.netptunus_hafiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netptunus_hafiz.databinding.ItemNoteBinding
import com.example.netptunus_hafiz.model.NoteModel

class NoteAdapter(
    private var list: List<NoteModel>,
    private val onEditClick: (NoteModel) -> Unit,
    private val onDeleteClick: (NoteModel) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvNoteTitle.text = item.title
        holder.binding.tvNoteContent.text = item.content
        holder.binding.tvNoteStatus.text = item.status

        holder.binding.btnEditNote.setOnClickListener { onEditClick(item) }
        holder.binding.btnDeleteNote.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<NoteModel>) {
        list = newList
        notifyDataSetChanged()
    }
}
