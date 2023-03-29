package com.example.workflow_manager_frontend.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.workflow_manager_frontend.presentation.main.home.HomeFragment
import com.example.workflow_manager_frontend.presentation.main.profile.ProfileFragment
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_item_home -> {setFragment(HomeFragment()); true}
                R.id.menu_item_profile -> {setFragment(ProfileFragment()); true}
                else -> false
            }
        }
    }

    fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}