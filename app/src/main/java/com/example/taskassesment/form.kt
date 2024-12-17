package com.example.taskassesment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskassesment.databinding.ActivityFormBinding

class form : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        try {
            uri?.let {
                binding.profilepicture.setImageURI(it)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val countries = arrayOf(
        "Select Country",
        "Nepal", "China", "India", "United States of America"
    )

    private val professions = arrayOf(
        "Designer", "Tester", "Marketing",
        "Sales", "Information Technology",
        "Medical", "Agriculture"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Gallery image selector
        binding.imageSelector.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Setup Spinner for countries
        val countryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, // Dropdown layout
            countries
        )
        binding.spinner.adapter = countryAdapter

        // Spinner item selection listener
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = countries[position]
                Toast.makeText(this@form, "You have selected: $selectedCountry", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                // Handle the case where nothing is selected (optional)
            }
        }

        // Setup AutoCompleteTextView for professions
        val professionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, // AutoComplete dropdown layout
            professions
        )
        binding.autoCompleteTextView.setAdapter(professionAdapter)

        binding.autoCompleteTextView.setAdapter(professionAdapter)

// Listener for AutoCompleteTextView item selection
        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedProfession = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "You selected: $selectedProfession", Toast.LENGTH_LONG).show()
        }
        binding.submit.setOnClickListener {
            // Get the values from the fields
            val name: String = binding.name.text.toString().trim()
            val email: String = binding.email.text.toString().trim()

            // Check if the name and email fields are empty
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the selected gender
            val selectedGender = when (binding.genderGroup.checkedRadioButtonId) {
                R.id.radioMale -> "Male"
                R.id.radioFemale -> "Female"
                else -> null // No gender selected
            }

            // Check if gender is selected
            if (selectedGender == null) {
                Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the selected country from spinner
            val selectedCountry = binding.spinner.selectedItem.toString()

            // Check if a country is selected
            if (selectedCountry == "Select Country") {
                Toast.makeText(this@form, "Please select a valid country.", Toast.LENGTH_SHORT).show()
            }

            // Get the selected profession from AutoCompleteTextView
            val selectedProfession = binding.autoCompleteTextView.text.toString().trim()

            // Check if profession is selected
            if (selectedProfession.isEmpty()) {
                Toast.makeText(this, "Please select a profession.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the checkbox is checked
            val isChecked = binding.checkBox3.isChecked
            if (!isChecked) {
                Toast.makeText(this, "You need to agree to the terms.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create the intent to navigate to the next activity
            val intent = Intent(this@form, homepage::class.java)

            // Add the data to the intent as extras
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("selected_gender", selectedGender)
            intent.putExtra("selected_country", selectedCountry)
            intent.putExtra("selected_profession", selectedProfession)
            intent.putExtra("image_uri", it.toString()) // Convert URI to String



            // Start the activity
            startActivity(intent)
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
