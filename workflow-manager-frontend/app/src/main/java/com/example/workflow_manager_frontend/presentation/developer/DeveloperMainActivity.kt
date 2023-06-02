package com.example.workflow_manager_frontend.presentation.developer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.ActivityDeveloperMainBinding
import com.example.workflow_manager_frontend.databinding.ActivityMainBinding
import com.example.workflow_manager_frontend.presentation.main.home.HomeFragment
import com.example.workflow_manager_frontend.presentation.main.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeveloperMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_item_home -> {setFragment(HomeFragment()); true}
                R.id.menu_item_profile -> {setFragment(ProfileFragment()); true}
                R.id.menu_item_groups -> {setFragment(ProfileFragment()); true}
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