package com.nauval.aplikasigithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nauval.aplikasigithubuser.helper.SettingPrefs
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPrefs) : ViewModel() {
    fun saveThemeMode(isDarkMode: Boolean) = viewModelScope.launch { pref.saveThemeMode(isDarkMode) }
    fun getIsDarkMode(): LiveData<Boolean> = pref.getIsDarkMode().asLiveData()
}