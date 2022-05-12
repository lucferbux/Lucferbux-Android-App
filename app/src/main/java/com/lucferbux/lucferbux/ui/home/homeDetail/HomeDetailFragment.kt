package com.lucferbux.lucferbux.ui.home.homeDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.HomeDetailFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*


class HomeDetailFragment : Fragment() {

    private lateinit var binding: HomeDetailFragmentBinding

    companion object {
        fun newInstance() = HomeDetailFragment()
    }

    private lateinit var viewModel: HomeDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // set viewmodel
        val application = requireNotNull(this.activity).application
        val firebase = FirestoreUtil()
        val viewModelFactory = HomeDetailViewModelFactory(firebase, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeDetailViewModel::class.java)

        val clickListener = LinkHandler(viewModel)


        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.home_detail_fragment, container, false)
        binding.viewModel = viewModel
        binding.clickListener = clickListener
        binding.lifecycleOwner = this

        arguments?.let {

            try {
                val args = HomeDetailFragmentArgs.fromBundle(it)
                viewModel.getDataFromFirestore(args.idIntro)
            }
            catch (e: Exception) {
                //Log.e("Error", e.toString())
            }


        }

        viewModel.navigateBack.observe(viewLifecycleOwner, Observer { data ->

            data?.let {
                //this.findNavController().popBackStack()

                this.findNavController().navigate(HomeDetailFragmentDirections.actionHomeFragmentDetailToHomeDetail())

                viewModel.onBackNavigation()
            }

        })

        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        nav_host_fragment.findNavController().navigate(HomeDetailFragmentDirections.actionHomeFragmentDetailToHomeDetail())
                        viewModel.onBackNavigation()
                    }
                })



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


    class LinkHandler(val viewModel: HomeDetailViewModel) {
        fun onLinkPressed(view: View, uri: String) {
            viewModel.prepareLink(uri)
        }

        fun onBackPressed(view: View) {
                viewModel.prepareBackNavigation("navigation")
        }
    }



}
