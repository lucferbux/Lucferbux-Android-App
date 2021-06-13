package com.lucferbux.lucferbux.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.HomeFragmentBinding


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // set viewmodel
        val application = requireNotNull(this.activity).application
        val firebase = FirestoreUtil()
        val viewModelFactory = HomeViewModelFactory(firebase, application)
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // set adapter
        val adapter = HomeAdapter(IntroListener { introId ->
            homeViewModel.prepareDetailNavigation(introId)
        })

        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        // Grid Layout
        val manager = GridLayoutManager(activity, resources.getInteger(R.integer.num_columns))
        binding.dealsList.layoutManager = manager
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> resources.getInteger(R.integer.num_columns)
                else -> 1
            }

        }


        binding.dealsList.adapter = adapter
        binding.lifecycleOwner = this

        // UI
        homeViewModel.introData.observe(viewLifecycleOwner, Observer { result ->
            adapter.addHeaderAndSubmitList(result)
        })

        homeViewModel.navigateToHomeDetail.observe(viewLifecycleOwner, Observer { intro ->

            intro?.let {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment(intro.id))
                homeViewModel.onHomeDeatilNavigated()
            }

        })
        return binding.root
    }




}
