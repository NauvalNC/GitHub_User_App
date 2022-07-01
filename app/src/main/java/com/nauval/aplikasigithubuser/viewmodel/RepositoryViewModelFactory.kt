package com.nauval.aplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nauval.aplikasigithubuser.helper.SettingPrefs

class RepositoryViewModelFactory(
    private val app: Application?,
    private val pref: SettingPrefs?
) : ViewModelProvider.NewInstanceFactory() {

    constructor(pref: SettingPrefs) : this(null, pref)
    constructor(app: Application) : this(app, null)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java) && pref != null)
            return SettingViewModel(pref) as T
        else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) && app != null)
            return FavoriteUserViewModel(app) as T
        throw IllegalArgumentException("${modelClass.name} is not supported by this factory")
    }
}