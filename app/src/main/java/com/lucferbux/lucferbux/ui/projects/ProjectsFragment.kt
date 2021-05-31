package com.lucferbux.lucferbux.ui.projects

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.ProjectsFragmentBinding
import com.lucferbux.lucferbux.ui.articles.ArticlesAdapter
import com.lucferbux.lucferbux.ui.articles.ArticlesViewModel
import com.lucferbux.lucferbux.ui.articles.ArticlesViewModelFactory
import com.lucferbux.lucferbux.ui.articles.PostListener


class ProjectsFragment : Fragment() {

    private lateinit var binding: ProjectsFragmentBinding
    private lateinit var viewModel: ProjectsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set ViewModel
        val application = requireNotNull(this.activity).application
        var firebase = FirestoreUtil()
        val viewModelFactory = ProjectViewModelFactory(firebase, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectsViewModel::class.java)


        // set adapter
        val adapter = ProjectsAdapter(ProjectListener { projectId ->
            viewModel.prepareLink(projectId.link)
        })


        // set binding
        binding = DataBindingUtil.inflate(inflater, R.layout.projects_fragment, container, false)
        binding.projectList.adapter = adapter
        binding.lifecycleOwner = this

        // UI
        viewModel.postData.observe(viewLifecycleOwner, { result ->
            adapter.addHeaderAndSubmitList(result)
        })

        viewModel.openLink.observe(viewLifecycleOwner, { url ->
            url?.let {
                val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                defaultBrowser.data = Uri.parse(it)
                startActivity(defaultBrowser)
                viewModel.onLinkPrepared()
            }
        })


        return binding.root
    }



}
