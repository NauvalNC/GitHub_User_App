package com.nauval.aplikasigithubuser.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nauval.aplikasigithubuser.helper.SettingPrefs
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SettingViewModelTest : TestCase() {
    private val thread = newSingleThreadContext("Test Thread")
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var pref: SettingPrefs

    private var dummyDarkMode = true
    private var dummyResultDarkModeCallback = true

    @Before
    public override fun setUp() {
        Dispatchers.setMain(thread)
        pref = mock(SettingPrefs::class.java)
        settingViewModel = spy(SettingViewModel(pref))
        super.setUp()
    }

    @After
    public override fun tearDown() {
        Dispatchers.resetMain()
        thread.close()
        super.tearDown()
    }

    @Test
    fun testSaveThemeMode() = runBlocking {
        settingViewModel.saveThemeMode(dummyDarkMode)
        verify(pref).saveThemeMode(dummyDarkMode)

        doReturn(MutableLiveData(dummyResultDarkModeCallback)).`when`(settingViewModel).getIsDarkMode()

        assertEquals(settingViewModel.getIsDarkMode().value, dummyDarkMode)
    }
}