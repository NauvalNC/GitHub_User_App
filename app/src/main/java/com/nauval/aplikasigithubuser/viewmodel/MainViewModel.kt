package com.nauval.aplikasigithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nauval.aplikasigithubuser.helper.EspressoIdleRes
import com.nauval.aplikasigithubuser.helper.Event

open class MainViewModel: ViewModel() {

    private val _isFailed = MutableLiveData<Event<Boolean>>()
    val isFailed: LiveData<Event<Boolean>> = _isFailed

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun setOnLoad() {
        EspressoIdleRes.increment()
        _isFailed.value = Event(false)
        _isLoading.value = true
    }

    protected fun setFail() {
        _isFailed.value = Event(true)
    }

    protected fun setEndLoad() {
        if (!EspressoIdleRes.idleRes.isIdleNow) EspressoIdleRes.decrement()
        _isLoading.value = false
    }
}