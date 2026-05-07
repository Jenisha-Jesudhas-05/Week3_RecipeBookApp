package com.example.recipebookapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback()) {
    private var fullList: List<Recipe> = emptyList()

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgRecipe)
        val title: TextView = view.findViewById(R.id.txtTitle)
        val time: TextView = view.findViewById(R.id.txtTime)
        val desc: TextView = view.findViewById(R.id.txtShortDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)

        holder.title.text = recipe.title
        holder.time.text = "⏱ ${recipe.time}"
        holder.desc.text = recipe.shortDesc
        holder.image.setImageResource(recipe.image)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("title", recipe.title)
            intent.putExtra("time", recipe.time)
            intent.putExtra("ingredients", recipe.ingredients)
            intent.putExtra("fullRecipe", recipe.fullRecipe)
            intent.putExtra("image", recipe.image)
            context.startActivity(intent)
        }
    }

    fun setFullList(list: List<Recipe>) {
        fullList = list
        submitList(list)
    }

    fun filter(query: String) {
        val filtered = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                        recipe.shortDesc.contains(query, ignoreCase = true)
            }
        }
        submitList(filtered)
    }
}