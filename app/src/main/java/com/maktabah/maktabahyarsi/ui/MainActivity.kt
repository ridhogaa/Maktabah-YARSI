package com.maktabah.maktabahyarsi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.ActivityMainBinding
import com.maktabah.maktabahyarsi.ui.home.HomeFragment
import com.maktabah.maktabahyarsi.ui.search.SearchFragment
import com.maktabah.maktabahyarsi.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.searchFragment-> replaceFragment(SearchFragment())
                R.id.settingFragment-> replaceFragment(SettingFragment())

                else ->{

                }
            }
        }

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}