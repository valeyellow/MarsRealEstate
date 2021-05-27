package com.example.marsrealestate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marsrealestate.R
import com.example.marsrealestate.data.MarsData
import com.example.marsrealestate.databinding.PropertyItemBinding

class MarsPropertyAdapter(
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MarsPropertyAdapter.MarsPropertyViewHolder>() {
    inner class MarsPropertyViewHolder(private val binding: PropertyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = differ.currentList[position]
                        if (item != null) {
                            listener.onItemClick(item)
                        }
                    }
                }
            }
        }

        fun bind(marsData: MarsData) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(marsData.img_src)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .centerCrop()
                    .into(propertyIv)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MarsData>() {
        override fun areItemsTheSame(oldItem: MarsData, newItem: MarsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsData, newItem: MarsData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        val binding =
            PropertyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsPropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(marsData: MarsData)
    }
}