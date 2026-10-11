package com.example.mad_assignment_zmb


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_assignment_zmb.models.YardModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var websitelinkInput: EditText
    private lateinit var phone_numInput: EditText
    private lateinit var age_limitInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUserInterface()

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingYard(editingId!!)
        }
    }

    private fun createUserInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        titleInput = EditText(this).apply {
            hint = "Yard Name"
        }

        descriptionInput = EditText(this).apply {
            hint = "Description"
        }

        priceInput = EditText(this).apply {
            hint = "Price"
            inputType =
                android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        websitelinkInput = EditText(this).apply {
            hint = "Website URL"
        }

        phone_numInput = EditText(this).apply {
            hint = "Phone Number"
        }

        age_limitInput = EditText(this).apply {
            hint = "Age Limit for Horse Riding Lessons"
        }


        val saveButton = Button(this).apply {
            text = "Save"

            setOnClickListener {
                saveYard()
            }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"

            setOnClickListener {
                finish()
            }
        }

        root.addView(titleInput)
        root.addView(descriptionInput)
        root.addView(priceInput)
        root.addView(websitelinkInput)
        root.addView(phone_numInput)
        root.addView(age_limitInput)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)
    }

    private fun loadExistingYard(id: Long) {

        val yard = AppData.yards.findOne(id)

        if (yard == null) {
            Toast.makeText(
                this,
                "Yard not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        titleInput.setText(yard.title)
        descriptionInput.setText(yard.desc)
        priceInput.setText(yard.price.toString())
        websitelinkInput.setText(yard.websitelink)
        phone_numInput.setText(yard.phone_num)
        age_limitInput.setText(yard.age_limit)
    }

    private fun saveYard() {

        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val websitelink = websitelinkInput.text.toString().trim()
        val phone_num = phone_numInput.text.toString().trim()
        val age_limit = age_limitInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Yard name is required"
            return
        }

        val price = priceInput.text.toString().toDoubleOrNull()

        if (price == null) {
            priceInput.error = "Enter a valid number"
            return
        }


        if (editingId == null || editingId == -1L) {

            val yard = YardModel(
                title = title,
                desc = description,
                price = price,
                websitelink = websitelink,
                phone_num = phone_num,
                age_limit = age_limit
            )

            AppData.yards.create(yard)

            Toast.makeText(
                this,
                "Yard created",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val yard = YardModel(
                id = editingId!!,
                title = title,
                desc = description,
                price = price,
                websitelink = websitelink,
                phone_num = phone_num,
                age_limit = age_limit
            )

            AppData.yards.update(yard)

            Toast.makeText(
                this,
                "Yard updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}

