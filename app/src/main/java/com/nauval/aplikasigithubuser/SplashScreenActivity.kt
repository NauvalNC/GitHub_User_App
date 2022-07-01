package com.nauval.aplikasigithubuser

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.postDelayed
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.nauval.aplikasigithubuser.helper.SettingPrefs
import com.nauval.aplikasigithubuser.viewmodel.RepositoryViewModelFactory
import com.nauval.aplikasigithubuser.viewmodel.SettingViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val Context.dataStore by preferencesDataStore(name = SettingPrefs.PREF_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        handler.postDelayed(waitDelay) {
            // Load theme mode on splash screen
            ViewModelProvider(this,
                RepositoryViewModelFactory(SettingPrefs.getInstance(dataStore))
            )[SettingViewModel::class.java].getIsDarkMode().observe(this) {
                if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            startActivity(Intent(this@SplashScreenActivity, MainLandingPageActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        super.onPause()
    }

    companion object {
        private const val waitDelay = 2000L
    }
}