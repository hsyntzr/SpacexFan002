package com.example.spacexfan002

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.spacexfan002.databinding.ActivityMainBinding
import com.example.spacexfan002.favorite.FavoriteFragment
import com.example.spacexfan002.loginFragment.LoginFragment
import com.example.spacexfan002.rockets.RocketsFragment
import com.example.spacexfan002.upcoming.UpcomingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(LoginFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.rockets -> replaceFragment(RocketsFragment())
                R.id.upcoming -> replaceFragment(UpcomingFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
                else -> {
                }

            }
            true
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }

}