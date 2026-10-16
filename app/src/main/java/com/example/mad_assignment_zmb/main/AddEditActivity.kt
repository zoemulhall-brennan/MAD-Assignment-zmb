package com.example.mad_assignment_zmb


import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import timber.log.Timber
import com.example.mad_assignment_zmb.models.YardModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var websitelinkInput: EditText
    private lateinit var phone_numInput: EditText
    private lateinit var age_limitInput: EditText
    private lateinit var select_imageInput: ImageView //sets the image to the selected image

    private var selectedImageUri: Uri? = null // stores the selected image URI
    private var editingId: Long? = null

    // Register Photo Picker activity launcher in single-select mode
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Timber.d("Selected URI: $uri")
            selectedImageUri = uri // 1. Remembers the selected photo

            // Grant persistent read permission to your app
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            try {
                contentResolver.takePersistableUriPermission(uri, flag)
            } catch (e: Exception) {
                Timber.e(e, "Could not take persistable permission") // Logs print out the error
            }
            select_imageInput.setImageURI(uri)   // 2. Displays the preview on screen
        } else {
            Timber.d("No media selected") // Logs the fact that no media was selected
        }
    }

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

        select_imageInput =
            findViewById(R.id.select_imageInput) // ImageView to display the selected image

        val select_imageButton =
            findViewById<Button>(R.id.imageButton) // Button to launch the photo picker

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

        select_imageButton.setOnClickListener { // Launch the photo picker when the button is clicked
            // Launch the photo picker for images only
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
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

        if (yard.select_image.isNotEmpty()) { // Check if there's an image URI
            selectedImageUri = Uri.parse(yard.select_image) // Set the selected image URI to the one from the yard
            select_imageInput.setImageURI(selectedImageUri) // Display the preview on screen
        }
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
                age_limit = age_limit,
                select_image = selectedImageUri?.toString() ?: "" // Use the selected image URI
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
                age_limit = age_limit,
                select_image = selectedImageUri?.toString() ?: "" // Use the selected image URI
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

