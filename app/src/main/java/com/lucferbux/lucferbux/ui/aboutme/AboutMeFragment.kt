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
import com.lucferbux.lucferbux.FirestoreUtil
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

        val application = requireNotNull(this.activity).application
        val firebase = FirestoreUtil()
        val viewModelFactory = AboutMeViewModelFactory(firebase, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AboutMeViewModel::class.java)

        // set adapter
        val adapter = AboutMeAdapter()

        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.about_me_fragment, container, false)
        binding.aboutmeWorkAdapter.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // UI

        viewModel.workData.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }

        viewModel.openLink.observe(viewLifecycleOwner, Observer { data ->

            data?.let {
                val webpage: Uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                try {
                    startActivity(intent)
                    viewModel.onLinkPrepared()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error, no browser provided", Toast.LENGTH_SHORT).show()
                }
            }

        })





        return binding.root
    }



}
