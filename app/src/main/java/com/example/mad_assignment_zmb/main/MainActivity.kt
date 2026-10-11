package com.example.mad_assignment_zmb

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(){
    private lateinit var listLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUserInterface()
    }

    override fun onResume() {
        super.onResume()

        if (::listLayout.isInitialized) {
            displayYards()
        }
    }

    private fun createUserInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        val title = TextView(this).apply {
            text = "EquiFind: Livery Yard & Horse Riding School Finder"
            textSize = 28f
            gravity = Gravity.CENTER
        }

        val addButton = Button(this).apply {
            text = "Add Yard"
            setOnClickListener {
                val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                startActivity(intent)
            }
        }

        listLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        root.addView(
            title,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        root.addView(
            addButton,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        root.addView(
            listLayout,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(root)

        displayYards()
    }

    private fun displayYards() {

        listLayout.removeAllViews()

        val yards = AppData.yards.findAll()

        if (yards.isEmpty()) {

            val emptyText = TextView(this).apply {
                text = "No yards stored yet."
                textSize = 18f
                setPadding(0, 40, 0, 40)
            }

            listLayout.addView(emptyText)

            return
        }

        for (yard in yards) {

            val yardLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 20, 0, 20)
            }

            val yardTitle = TextView(this).apply {
                text = "${yard.id}: ${yard.title}"
                textSize = 20f
            }

            val yardDescription = TextView(this).apply {
                text = yard.desc
                textSize = 16f
            }

            val yardPrice = TextView(this).apply {
                text = "Price €${yard.price}"
                textSize = 14f
            }

            val yardContact = TextView(this).apply {
                text = "Phone: ${yard.phone_num}"
                textSize = 14f
            }

            val yardAgeLimit = TextView(this).apply {
                text = "Age Limit for lessons:  ${yard.age_limit}"
                textSize = 14f
            }


            val deleteButton = Button(this).apply {
                text = "Delete"

                setOnClickListener {
                    AppData.yards.delete(yard.id)
                    displayYards()
                }
            }

            yardLayout.addView(yardTitle)
            yardLayout.addView(yardDescription)
            yardLayout.addView(yardPrice)
            yardLayout.addView(yardContact)
            yardLayout.addView(yardAgeLimit)
            yardLayout.addView(deleteButton)

            listLayout.addView(yardLayout)
        }
    }


}