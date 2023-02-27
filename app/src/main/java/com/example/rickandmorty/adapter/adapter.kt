package com.example.rickandmorty.adapter

import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDenemeBinding
import com.example.rickandmorty.databinding.RowItemBinding
import com.squareup.picasso.Picasso


class adapter(var list: ArrayList<com.example.rickandmorty.CharacterModel.Result>) :
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


        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_deneme_to_characterView)
        }




    }

    fun getFilter(newlist: ArrayList<com.example.rickandmorty.CharacterModel.Result>) {
        list=newlist
        notifyDataSetChanged()
    }
}