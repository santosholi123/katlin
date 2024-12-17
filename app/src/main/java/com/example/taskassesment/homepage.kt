package com.example.taskassesment

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskassesment.databinding.ActivityHomepageBinding


class homepage : AppCompatActivity() {
    lateinit var binding: ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val gender= intent.getStringExtra("selected_gender")
        val country= intent.getStringExtra("selected_country")
        val profession= intent.getStringExtra("selected_profession")

        binding.greeting.text= "Welcome $name"
        binding.email.text= "Email: $email"
        binding.gender.text= "Github: $gender"
        binding.country.text= "Country of Origin $country"
        binding.profession.text= "Profession: $profession"


        val imageUriString = intent.getStringExtra("image_uri")
        if (imageUriString != null) {
            try {
                // Convert back to URI and set it
                val imageUri = Uri.parse(imageUriString)
                binding.imageView.setImageURI(imageUri)
                Toast.makeText(this, "Image loaded successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Image URI is null!", Toast.LENGTH_LONG).show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}