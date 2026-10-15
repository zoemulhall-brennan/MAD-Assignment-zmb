package com.example.mad_assignment_zmb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import com.example.mad_assignment_zmb.models.YardModel

class YardListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: YardAdapter
    private lateinit var searchInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_yard_list)

        recyclerView =
            findViewById(R.id.yardRecyclerView)

        adapter = YardAdapter(
            yards = AppData.yards.findAll(),

            listener = object : OnYardListener {
                override fun onYardClick(yard: YardModel) {
                    editYard(yard)
                }
                override fun onYardLongClick(yard: YardModel): Boolean {
                    confirmDeleteYard(yard)
                    return true
                }
            }
        )


        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        searchInput = findViewById(R.id.searchInput)
        searchInput.doAfterTextChanged {
            performFilter()
        }

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    private fun performFilter() {
        val query = searchInput.text.toString().trim()

        val filteredList = AppData.yards.findAll().filter { yard ->
            query.isEmpty() ||
                    yard.id.toString().contains(query, ignoreCase = true) ||
                    yard.title.contains(query, ignoreCase = true) ||
                    yard.desc.contains(query, ignoreCase = true) ||
                    yard.price.toString().contains(query, ignoreCase = true)
        }

        adapter.updateYards(filteredList)
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateYards(
                AppData.yards.findAll()
            )
        }
    }

    private fun editYard(yard: YardModel) {

        Timber.d("Editing yard id=${yard.id}, title=${yard.title}")
        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", yard.id)

        startActivity(intent)
    }

    private fun confirmDeleteYard(yard: YardModel) {

        AlertDialog.Builder(this)
            .setTitle("Delete yard?")
            .setMessage("Are you sure you want to delete \"${yard.title}\"?")
            .setPositiveButton("Delete") { _, _ ->
                deleteYard(yard)     // only runs if user taps "Delete"
            }
            .setNegativeButton("Cancel", null)   // null = do nothing
            .show()
    }

    private fun deleteYard(yard: YardModel) {

        Timber.d("Deleting yard id=${yard.id}, title=${yard.title}")

        AppData.yards.delete(yard.id)

        adapter.updateYards(
            AppData.yards.findAll()
        )
    }

}