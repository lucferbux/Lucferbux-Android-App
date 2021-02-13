package com.lucferbux.lucferbux.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucferbux.lucferbux.data.Intro
import com.lucferbux.lucferbux.databinding.HomeCardBinding


class HomeAdapter(val clickListener: IntroListener): ListAdapter<Intro, HomeAdapter.ViewHolder>(HomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // listadapter has a method to get item
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: HomeCardBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Intro,
            clickListener: IntroListener
        ) {
            //assing value to xml and check teh binding utils

            binding.introData = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = HomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

}



class HomeDiffCallback: DiffUtil.ItemCallback<Intro>() {

    override fun areItemsTheSame(oldItem: Intro, newItem: Intro): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Intro, newItem: Intro): Boolean {
        // as SleepNight is a data class equal check all values
        return oldItem == newItem
    }


}

class IntroListener(val clickListener: (introId: Intro) -> Unit) {
    fun onclick(intro: Intro) = clickListener(intro)
}
