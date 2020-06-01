package com.umeshghimire.commons.samples.activities

import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.umeshghimire.commons.activities.BaseSimpleActivity
import com.umeshghimire.commons.adapters.MyRecyclerViewAdapter
import com.umeshghimire.commons.samples.R
import com.umeshghimire.commons.views.FastScroller
import com.umeshghimire.commons.views.MyRecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class StringsAdapter(activity: BaseSimpleActivity, var strings: MutableList<String>, recyclerView: MyRecyclerView, fastScroller: FastScroller, itemClick: (Any) -> Unit) :
        MyRecyclerViewAdapter(activity, recyclerView, fastScroller, itemClick) {

    override fun getActionMenuId() = R.menu.cab_delete_only

    override fun prepareActionMode(menu: Menu) {}

    override fun actionItemPressed(id: Int) {}

    override fun getSelectableItemCount() = strings.size

    override fun getIsItemSelectable(position: Int) = true

    override fun getItemSelectionKey(position: Int) = strings.getOrNull(position)?.hashCode()

    override fun getItemKeyPosition(key: Int) = strings.indexOfFirst { it.hashCode() == key }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createViewHolder(R.layout.list_item, parent)

    override fun onActionModeCreated() {}

    override fun onActionModeDestroyed() {}

    override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = strings[position]
        holder.bindView(item, true, true) { itemView, layoutPosition ->
            setupView(itemView, item)
        }
        bindViewHolder(holder)
    }

    override fun getItemCount() = strings.size

    private fun setupView(view: View, string: String) {
        val isSelected = selectedKeys.contains(string.hashCode())
        view.apply {
            item_frame.isSelected = isSelected
            item_name.text = string
        }
    }
}
