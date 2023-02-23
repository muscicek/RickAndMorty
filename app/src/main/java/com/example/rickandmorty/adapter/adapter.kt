package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.RowItemBinding
import com.squareup.picasso.Picasso

class adapter(val list: ArrayList<com.example.rickandmorty.CharacterModel.Result>) :
    RecyclerView.Adapter<adapter.Holder>() {

    class Holder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = list.get(position).image
        Picasso.get().load(image).into(holder.binding.image)
        holder.binding.text.text = list.get(position).name

    }
}