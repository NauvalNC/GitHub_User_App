package com.nauval.aplikasigithubuser.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.nauval.aplikasigithubuser.R
import com.nauval.aplikasigithubuser.UserDetailsActivity
import com.nauval.aplikasigithubuser.adapter.ListUserAdapter

class UserLoader {
    fun setUserListData(
        ctx: Context,
        listUser: List<UserResponse>,
        rvUser: RecyclerView,
        noData: View,
        resultLauncher: ActivityResultLauncher<Intent>? = null,
        replaceWithToast: Boolean = false
    ) {
        if (listUser.isEmpty()) {
            rvUser.visibility = View.GONE
            noData.visibility = View.VISIBLE
        }
        else {
            rvUser.adapter = ListUserAdapter(listUser).apply {
                setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(itemResponse: UserResponse, position: Int) {
                        if (replaceWithToast) {
                            Toast.makeText(
                                ctx, itemResponse.getAtUsername(),
                                Toast.LENGTH_SHORT).show()
                        } else {
                            if (resultLauncher != null) {
                                resultLauncher.launch(Intent(ctx,
                                    UserDetailsActivity::class.java).apply {
                                    putExtra(EXTRA_USERNAME, itemResponse.login)
                                    putExtra(EXTRA_LIST_POS, position)
                                })
                            } else {
                                ctx.startActivity(
                                    Intent(ctx,
                                        UserDetailsActivity::class.java).apply {
                                        putExtra(EXTRA_USERNAME, itemResponse.login)
                                    })
                            }
                        }
                    }
                })
            }
            noData.visibility = View.GONE
            rvUser.visibility = View.VISIBLE
        }
    }

    fun showLoadingBar(isLoading: Boolean, loadingBar: View) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @SuppressLint("SetTextI18n")
    fun setFoundInfo(ctx: Context, numOfFound: Int, foundInfo: TextView) {
        foundInfo.text = "$numOfFound " + ctx.resources.getString(R.string.found_info)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_LIST_POS = "extra_list_position"
        const val EXTRA_LIST_MODIFIED = "extra_list_modified"
        const val RESULT_CODE = 123
        const val MAX_VIEW_CACHE = 50
    }
}