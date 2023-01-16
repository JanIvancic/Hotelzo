package com.example.hotelzo.roomViewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.R

class RoomAdapter(private val roomList:ArrayList<Room>) : RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val price : TextView = itemView.findViewById(R.id.room_price)
        val description : TextView = itemView.findViewById(R.id.room_description)
        val image : ImageView = itemView.findViewById(R.id.room_image)
        val capacity: TextView = itemView.findViewById(R.id.room_capacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.room_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.capacity.text = roomList[position].capacity.toString()
        holder.description.text = roomList[position].description
        holder.price.text = roomList[position].price.toString()

    }

    override fun getItemCount(): Int {
        return roomList.size
    }
}