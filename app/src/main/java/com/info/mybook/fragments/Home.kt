package com.info.mybook.fragments

import UserAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.mybook.R
import com.info.mybook.databinding.FragmentHomeBinding
import com.info.mybook.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: UserViewModel by viewModels()
    //private val networkViewModel: NetWorkConnectionViewModel by viewModels()
    private lateinit var adapter : UserAdapter

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize NavController
        //val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //navController = navHostFragment.navController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout using Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Get the ViewModel from the parent activity
        //binding.viewModel = (activity as? MainActivity)?.viewModelStore

        // Set the lifecycle owner for LiveData to observe changes
        binding.lifecycleOwner = viewLifecycleOwner

        binding.fab.setOnClickListener(View.OnClickListener {
            //navController.navigate(R.id.secondFragment)
            findNavController().navigate(R.id.profileFragment)

//            val dialogFragment = CustomDialogFragment()
//            dialogFragment.show(childFragmentManager, "customDialog")
        })

        adapter = UserAdapter()
        setupRecyclerView()
        observeViewModel()

//        networkViewModel.isConnected.observe(requireActivity(), Observer { isConnected ->
//            if(isConnected) {
//                observeViewModel()
//            } else {
//                Toast.makeText(requireContext(), "Connection not found!!", Toast.LENGTH_SHORT).show()
//            }
//        })


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.userRecyclerView.setHasFixedSize(true)
        binding.userRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.userList.observe(viewLifecycleOwner, Observer { users ->
            Log.d("my Data", users[0].toString())
            adapter.submitList(users)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressView.isVisible = isLoading
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Handle error, e.g., show toast or snackbar
        })
    }

//    override fun onItemClick(user: User) {
//        TODO("Not yet implemented")
//    }
}