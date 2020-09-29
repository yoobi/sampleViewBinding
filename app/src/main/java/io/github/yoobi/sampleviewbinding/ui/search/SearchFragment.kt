package io.github.yoobi.sampleviewbinding.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.yoobi.sampleviewbinding.R
import io.github.yoobi.sampleviewbinding.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = SearchAdapter(SearchAdapter.SearchOnClickListener { text ->
            binding.searchSearchview.apply {
                setQuery(text, true)
                clearFocus()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearchHistory.adapter = searchAdapter
        binding.searchSearchview.apply {
            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if(hasFocus) viewModel.getTerms()
            }

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { queryText ->
                        viewModel.insertTerm(queryText)
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToHomeFragment(queryText))
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        viewModel.termList.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it.map { searchEntity -> searchEntity.searchText })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchHistory.adapter = null
    }
}
