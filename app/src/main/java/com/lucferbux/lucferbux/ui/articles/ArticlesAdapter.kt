package com.lucferbux.lucferbux.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.data.Intro
import com.lucferbux.lucferbux.data.Post
import com.lucferbux.lucferbux.databinding.PostCellBinding
import com.lucferbux.lucferbux.ui.home.HomeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class ArticlesAdapter(val clickListener: PostListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(HomeDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val homeItem = getItem(position) as DataItem.PostItem
                holder.bind(homeItem.post, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.PostItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<Post>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map  { DataItem.PostItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
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

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.article_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }

}



class HomeDiffCallback: DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }


}

class PostListener(val clickListener: (postId: Post) -> Unit) {
    fun onclick(post: Post) = clickListener(post)
}

sealed class DataItem {
    data class PostItem(val post: Post): DataItem() {
        override val id = post.id
    }

    object Header: DataItem() {
        override val id = "0"
    }

    abstract val id: String
}