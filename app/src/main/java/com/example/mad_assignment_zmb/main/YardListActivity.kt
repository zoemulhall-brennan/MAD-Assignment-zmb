package com.example.mad_assignment_zmb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_assignment_zmb.models.YardModel

class YardListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: YardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_yard_list)

        recyclerView =
            findViewById(R.id.yardRecyclerView)

        adapter = YardAdapter(
            yards = AppData.yards.findAll(),

            onEdit = { yard ->
                editMark(yard)
            },

            onDelete = { yard ->
                deleteMark(yard)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateYards(
                AppData.yards.findAll()
            )
        }
    }

    private fun editMark(yard: YardModel) {

        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", yard.id)

        startActivity(intent)
    }

    private fun deleteMark(yard: YardModel) {

        AppData.yards.delete(yard.id)

        adapter.updateYards(
            AppData.yards.findAll()
        )
    }

}