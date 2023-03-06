package com.example.rickandmorty.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.RowItemBinding
import com.example.rickandmorty.denemeDirections
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

            val name = list.get(position).name
            val status = list.get(position).status
            val species = list.get(position).species
            val locationName = list.get(position).location.name
            val image = list.get(position).image


            val action =
                denemeDirections.actionDenemeToCharacterView(
                    image,
                    name,
                    status,
                    species,
                    locationName
                )

            Navigation.findNavController(it).navigate(action)

        }


    }

    fun getFilter(newlist: ArrayList<com.example.rickandmorty.CharacterModel.Result>) {
        list = newlist
        notifyDataSetChanged()
    }
}