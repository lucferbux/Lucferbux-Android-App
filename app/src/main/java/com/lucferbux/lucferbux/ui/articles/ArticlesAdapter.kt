package com.lucferbux.lucferbux.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucferbux.lucferbux.data.Post
import com.lucferbux.lucferbux.databinding.PostCellBinding


class ArticlesAdapter(val clickListener: PostListener): ListAdapter<Post, ArticlesAdapter.ViewHolder>(HomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: PostCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Post,
            clickListener: PostListener
        ) {
            //assing value to xml and check teh binding utils

            binding.postData = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = PostCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

}



class HomeDiffCallback: DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }


}

class PostListener(val clickListener: (postId: Post) -> Unit) {
    fun onclick(post: Post) = clickListener(post)
}
