package com.maktabah.maktabahyarsi.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenProvider ->
            val fadeOut = ObjectAnimator.ofFloat(splashScreenProvider.view, View.ALPHA, 0f)
            fadeOut.duration = 1000L // Or your desired duration
            fadeOut.doOnEnd { splashScreenProvider.remove() }
            fadeOut.start()
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.getTheme.collect { isDarkModeActive ->
                val theme = if (isDarkModeActive == true) {
                    MODE_NIGHT_YES
                } else MODE_NIGHT_NO

                setDefaultNightMode(theme)
            }
        }
        val navHostFragment =
            binding.fragmentContainerView.getFragment<Fragment>() as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> hideBottomNav(false)
                R.id.riwayatFragment -> hideBottomNav(false)
                R.id.favoriteFragment -> hideBottomNav(false)
                R.id.profileFragment -> hideBottomNav(false)
                R.id.alertKeluarDialogFragment -> hideBottomNav(false)
                else -> hideBottomNav(true)
            }
        }
    }

    private fun hideBottomNav(hide: Boolean) {
        if (hide) {
            binding.bottomNav.visibility = View.GONE
        } else {
            binding.bottomNav.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}