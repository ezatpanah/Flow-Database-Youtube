package com.ezatpanah.flowdatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.flowdatabase.databinding.ItemContactsBinding
import com.ezatpanah.flowdatabase.db.ContactsEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContactsAdapter @Inject constructor() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private lateinit var binding: ItemContactsBinding
    private var contactsList = emptyList<ContactsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(item: ContactsEntity) {
            binding.apply {
                tvName.text = item.name
                tvPhone.text = item.phone
            }
        }
    }

    fun setData(data: List<ContactsEntity>?) {
        val contactsDiffUtil = ContactsDiffUtils(contactsList, data!!)
        val diffUtils = DiffUtil.calculateDiff(contactsDiffUtil)
        contactsList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class ContactsDiffUtils(
        private val oldItem: List<ContactsEntity>,
        private val newItem: List<ContactsEntity>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}