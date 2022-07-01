package com.nauval.aplikasigithubuser.helper

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdleRes {
    private val idleResCount = CountingIdlingResource("global_source")
    val idleRes: IdlingResource get() = idleResCount
    fun increment() = idleResCount.increment()
    fun decrement() = idleResCount.decrement()
}