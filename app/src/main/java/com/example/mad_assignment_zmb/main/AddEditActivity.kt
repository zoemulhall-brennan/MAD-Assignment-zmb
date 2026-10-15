package com.example.mad_assignment_zmb


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
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

        setContentView(R.layout.activity_add_edit)


        titleInput =
            findViewById(R.id.titleInput)

        descriptionInput =
            findViewById(R.id.descriptionInput)

        priceInput =
            findViewById(R.id.priceInput)

        websitelinkInput =
            findViewById(R.id.websiteLinkInput)

        phone_numInput =
            findViewById(R.id.phoneNumInput)

        age_limitInput =
            findViewById(R.id.ageLimitInput)

        val saveButton =
            findViewById<Button>(R.id.saveButton)

        val cancelButton =
            findViewById<Button>(R.id.cancelButton)

        editingId =
            intent.getLongExtra("id", -1L)
                .takeIf { it != -1L }

        if (editingId != null) {

            findViewById<TextView>(R.id.editYardView)
                .text = "Edit Yard"

            loadExistingYard(editingId!!)
        }

        saveButton.setOnClickListener {
            saveYard()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingYard(id: Long) {

        Timber.d("Editing yard id=$id")

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


        if (editingId == null){

            Timber.d("Creating new yard")

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

            Timber.d("Editing existing yard")

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

