package com.example.movies_app.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies_app.MAIN
import com.example.movies_app.R
import com.example.movies_app.databinding.FragmentMainBinding
import com.example.movies_app.models.Film
import com.example.movies_app.databinding.ItemLayoutBinding // Импорт привязки представления

class MainAdapter: RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var listMovies = emptyList<Film>()
    private lateinit var viewModel: MainFragmentViewModel
    inner class MyViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        // Получение представлений через привязку представления
        val itemTitle = binding.itemTitle
        val itemDate = binding.itemDate
        val itemImg = binding.itemImg
        val itemGenres = binding.itemGenres
        // и т.д., добавьте представления, которые вам нужны
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val film = listMovies[position]
        holder.itemTitle.text = film.nameRu
        holder.itemDate.text = film.year

        // Получение списка жанров и формирование строки для отображения
        val genres = film.genres.joinToString(", ") { it.genre } // предполагается, что genres - это список объектов, у каждого объекта есть свойство "genre"
        holder.itemGenres.text = genres

        Glide.with(MAIN)
            .load(film.posterUrlPreview)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.itemImg)
    }

    override fun getItemCount(): Int {
        return  listMovies.size
    }

    fun setList(list: List<Film>){
        listMovies = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener{
            MainFragment.clickMovie(listMovies[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}