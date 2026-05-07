package com.example.recipebookapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var isGridLayout = false
    private var isStaggered = false
    private lateinit var adapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private var currentCategory = "All"  // track selected chip

    val recipeList = listOf(
        Recipe(
            title = "Pizza",
            time = "30 mins",
            shortDesc = "Cheesy homemade pizza",
            image = R.drawable.pizza,
            ingredients = "• Pizza dough\n• Tomato sauce\n• Mozzarella cheese\n• Olives\n• Bell peppers",
            fullRecipe = "1. Preheat oven to 220°C.\n2. Roll out the dough.\n3. Spread tomato sauce.\n4. Add toppings.\n5. Bake 15–20 mins.",
            category = "Italian"
        ),
        Recipe(
            title = "Burger",
            time = "20 mins",
            shortDesc = "Juicy beef burger",
            image = R.drawable.burger,
            ingredients = "• Beef patty\n• Burger bun\n• Lettuce\n• Tomato\n• Cheese\n• Ketchup",
            fullRecipe = "1. Season the patty.\n2. Grill 4 mins each side.\n3. Toast the bun.\n4. Layer all ingredients.\n5. Serve hot.",
            category = "Fast Food"
        ),
        Recipe(
            title = "Pasta",
            time = "25 mins",
            shortDesc = "Classic Italian pasta",
            image = R.drawable.pasta,
            ingredients = "• 200g pasta\n• Tomato sauce\n• Garlic\n• Olive oil\n• Parmesan\n• Basil",
            fullRecipe = "1. Boil pasta until al dente.\n2. Sauté garlic in oil.\n3. Add tomato sauce, simmer.\n4. Toss pasta in sauce.\n5. Top with parmesan.",
            category = "Italian"
        ),
        Recipe(
            title = "Sushi",
            time = "45 mins",
            shortDesc = "Fresh Japanese sushi rolls",
            image = R.drawable.sushi,
            ingredients = "• Sushi rice\n• Nori sheets\n• Salmon / Tuna\n• Cucumber\n• Avocado\n• Soy sauce\n• Wasabi",
            fullRecipe = "1. Cook and season sushi rice with vinegar.\n2. Lay nori on bamboo mat.\n3. Spread rice evenly on nori.\n4. Place fillings in a line.\n5. Roll tightly using the mat.\n6. Slice into pieces and serve with soy sauce.",
            category = "Japanese"
        ),
        Recipe(
            title = "Tacos",
            time = "20 mins",
            shortDesc = "Spicy Mexican street tacos",
            image = R.drawable.tacos,
            ingredients = "• Taco shells\n• Ground beef\n• Taco seasoning\n• Lettuce\n• Tomato\n• Cheddar cheese\n• Sour cream\n• Salsa",
            fullRecipe = "1. Cook ground beef with taco seasoning.\n2. Warm taco shells in oven for 3 mins.\n3. Fill shells with beef.\n4. Top with lettuce, tomato, cheese.\n5. Add sour cream and salsa on top.",
            category = "Mexican"
        ),
        Recipe(
            title = "Chicken Biryani",
            time = "60 mins",
            shortDesc = "Aromatic Indian spiced rice",
            image = R.drawable.biriyani,
            ingredients = "• 500g chicken\n• 2 cups basmati rice\n• Onions\n• Yogurt\n• Biryani masala\n• Saffron\n• Ghee\n• Mint leaves",
            fullRecipe = "1. Marinate chicken in yogurt and spices for 30 mins.\n2. Fry onions until golden brown.\n3. Cook chicken until tender.\n4. Par-boil rice with whole spices.\n5. Layer rice over chicken.\n6. Add saffron milk and mint.\n7. Dum cook (seal and steam) for 20 mins.",
            category = "Indian"
        ),
        Recipe(
            title = "Pancakes",
            time = "15 mins",
            shortDesc = "Fluffy breakfast pancakes",
            image = R.drawable.pancakes,
            ingredients = "• 1 cup flour\n• 1 cup milk\n• 1 egg\n• 2 tbsp sugar\n• 1 tsp baking powder\n• Butter\n• Maple syrup",
            fullRecipe = "1. Mix flour, sugar, and baking powder.\n2. Whisk in milk and egg until smooth.\n3. Heat butter in a non-stick pan.\n4. Pour batter to form circles.\n5. Cook until bubbles form, then flip.\n6. Serve with maple syrup and fresh fruit.",
            category = "Breakfast"
        ),
        Recipe(
            title = "Caesar Salad",
            time = "10 mins",
            shortDesc = "Crispy classic Caesar salad",
            image = R.drawable.salad,
            ingredients = "• Romaine lettuce\n• Croutons\n• Parmesan cheese\n• Caesar dressing\n• Lemon juice\n• Black pepper",
            fullRecipe = "1. Wash and chop romaine lettuce.\n2. Toss with Caesar dressing.\n3. Add croutons and parmesan.\n4. Squeeze lemon juice on top.\n5. Season with black pepper and serve immediately.",
            category = "Healthy"
        ),
        Recipe(
            title = "Tomato Soup",
            time = "30 mins",
            shortDesc = "Creamy homemade tomato soup",
            image = R.drawable.soup,
            ingredients = "• 6 tomatoes\n• 1 onion\n• Garlic\n• Vegetable broth\n• Heavy cream\n• Olive oil\n• Basil\n• Salt & pepper",
            fullRecipe = "1. Sauté onion and garlic in olive oil.\n2. Add chopped tomatoes and cook 10 mins.\n3. Pour in vegetable broth.\n4. Simmer for 15 mins.\n5. Blend until smooth.\n6. Stir in cream and season.\n7. Serve hot with bread.",
            category = "Healthy"
        ),
        Recipe(
            title = "Grilled Steak",
            time = "25 mins",
            shortDesc = "Perfectly grilled beef steak",
            image = R.drawable.steak,
            ingredients = "• 2 ribeye steaks\n• Salt & black pepper\n• Garlic powder\n• Rosemary\n• Butter\n• Olive oil",
            fullRecipe = "1. Season steaks generously with salt, pepper, garlic powder.\n2. Let rest at room temperature 20 mins.\n3. Heat grill pan on high with olive oil.\n4. Sear 3–4 mins each side for medium.\n5. Add butter and rosemary, baste steak.\n6. Rest 5 mins before slicing.",
            category = "Grill"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerView = findViewById(R.id.recyclerView)
        val fabToggle = findViewById<FloatingActionButton>(R.id.fabToggle)
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)

        adapter = RecipeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setFullList(recipeList)

        // build chips dynamically from categories
        val categories = listOf("All") + recipeList.map { it.category }.distinct()
        categories.forEach { category ->
            val chip = Chip(this)
            chip.text = category
            chip.isCheckable = true
            chip.isChecked = category == "All"  // All selected by default
            chipGroup.addView(chip)
        }

        // filter by chip selection
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                // prevent no chip being selected — reselect All
                (group.getChildAt(0) as Chip).isChecked = true
                return@setOnCheckedStateChangeListener
            }
            val selectedChip = findViewById<Chip>(checkedIds[0])
            currentCategory = selectedChip.text.toString()
            applyFilter()
        }

        // FAB — cycle through List → Grid → Staggered → List
        fabToggle.setOnClickListener {
            when {
                !isGridLayout && !isStaggered -> {
                    // switch to Grid
                    isGridLayout = true
                    recyclerView.layoutManager = GridLayoutManager(this, 2)
                    fabToggle.setImageResource(android.R.drawable.ic_dialog_info)
                    Snackbar.make(recyclerView, "Switched to Grid view", Snackbar.LENGTH_SHORT).show()
                }
                isGridLayout && !isStaggered -> {
                    // switch to Staggered
                    isGridLayout = false
                    isStaggered = true
                    recyclerView.layoutManager = StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL
                    )
                    fabToggle.setImageResource(android.R.drawable.ic_menu_gallery)
                    Snackbar.make(recyclerView, "Switched to Staggered view", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    // back to List
                    isGridLayout = false
                    isStaggered = false
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    fabToggle.setImageResource(android.R.drawable.ic_dialog_dialer)
                    Snackbar.make(recyclerView, "Switched to List view", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    // combine category + search filter
    private fun applyFilter(query: String = "") {
        val filtered = recipeList.filter { recipe ->
            val matchesCategory = currentCategory == "All" || recipe.category == currentCategory
            val matchesQuery = query.isEmpty() ||
                    recipe.title.contains(query, ignoreCase = true) ||
                    recipe.shortDesc.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
        adapter.submitList(filtered)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search recipes..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                applyFilter(newText)
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                applyFilter(query)
                searchView.clearFocus()
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem) = true
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                applyFilter("")
                return true
            }
        })

        return true
    }
}