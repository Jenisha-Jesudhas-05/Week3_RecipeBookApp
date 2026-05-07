package com.example.recipebookapp

data class Recipe(
    val title: String,
    val time: String,
    val shortDesc: String,
    val image: Int,
    val ingredients: String,
    val fullRecipe: String,
    val category: String
)