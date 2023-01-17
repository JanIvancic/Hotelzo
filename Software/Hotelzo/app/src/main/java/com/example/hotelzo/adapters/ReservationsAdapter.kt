package com.example.hotelzo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.R
import com.example.hotelzo.data.Reservations

class ReservationsAdapter(private val reservationList: ArrayList<Reservations>) : RecyclerView.Adapter<ReservationsAdapter.reservationViewHolder>() {
    class reservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.txt_name)
        val tvRoomLabel: TextView = itemView.findViewById(R.id.txt_room_label)
        val tvStartDate: TextView = itemView.findViewById(R.id.date_start)
        val tvEndDate: TextView = itemView.findViewById(R.id.date_end)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reservationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reservation_list_item, parent, false)
        return reservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: reservationViewHolder, position: Int) {
        holder.tvName.text = reservationList[position].name
        holder.tvRoomLabel.text = reservationList[position].room_label
        holder.tvStartDate.text = reservationList[position].start_date.toString()
        holder.tvEndDate.text = reservationList[position].end_date.toString()
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }
}