package com.nauval.aplikasigithubuser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.nauval.aplikasigithubuser.databinding.ActivityMainLandingPageBinding
import com.nauval.aplikasigithubuser.helper.SettingPrefs
import com.nauval.aplikasigithubuser.viewmodel.SettingViewModel
import com.nauval.aplikasigithubuser.viewmodel.RepositoryViewModelFactory
import com.nauval.aplikasigithubuser.viewmodel.UserViewModel

class MainLandingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainLandingPageBinding
    private val viewModel: UserViewModel by viewModels()
    private val Context.dataStore by preferencesDataStore(name = SettingPrefs.PREF_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apply {
            isFailed.observe(this@MainLandingPageActivity) {
                it.getContentIfNotHandled()?.let { fail ->
                    if (fail) {
                        Snackbar.make(
                            window.decorView.rootView,
                            resources.getString(R.string.failed_to_load),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        setNavigationTabs()
        setSettingSummary()
    }

    private fun setNavigationTabs() {
        val navCtrl = findNavController(R.id.bottom_nav_fragment)
        setupActionBarWithNavController(
            navCtrl,
            AppBarConfiguration.Builder(setOf(R.id.home_menu,
                R.id.search_menu,
                R.id.favorite_menu,
                R.id.setting_menu)).build()
        )
        binding.bottomNav.setupWithNavController(navCtrl)
    }

    private fun setSettingSummary() {
        SETTING_VIEW_MODEL = ViewModelProvider(
            this,
            RepositoryViewModelFactory(SettingPrefs.getInstance(dataStore))
        )[SettingViewModel::class.java]
        SETTING_VIEW_MODEL!!.getIsDarkMode().observe(this) {
            if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        @Volatile
        private var SETTING_VIEW_MODEL: SettingViewModel? = null
        fun getSettingViewModel(): SettingViewModel? = SETTING_VIEW_MODEL
    }
}