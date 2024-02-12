package com.example.movies_app.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies_app.MAIN
import com.example.movies_app.MainActivity
import com.example.movies_app.R
import com.example.movies_app.databinding.FragmentDetailBinding
import com.example.movies_app.databinding.FragmentMainBinding
import com.example.movies_app.models.Film
import com.example.movies_app.screens.favorite.FavoriteFragmentViewModel
import com.example.movies_app.screens.main.MainAdapter

class DetailFragment : Fragment() {

    private var mBinding: FragmentDetailBinding?= null;
    private val binding get() = mBinding!!

    lateinit var currentMovie: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        currentMovie = arguments?.getSerializable("movie") as Film
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getFilmDescriptionAndGenresAndCountry(currentMovie.filmId)
        viewModel.myDescription.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                val filmDescription = response.body()
                filmDescription?.let {
                    binding.tvDescription.text = it.description
                }
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки описания", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myGenres.observe(viewLifecycleOwner, Observer { genres ->
            // Обновление интерфейса DetailFragment с полученными жанрами
            // Пример: установка списка жанров в TextView
            binding.tvGenres.text = genres.joinToString(", ")
        })

        viewModel.myCountries.observe(viewLifecycleOwner, Observer { countries ->
            // Обновление интерфейса DetailFragment с полученными странами
            // Пример: установка списка стран в TextView
            binding.tvCountry.text = countries.joinToString(", ")
        })
        // Находим иконку назад и назначаем ей слушатель кликов
        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        // Скрыть Toolbar при открытии DetailFragment
        (activity as? MainActivity)?.hideToolbar()
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        Glide.with(MAIN)
            .load(currentMovie.posterUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgDetail)
        binding.tvTitle.text = currentMovie.nameRu
        binding.tvDate.text = currentMovie.year
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Показать Toolbar при закрытии DetailFragment
        (activity as? MainActivity)?.showToolbar()
        mBinding = null
    }
}