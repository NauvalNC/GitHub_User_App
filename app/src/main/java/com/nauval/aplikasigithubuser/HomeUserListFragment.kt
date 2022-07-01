package com.nauval.aplikasigithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nauval.aplikasigithubuser.databinding.FragmentHomeUserListBinding
import com.nauval.aplikasigithubuser.helper.UserLoader
import com.nauval.aplikasigithubuser.viewmodel.UserViewModel

class HomeUserListFragment : Fragment() {

    private lateinit var binding: FragmentHomeUserListBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setItemViewCacheSize(UserLoader.MAX_VIEW_CACHE)
        }

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        UserLoader().let { loader ->
            viewModel.apply {
                userList.observe(viewLifecycleOwner) {
                    loader.setUserListData(requireContext(), it, binding.rvUser, binding.noData)
                }
                isLoading.observe(viewLifecycleOwner) {
                    loader.showLoadingBar(it, binding.loadingBar)
                }
            }
        }
    }
}