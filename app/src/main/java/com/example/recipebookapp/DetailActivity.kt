package com.example.recipebookapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // setup toolbar with back button
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.detailToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("title")

        // bind views
        val image = findViewById<ImageView>(R.id.detailImage)
        val time = findViewById<TextView>(R.id.detailTime)
        val ingredients = findViewById<TextView>(R.id.detailIngredients)
        val recipe = findViewById<TextView>(R.id.detailRecipe)

        // populate data from intent
        time.text = "⏱ " + intent.getStringExtra("time")
        ingredients.text = intent.getStringExtra("ingredients")
        recipe.text = intent.getStringExtra("fullRecipe")
        image.setImageResource(intent.getIntExtra("image", 0))
    }

    // back arrow press goes back to MainActivity
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}