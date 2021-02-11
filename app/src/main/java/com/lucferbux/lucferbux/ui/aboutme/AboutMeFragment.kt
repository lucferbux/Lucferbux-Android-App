package com.lucferbux.lucferbux.ui.aboutme

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lucferbux.lucferbux.R
import java.lang.Exception


class AboutMeFragment : Fragment() {

    companion object {
        fun newInstance() =
            AboutMeFragment()
    }

    private lateinit var viewModel: AboutMeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_me_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutMeViewModel::class.java)

        arguments?.let {

            try {
                val args = AboutMeFragmentArgs.fromBundle(it)

                Toast.makeText(context, "Test ARgument: ${args.argTest}", Toast.LENGTH_LONG).show()
            }
            catch (e: Exception) {
                Log.e("Error", e.toString())
            }


        }




        // TODO: Use the ViewModel
    }

}
