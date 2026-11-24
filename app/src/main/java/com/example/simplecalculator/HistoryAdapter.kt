package com.example.pasteldreamcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pasteldreamcalculator.data.HistoryEntity

class HistoryAdapter(
    private val onItemClick: (HistoryEntity) -> Unit,
    private val onDeleteClick: (Long) -> Unit
) : ListAdapter<HistoryEntity, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvExpression: TextView = itemView.findViewById(R.id.tvHistoryExpression)
        private val tvResult: TextView = itemView.findViewById(R.id.tvHistoryResult)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteHistoryItem)

        fun bind(item: HistoryEntity) {
            tvExpression.text = item.expression
            tvResult.text = "= ${item.result}"

            itemView.setOnClickListener { onItemClick(item) }
            btnDelete.setOnClickListener { onDeleteClick(item.id) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HistoryEntity>() {
        override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean = oldItem == newItem
    }
}