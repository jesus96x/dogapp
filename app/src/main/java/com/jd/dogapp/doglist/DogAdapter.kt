package com.jd.dogapp.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jd.dogapp.R
import com.jd.dogapp.models.Dog
import com.jd.dogapp.doglist.DogAdapter.DogViewHolder
import com.jd.dogapp.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.bind(dog)
    }

    inner class DogViewHolder(val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        fun bind(dog: Dog)
        {
            if(dog.inCollection)
            {
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context, R.drawable.dog_list_item_background)
                binding.dogImage.visibility = View.VISIBLE
                binding.dogIndex.visibility = View.GONE

                binding.dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }

                binding.dogImage.load(dog.imageUrl)
            }
            else
            {
                binding.dogImage.visibility = View.GONE
                binding.dogIndex.visibility = View.VISIBLE
                binding.dogIndex.text = dog.index.toString()
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context, R.drawable.dog_list_item_null_background)

            }

        }
    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit)
    {
        this.onItemClickListener = onItemClickListener
    }

}