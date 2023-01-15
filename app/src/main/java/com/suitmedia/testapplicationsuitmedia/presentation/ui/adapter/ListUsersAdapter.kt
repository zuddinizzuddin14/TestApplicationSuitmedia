package com.suitmedia.testapplicationsuitmedia.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.suitmedia.testapplicationsuitmedia.data.network.response.Data
import com.suitmedia.testapplicationsuitmedia.databinding.ItemUserBinding

class ListUsersAdapter : RecyclerView.Adapter<ListUsersAdapter.DataViewHolder>() {

    private lateinit var onUserItem: OnUserItem

    private var items: MutableList<Data> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(items : List<Data>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun actionClick(onUserItem: OnUserItem){
        this.onUserItem = onUserItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = items[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = items.size

    inner class DataViewHolder(
        private val binding : ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindingItem(item: Data?, position: Int) {
            binding.apply {
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                ivAvatar.load(item?.avatar) { transformations(CircleCropTransformation()) }
                val fullName = "${item?.firstName} ${item?.lastName}"
                tvName.text = fullName
                tvEmail.text = item?.email

                binding.root.setOnClickListener {
                    onUserItem.userClicked(fullName.trim())
                }
            }
        }
    }

    interface OnUserItem {
        fun userClicked(fullName: String)
    }

}