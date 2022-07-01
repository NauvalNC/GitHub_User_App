package com.nauval.aplikasigithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nauval.aplikasigithubuser.database.FavoriteUser
import com.nauval.aplikasigithubuser.databinding.FragmentFavoriteUserBinding
import com.nauval.aplikasigithubuser.helper.UserLoader
import com.nauval.aplikasigithubuser.helper.UserResponse
import com.nauval.aplikasigithubuser.viewmodel.FavoriteUserViewModel
import com.nauval.aplikasigithubuser.viewmodel.RepositoryViewModelFactory

class FavoriteUserFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var favUserViewModel: FavoriteUserViewModel

    private val resLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == UserLoader.RESULT_CODE && result.data != null) {
            result.data?.apply {
                val isStable = getBooleanExtra(UserLoader.EXTRA_LIST_MODIFIED, true)
                val pos = getIntExtra(UserLoader.EXTRA_LIST_POS, -1)
                val username = getStringExtra(UserLoader.EXTRA_USERNAME)
                if (!isStable && pos != -1) {
                    binding.rvUser.adapter?.notifyItemRemoved(pos)
                    Toast.makeText(requireContext(),
                        "@$username " + requireContext().resources.getString(R.string.removed),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(UserLoader.MAX_VIEW_CACHE)
        }

        favUserViewModel = ViewModelProvider(
            this, RepositoryViewModelFactory(requireActivity().application)
        )[FavoriteUserViewModel::class.java]
        UserLoader().apply {
            favUserViewModel.getFavUsers().observe(viewLifecycleOwner) {
                setUserListData(
                    requireContext(), convertToUserResponses(it),
                    binding.rvUser, binding.noData, resLauncher
                )
            }
        }
    }

    private fun convertToUserResponses(listUser: List<FavoriteUser>): List<UserResponse> {
        return listUser.map {
            UserResponse(it.username,
                it.roleType ?: resources.getString(R.string.temp_role),
                it.avatarURL ?: "")
        }
    }
}