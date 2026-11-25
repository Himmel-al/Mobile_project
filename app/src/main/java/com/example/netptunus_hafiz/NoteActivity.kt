package com.example.netptunus_hafiz

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netptunus_hafiz.adapter.NoteAdapter
import com.example.netptunus_hafiz.databinding.ActivityNoteBinding
import com.example.netptunus_hafiz.model.NoteModel
import com.example.netptunus_hafiz.network.SupabaseApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var adapter: NoteAdapter
    private val noteList = mutableListOf<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()

        binding.fabAddNote.setOnClickListener {
            showNoteDialog(null)
        }

        loadNotes()
    }

    private fun setupToolbar() {
        binding.toolbarNote.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarNote.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(
            noteList,
            onEditClick = { note -> showNoteDialog(note) },
            onDeleteClick = { note -> confirmDeleteNote(note) }
        )
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = adapter
    }

    private fun loadNotes() {
        binding.progressBarNote.visibility = View.VISIBLE
        SupabaseApiClient.getService().getNotes().enqueue(object : Callback<List<NoteModel>> {
            override fun onResponse(
                call: Call<List<NoteModel>>,
                response: Response<List<NoteModel>>
            ) {
                binding.progressBarNote.visibility = View.GONE
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    noteList.clear()
                    noteList.addAll(data)
                    adapter.updateData(noteList)
                } else {
                    Toast.makeText(
                        this@NoteActivity,
                        "Gagal memuat catatan: ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<NoteModel>>, t: Throwable) {
                binding.progressBarNote.visibility = View.GONE
                Toast.makeText(
                    this@NoteActivity,
                    "Koneksi gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showNoteDialog(note: NoteModel?) {
        val isEdit = note != null

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(if (isEdit) "Edit Catatan" else "Tambah Catatan")

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val etTitle = EditText(this).apply {
            hint = "Judul Catatan"
            setText(note?.title ?: "")
        }
        val etContent = EditText(this).apply {
            hint = "Isi Catatan"
            setText(note?.content ?: "")
        }

        val spinnerStatus = Spinner(this)
        val statusOptions = arrayOf("To Do", "On Progress", "Done")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = spinnerAdapter

        if (note != null) {
            val statusIndex = statusOptions.indexOf(note.status)
            if (statusIndex >= 0) {
                spinnerStatus.setSelection(statusIndex)
            }
        }

        layout.addView(etTitle)
        layout.addView(etContent)
        layout.addView(spinnerStatus)

        dialogBuilder.setView(layout)

        dialogBuilder.setPositiveButton("Simpan") { dialog, _ ->
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()
            val status = spinnerStatus.selectedItem.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Judul catatan wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            if (isEdit && note?.id != null) {
                val updatedNote = NoteModel(
                    id = note.id,
                    title = title,
                    content = content,
                    status = status
                )
                updateNoteInSupabase(note.id, updatedNote)
            } else {
                val newNote = NoteModel(
                    title = title,
                    content = content,
                    status = status
                )
                createNoteInSupabase(newNote)
            }
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Batal", null)
        dialogBuilder.show()
    }

    private fun createNoteInSupabase(note: NoteModel) {
        binding.progressBarNote.visibility = View.VISIBLE
        SupabaseApiClient.getService().createNote(note).enqueue(object : Callback<List<NoteModel>> {
            override fun onResponse(
                call: Call<List<NoteModel>>,
                response: Response<List<NoteModel>>
            ) {
                binding.progressBarNote.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@NoteActivity, "Catatan berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    loadNotes()
                } else {
                    Toast.makeText(this@NoteActivity, "Gagal tambah: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<NoteModel>>, t: Throwable) {
                binding.progressBarNote.visibility = View.GONE
                Toast.makeText(this@NoteActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateNoteInSupabase(id: Long, note: NoteModel) {
        binding.progressBarNote.visibility = View.VISIBLE
        SupabaseApiClient.getService().updateNote("eq.$id", note).enqueue(object : Callback<List<NoteModel>> {
            override fun onResponse(
                call: Call<List<NoteModel>>,
                response: Response<List<NoteModel>>
            ) {
                binding.progressBarNote.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@NoteActivity, "Catatan berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    loadNotes()
                } else {
                    Toast.makeText(this@NoteActivity, "Gagal update: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<NoteModel>>, t: Throwable) {
                binding.progressBarNote.visibility = View.GONE
                Toast.makeText(this@NoteActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmDeleteNote(note: NoteModel) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus '${note.title}'?")
            .setPositiveButton("Hapus") { _, _ ->
                note.id?.let { deleteNoteInSupabase(it) }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteNoteInSupabase(id: Long) {
        binding.progressBarNote.visibility = View.VISIBLE
        SupabaseApiClient.getService().deleteNote("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.progressBarNote.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@NoteActivity, "Catatan berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadNotes()
                } else {
                    Toast.makeText(this@NoteActivity, "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.progressBarNote.visibility = View.GONE
                Toast.makeText(this@NoteActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
