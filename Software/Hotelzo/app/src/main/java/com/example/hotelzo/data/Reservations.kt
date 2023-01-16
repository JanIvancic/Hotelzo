package com.example.hotelzo.data

import java.util.Date

data class Reservations(
    val end_date: Date? = null,
    val start_date: Date? = null,
    val name: String? = null,
    val room_label: String? = null
)
