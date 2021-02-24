package com.lucferbux.lucferbux.ui.aboutme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucferbux.lucferbux.data.Work
import com.lucferbux.lucferbux.databinding.AboutmeJobBinding


class AboutMeAdapter(): ListAdapter<Work, AboutMeAdapter.ViewHolder>(AboutMeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // listadapter has a method to get item
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: AboutmeJobBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Work
        ) {
            //assing value to xml and check teh binding utils
            binding.jobData = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = AboutmeJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }



}



class AboutMeDiffCallback: DiffUtil.ItemCallback<Work>() {

    override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
        // as SleepNight is a data class equal check all values
        return oldItem == newItem
    }


}

