package com.nauval.aplikasigithubuser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.nauval.aplikasigithubuser.database.FavoriteUser
import com.nauval.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.nauval.aplikasigithubuser.adapter.FollowerPagerAdapter
import com.nauval.aplikasigithubuser.helper.UserDetailsResponse
import com.nauval.aplikasigithubuser.helper.UserLoader
import com.nauval.aplikasigithubuser.viewmodel.FavoriteUserViewModel
import com.nauval.aplikasigithubuser.viewmodel.RepositoryViewModelFactory
import com.nauval.aplikasigithubuser.viewmodel.UserDetailsViewModel

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailsViewModel by viewModels()
    private lateinit var favUserViewModel: FavoriteUserViewModel

    private lateinit var tUsername: String
    private var tInvokePos: Int = -1
    private var tIsFollowing = false
    private var tIsFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = resources.getString(R.string.temp_subtitle)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0f
        }

        tUsername = intent.getStringExtra(UserLoader.EXTRA_USERNAME).toString()
        tInvokePos = intent.getIntExtra(UserLoader.EXTRA_LIST_POS, -1)

        setupFavoriteFeature()
        viewModel.apply {
            getUserDetails(tUsername).observe(this@UserDetailsActivity) { setUserDetails(it) }
            isLoading.observe(this@UserDetailsActivity) {
                UserLoader().showLoadingBar(it, binding.loadingBar)
            }
            isFailed.observe(this@UserDetailsActivity) {
                it.getContentIfNotHandled()?.let { fail ->
                    if (fail) {
                        Snackbar.make(
                            window.decorView.rootView,
                            resources.getString(R.string.failed_to_load),
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        setupFollowButton()
        setupFollowerPages()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserDetails(user: UserDetailsResponse) {
        supportActionBar?.title = user.getAtUsername()

        binding.apply {
            username.text = user.getAtUsername()
            Glide.with(this@UserDetailsActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.avatarImg)

            // There is some JSON field that returns null, replace with placeholders
            name.text = user.name ?: resources.getString(R.string.temp_title)
            company.text = user.company ?: resources.getString(R.string.temp_company)
            location.text = user.location ?: resources.getString(R.string.temp_location)
            followers.text = user.followers.toString()
            followings.text = user.following.toString()
            numOfRepo.text = user.publicRepos.toString()
            followBtn.setOnClickListener { toggleFollow() }
            githubBtn.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user.getRepoURL())))
            }
            favoriteBtn.setOnClickListener {
                toggleFavorite(
                    FavoriteUser(user.login, user.type, user.avatarUrl)
                )
            }
        }
    }

    private fun setupFollowerPages() {
        val pagerAdapter = FollowerPagerAdapter(
            this@UserDetailsActivity
        ).apply { pageForUser = tUsername }

        binding.apply {
            followerPager.adapter = pagerAdapter
            TabLayoutMediator(followerTabs, followerPager) { tab, pageNum ->
                tab.text = resources.getString(PAGE_TITLES[pageNum])
            }.attach()
        }
    }

    private fun toggleFollow() {
        Toast.makeText(this@UserDetailsActivity,
            "${getToggledFollowText()} ${"@$tUsername"}",
            Toast.LENGTH_SHORT).show()
        tIsFollowing = !tIsFollowing
        setupFollowButton()
    }

    private fun setupFollowButton() {
        binding.followBtn.text = getToggledFollowText()
    }

    private fun getToggledFollowText(): String {
        return if (tIsFollowing) resources.getString(R.string.unfollow)
        else resources.getString(R.string.follow)
    }

    private fun setupFavoriteFeature() {
        favUserViewModel = ViewModelProvider(
            this, RepositoryViewModelFactory(application)
        )[FavoriteUserViewModel::class.java]

        favUserViewModel.isAFavUser(tUsername).observe(this) {
            setupFavoriteButton(it > 0)
        }
    }

    private fun toggleFavorite(user: FavoriteUser) {
        if (tIsFavorite) favUserViewModel.deleteUser(user)
        else favUserViewModel.insertUser(user)
    }

    private fun setupFavoriteButton(isFavorite: Boolean) {
        tIsFavorite = isFavorite
        binding.favoriteBtn.setImageResource(
            if (isFavorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24)
    }

    override fun onBackPressed() {
        setResult(UserLoader.RESULT_CODE, Intent().apply {
            putExtra(UserLoader.EXTRA_LIST_MODIFIED, tIsFavorite)
            putExtra(UserLoader.EXTRA_LIST_POS, tInvokePos)
            putExtra(UserLoader.EXTRA_USERNAME, tUsername)
        })
        super.onBackPressed()
    }

    companion object {
        @StringRes
        private val PAGE_TITLES = intArrayOf(R.string.followers, R.string.followings)
    }
}