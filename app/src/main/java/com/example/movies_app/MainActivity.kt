package com.example.movies_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movies_app.databinding.ActivityMainBinding
import com.example.movies_app.databinding.FragmentMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding?= null;
    private val binding get() = mBinding!!
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MAIN = this
        navController = Navigation.findNavController(this, R.id.nav_host)

        // Показать Toolbar по умолчанию
        supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    // Метод для скрытия Toolbar
    fun hideToolbar() {
        supportActionBar?.hide()
    }

    // Метод для отображения Toolbar
    fun showToolbar() {
        supportActionBar?.show()
    }
}