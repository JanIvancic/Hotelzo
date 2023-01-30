package com.example.hotelzo.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.AllReservationsActivity
import com.example.hotelzo.R
import com.example.hotelzo.data.Reservations

import kotlin.collections.ArrayList


class ReservationsAdapter(private val reservationList: ArrayList<Reservations>, private val activity: AllReservationsActivity, private val gumb:Boolean,private val uloga:String) : RecyclerView.Adapter<ReservationsAdapter.reservationViewHolder>() {
    class reservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.txt_name)
        val tvRoomLabel: TextView = itemView.findViewById(R.id.txt_room)
        val tvStartDate: TextView = itemView.findViewById(R.id.date_start)
        val tvEndDate: TextView = itemView.findViewById(R.id.date_end)
        val ivDeleteButton: ImageView= itemView.findViewById(R.id.btn_delete_reservation)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reservationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View
        if (gumb || uloga== "admin") {
            itemView = inflater.inflate(R.layout.reservation_list_item, parent, false)
        } else {
            itemView = inflater.inflate(R.layout.reservation_list_item_user, parent, false)
        }
        return reservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: reservationViewHolder, position: Int) {
        holder.tvName.text = reservationList[position].name
        holder.tvRoomLabel.text = reservationList[position].room_label
        holder.tvStartDate.text = reservationList[position].start_date.toString()
        holder.tvEndDate.text = reservationList[position].end_date.toString()
        if (gumb || uloga== "admin") {
            holder.ivDeleteButton.setOnClickListener{
                activity.deleteReservation(reservationList[position].document_id)
                reservationList.removeAt(position)
                notifyDataSetChanged()
            }
        } else {
            holder.ivDeleteButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }
}