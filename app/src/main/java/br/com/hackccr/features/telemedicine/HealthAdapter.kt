package br.com.hackccr.features.telemedicine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.hackccr.R
import br.com.hackccr.data.HealthItem
import kotlinx.android.synthetic.main.health_item_list.view.*

class HealthAdapter(private var items: List<HealthItem>): RecyclerView.Adapter<HealthAdapter.CustomHolder>(){

    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.health_item_list, parent, false)
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

        fun bindItems(item: HealthItem, adapter: HealthAdapter) = with(itemView) {

            this.icon.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
            this.tvTitle.text = item.title

            itemView.setOnClickListener{ adapter.onItemHolderClick(this@CustomHolder)}
        }
    }
}