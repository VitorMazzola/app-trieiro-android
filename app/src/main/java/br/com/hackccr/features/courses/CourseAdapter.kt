package br.com.hackccr.features.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import br.com.hackccr.R
import br.com.hackccr.data.Course
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.course_item_list.view.*

class CourseAdapter(private var items: List<Course>): RecyclerView.Adapter<CourseAdapter.CustomHolder>(){

    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.course_item_list, parent, false)
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

        fun bindItems(item: Course, adapter: CourseAdapter) = with(itemView) {

            Glide.with(context).load(item.backgroundImage).into(this.ivBackground)
            this.tvDescription.text = item.description

            itemView.setOnClickListener{ adapter.onItemHolderClick(this@CustomHolder)}
        }
    }
}