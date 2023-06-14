package com.example.workflow_manager_frontend.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.presentation.main.home.HomeFragment
import com.example.workflow_manager_frontend.presentation.main.profile.ProfileFragment
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.ActivityMainBinding
import com.example.workflow_manager_frontend.presentation.main.notification.NotificationFragment
import com.example.workflow_manager_frontend.presentation.main.notification.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(HomeFragment())

        notificationViewModel.getAppNotificationCount().observe(this){
            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.menu_item_notifications)
            if(it ==0) {
                badge.isVisible = false
                return@observe
            }

            badge.isVisible = true
            badge.number=it
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_item_home -> {setFragment(HomeFragment()); true}
                R.id.menu_item_profile -> {setFragment(ProfileFragment()); true}
                R.id.menu_item_notifications -> {
                    setFragment(NotificationFragment());
                    true}
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