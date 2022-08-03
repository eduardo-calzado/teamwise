package es.eduardocalzado.teamwise.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.ViewListHeaderBinding
import es.eduardocalzado.teamwise.databinding.ViewListTwoLineItemOverlineBinding
import es.eduardocalzado.teamwise.domain.InfoItem
import es.eduardocalzado.teamwise.framework.px

class CustomListAdapter: ListAdapter<InfoItem, RecyclerView.ViewHolder>(DiffCallback()) {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return HeaderViewHolder(parent.inflate(R.layout.view_list_header, false))
        }
        return ItemViewHolder(parent.inflate(R.layout.view_list_two_line_item_overline, false))
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {
        if (holderItem is ItemViewHolder)
            holderItem.bind(getItem(position))
        if (holderItem is HeaderViewHolder)
            holderItem.bind(getItem(position))
    }

    open class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewListTwoLineItemOverlineBinding.bind(view)
        fun bind(info: InfoItem) {
            binding.overlineTitle.text = info.title
            binding.item.text = info.value
            if (info.isNested) {
                binding.layout.updatePadding (32.px, 0,0, 0)
            }
        }
    }

    open class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewListHeaderBinding.bind(view)
        fun bind(info: InfoItem) {
            binding.header.text = info.title
            if (info.isNested) {
                binding.layout.updatePadding(16.px, 0,0, 0)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).isHeader)
            return TYPE_HEADER
        return TYPE_ITEM
    }

    private class DiffCallback: DiffUtil.ItemCallback<InfoItem>() {
        override fun areItemsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem.getId() == newItem.getId()
        }
        override fun areContentsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem == newItem
        }
    }
}

