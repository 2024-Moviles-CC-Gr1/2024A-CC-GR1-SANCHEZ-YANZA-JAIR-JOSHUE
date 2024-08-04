package com.example.linkedln

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val postList: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.bind(currentPost)
    }

    override fun getItemCount(): Int = postList.size

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val likeCountTextView: TextView = itemView.findViewById(R.id.like_count_text_view)
        private val likeImageView: ImageView = itemView.findViewById(R.id.like_image_view)

        fun bind(post: Post) {
            likeCountTextView.text = post.likeCount.toString()
            updateLikeIcon(post.isLiked)

            likeImageView.setOnClickListener {
                post.isLiked = !post.isLiked
                post.likeCount += if (post.isLiked) 1 else -1
                notifyItemChanged(adapterPosition)
            }
        }

        private fun updateLikeIcon(isLiked: Boolean) {
            if (isLiked) {
                likeImageView.setImageResource(R.drawable.like1)
            } else {
                likeImageView.setImageResource(R.drawable.like2)
            }
        }
    }
}
