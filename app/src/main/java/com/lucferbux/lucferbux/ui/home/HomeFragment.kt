package com.lucferbux.lucferbux.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.HomeFragmentBinding


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // set adapter
        val adapter = HomeAdapter(IntroListener { introId ->
            Toast.makeText(context, "${introId} selected", Toast.LENGTH_LONG).show()
        })

        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.dealsList.adapter = adapter
        binding.lifecycleOwner = this

        // set viewmodel
        val application = requireNotNull(this.activity).application
        val firebase = FirestoreUtil()
        val viewModelFactory = HomeViewModelFactory(firebase, application)
        val homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)


        // UI
        homeViewModel.introData.observe(this, Observer { result ->
            adapter.submitList(result)
        })
        return binding.root
    }



}
