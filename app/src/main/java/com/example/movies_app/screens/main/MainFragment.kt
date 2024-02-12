package com.example.movies_app.screens.main
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movies_app.MAIN
import com.example.movies_app.R
import com.example.movies_app.databinding.FragmentMainBinding
import com.example.movies_app.models.Film
import com.example.movies_app.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainFragment : Fragment() {

    private var mBinding: FragmentMainBinding ?= null;
    private val binding get() = mBinding!!
    lateinit var recyclerView: RecyclerView
    private val adapter by lazy { MainAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            init()
        } else {
            showNetworkErrorSnackbar(requireView())
        }
    }

    private fun showNetworkErrorSnackbar(rootView: View) {
        val snackbar = Snackbar.make(
            rootView,
            "Нет сети. Проверьте соединение с интернетом и повторите cнова.",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Повторить") {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                init()
            } else {
                Toast.makeText(requireContext(), "Сеть все еще недоступна", Toast.LENGTH_SHORT).show()
            }
        }
        snackbar.show()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.getMovies()
        recyclerView = binding.rvMain
        recyclerView.adapter = adapter
        viewModel.myMovies.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) { // Добавлено условие для проверки успешности запроса
                val moviesModel = response.body()
                moviesModel?.let {
                    adapter.setList(it.films)
                }
            } else {
                Toast.makeText(requireContext(), "Error loading movies", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        fun clickMovie(model: Film){
            val bundle = Bundle()
            bundle.putSerializable("movie", model)
            MAIN.navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }
}