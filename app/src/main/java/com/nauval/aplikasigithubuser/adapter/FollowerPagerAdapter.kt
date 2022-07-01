package com.nauval.aplikasigithubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nauval.aplikasigithubuser.FollowerListFragment
import com.nauval.aplikasigithubuser.FollowerListFragment.Companion.PageType

class FollowerPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    lateinit var pageForUser: String

    override fun createFragment(position: Int): Fragment {
        return FollowerListFragment().apply {
            arguments = Bundle().apply {
                putString(FollowerListFragment.ARG_USERNAME, pageForUser)
                putString(
                    FollowerListFragment.ARG_FOLLOWER_TYPE,
                    PageType.values()[position].name
                )
            }
        }
    }

    override fun getItemCount(): Int = 2
}