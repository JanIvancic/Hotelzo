package com.example.hotelzo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.R
import com.bumptech.glide.Glide
import com.example.hotelzo.PregledSobeActivity
import com.example.hotelzo.data.Room


class RoomAdapter(private val roomList:ArrayList<Room>) : RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cijena_sobe : TextView = itemView.findViewById(R.id.room_price)
        val opis_sobe : TextView = itemView.findViewById(R.id.room_description)
        val image : ImageView = itemView.findViewById(R.id.room_image)
        val capacity: TextView = itemView.findViewById(R.id.room_capacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.room_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.opis_sobe.text = roomList[position].kratak_opis
        holder.capacity.text = "Kapacitet: "+roomList[position].kapacitet.toString()
        holder.cijena_sobe.text = roomList[position].cijena_sobe.toString() + "€"
        Glide.with(holder.itemView.context)
            .load(roomList[position].slika_url)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PregledSobeActivity::class.java)

            if(roomList[position].oznaka!=null){
                intent.putExtra("oznaka", roomList[position].oznaka)
            }
            if(roomList[position].opis_sobe!=null){
                intent.putExtra("opis_sobe", roomList[position].opis_sobe)
            }

            intent.putExtra("cijena_sobe", roomList[position].cijena_sobe)
            intent.putExtra("kapacitet", roomList[position].kapacitet)
            intent.putExtra("image_url", roomList[position].slika_url)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }
}