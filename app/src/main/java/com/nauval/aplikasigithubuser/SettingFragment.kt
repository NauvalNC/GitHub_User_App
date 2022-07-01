package com.nauval.aplikasigithubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nauval.aplikasigithubuser.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            MainLandingPageActivity.getSettingViewModel().let {
                it?.getIsDarkMode()?.observe(viewLifecycleOwner) { isDarkMode ->
                    themeSwitch.isChecked = isDarkMode
                    themeIcon.setImageResource(
                        if (isDarkMode) R.drawable.ic_baseline_nights_stay_24
                        else R.drawable.ic_baseline_wb_sunny_24)
                }
                themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                    MainLandingPageActivity.getSettingViewModel()?.saveThemeMode(isChecked)
                }
            }
        }
    }
}