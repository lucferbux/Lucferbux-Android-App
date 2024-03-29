package com.lucferbux.lucferbux.ui.articles

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.ArticlesFragmentBinding


class ArticlesFragment : Fragment() {

    private lateinit var binding: ArticlesFragmentBinding
    private lateinit var viewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set ViewModel
        val application = requireNotNull(this.activity).application
        var firebase = FirestoreUtil()
        val viewModelFactory = ArticlesViewModelFactory(firebase, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArticlesViewModel::class.java)


        // set adapter
        val adapter = ArticlesAdapter(PostListener { postId ->
            viewModel.prepareLink(postId.link)
        })


        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.articles_fragment, container, false)
        binding.postList.adapter = adapter
        binding.lifecycleOwner = this

        // UI
        viewModel.postData.observe(viewLifecycleOwner, { result ->
            adapter.addHeaderAndSubmitList(result)
        })

        viewModel.openLink.observe(viewLifecycleOwner) { url ->
            url?.let {
                val webpage: Uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                try {
                    startActivity(intent)
                    viewModel.onLinkPrepared()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error, no browser provided", Toast.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root
    }



}
