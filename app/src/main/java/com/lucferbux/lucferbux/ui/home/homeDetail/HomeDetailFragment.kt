package com.lucferbux.lucferbux.ui.home.homeDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.lucferbux.lucferbux.R
import java.lang.Exception

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
        viewModel = ViewModelProvider(this).get(HomeDetailViewModel::class.java)

        arguments?.let {

            try {
                val args = HomeDetailFragmentArgs.fromBundle(it)

                Toast.makeText(context, "Test ARgument: ${args.argTest}", Toast.LENGTH_LONG).show()
            }
            catch (e: Exception) {
                Log.e("Error", e.toString())
            }


        }
    }

}
