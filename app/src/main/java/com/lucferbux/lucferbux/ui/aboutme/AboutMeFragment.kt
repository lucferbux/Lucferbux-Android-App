package com.lucferbux.lucferbux.ui.aboutme

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.AboutMeFragmentBinding
import java.lang.Exception


class AboutMeFragment : Fragment() {

    private lateinit var viewModel: AboutMeViewModel
    private lateinit var binding: AboutMeFragmentBinding

    companion object {
        fun newInstance() =
            AboutMeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AboutMeViewModel::class.java)

        // set binding

        binding = DataBindingUtil.inflate(inflater, R.layout.about_me_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.openLink.observe(viewLifecycleOwner, Observer { data ->

            data?.let {
                val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                defaultBrowser.data = Uri.parse(it)
                startActivity(defaultBrowser)
                viewModel.onLinkPrepared()
            }

        })





        return binding.root
    }



}
