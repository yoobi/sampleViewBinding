package io.github.yoobi.sampleviewbinding.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.yoobi.sampleviewbinding.R
import io.github.yoobi.sampleviewbinding.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by lazy {
        val query = HomeFragmentArgs.fromBundle(requireArguments()).query
        val viewModelFactory = HomeViewModelFactory(query)
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val homeAdapter = HomeAdapter(HomeAdapter.MovieOnClickListener {
        Toast.makeText(context, "You clicked on ID: $it", Toast.LENGTH_SHORT).show()
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovie.layoutManager = LinearLayoutManager(context)
        binding.rvMovie.adapter = homeAdapter

        viewModel.movies.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovie.adapter = null
    }
}