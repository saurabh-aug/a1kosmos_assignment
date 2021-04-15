package com.sample.a1kosmos.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.a1kosmos.databinding.HomeFragmentBinding
import com.sample.a1kosmos.view.adapter.UserListAdapter
import com.sampleweather.utils.Status
import com.sampleweather.utils.hide
import com.sampleweather.utils.show
import com.sampleweather.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null

    private val binding: HomeFragmentBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var userAdapter: UserListAdapter


    var page = 1
    val perPage = 10
    var totalResult = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupObserver(page, perPage)
        initView()
        attachScrollListener()

    }

    private fun initView() {
        with(binding)
        {
            recyclerView.apply {
                recyclerView.layoutManager = LinearLayoutManager(context)
                userAdapter = UserListAdapter()
                adapter = userAdapter
            }

            fab.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToFormFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun attachScrollListener() {
        binding.recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val lastVisible = layoutManager!!.findLastVisibleItemPosition()

                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    Log.d("TAG", "Fist visible item - $lastVisible")
                    Log.d("TAG", "Total count - $totalItemCount")


                    if (totalResult > totalItemCount && lastVisible >= totalItemCount - 1) {
                        page++
                        setupObserver(page, perPage)
                    }
                }
            })
    }


    private fun setupObserver(page: Int, per_page: Int) {
        Log.d("TAG", "Total page - $page")
        viewModel.getUserData(page, per_page).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.hide()
                        resource.data?.let { response ->

                            if (response.data.isNotEmpty()) {
                                resource.data.total.also { totalResult = it }
                                userAdapter.setList(response.data)
                                userAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.hide()
                        snackBar(binding.root, it.message.toString())
                    }
                    Status.LOADING -> {
                        binding.progressBar.show()
                    }
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}