package com.lucferbux.lucferbux.ui.home.homeDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lucferbux.lucferbux.R

class HomeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = HomeDetailFragment()
    }

    private lateinit var viewModel: HomeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
