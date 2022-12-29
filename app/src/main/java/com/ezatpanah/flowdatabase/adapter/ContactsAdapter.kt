package com.ezatpanah.flowdatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.flowdatabase.databinding.ItemContactsBinding
import com.ezatpanah.flowdatabase.db.ContactsEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContactsAdapter @Inject constructor() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private lateinit var binding: ItemContactsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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

    private val differCallback = object : DiffUtil.ItemCallback<ContactsEntity>() {
        override fun areItemsTheSame(oldItem: ContactsEntity, newItem: ContactsEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactsEntity, newItem: ContactsEntity): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

}