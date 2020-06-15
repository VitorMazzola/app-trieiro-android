package br.com.hackccr.features.maps.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import br.com.hackccr.R
import br.com.hackccr.data.Comment
import kotlinx.android.synthetic.main.comment_item_list.view.*

class CommentsAdapter(private var items: List<Comment>): RecyclerView.Adapter<CommentsAdapter.CustomHolder>(){

    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.comment_item_list, parent, false)
        return CustomHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bindItems(items[position], this)
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    private fun onItemHolderClick(itemHolder: CustomHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(null, itemHolder.itemView,
                itemHolder.adapterPosition, itemHolder.itemId)
        }
    }

    inner class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(item: Comment, adapter: CommentsAdapter) = with(itemView) {

            this.tvTitle.text = item.title
            this.ratingBar.rating = item.rating.toFloat()
            this.tvUser.text = item.userName
            this.tvDate.text = item.date
            this.tvComment.text = item.comment

            itemView.setOnClickListener{ adapter.onItemHolderClick(this@CustomHolder)}
        }
    }
}