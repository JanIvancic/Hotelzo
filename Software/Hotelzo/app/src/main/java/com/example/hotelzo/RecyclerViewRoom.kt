package com.example.hotelzo


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.adapters.RoomAdapter
import com.example.hotelzo.data.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RecyclerViewRoom : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomList: ArrayList<Room>
    private lateinit var db: FirebaseFirestore
    private var filteredRoomList = arrayListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_list)
        val btnResetFilters = findViewById<ImageView>(R.id.imageView_reset_filter)
        val floatButton: FloatingActionButton = findViewById(R.id.floating_action_button)

        floatButton.setOnClickListener {
            showOptions(floatButton,it)
        }

        recyclerView = findViewById(R.id.recycler_view_rooms)
        recyclerView.layoutManager = LinearLayoutManager(this)

        roomList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("Soba").get().addOnSuccessListener {
            if(!it.isEmpty){
                for (data in it.documents){
                    val room: Room? = data.toObject(Room::class.java)
                    if (room != null) {
                        roomList.add(room)

                    }
                }
                recyclerView.adapter = RoomAdapter(roomList)
            }

            val priceFilterSpinner = findViewById<Spinner>(R.id.price_filter_spinner)
            var filteredByCapacity = roomList
            var filteredByPrice = roomList
            var selectedCapacity = ""
            var selectedPrice= ""
            val options = arrayOf("Sve", "1", "2", "3")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinner = findViewById<Spinner>(R.id.capacity_filter_spinner)
            spinner.adapter = adapter

            spinner.setSelection(0)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }


                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCapacity = options[position]

                    if (selectedCapacity != "Sve") {

                        filteredByCapacity= roomList.filter { it.kapacitet == selectedCapacity.toInt() } as ArrayList<Room>
                    } else {
                        filteredByCapacity = roomList
                    }
                    filteredRoomList = filteredByCapacity
                    recyclerView.adapter = RoomAdapter(filteredRoomList)

                }
            }


            val priceRanges = arrayOf("Sve", "50€ - 75€", "75€ - 100€", "100€ - 125€", "125€ - 150€")
            val priceRangesMap = mapOf("50€ - 75€" to 50..75, "75€ - 100€" to 75..100, "100€ - 125€" to 100..125, "125€ - 150€" to 125..150)
            val adapterPrice = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceRanges)
            adapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            priceFilterSpinner.adapter = adapterPrice
            priceFilterSpinner.setSelection(0)

            priceFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected (parent: AdapterView<*>?) {

                }


                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    fun filterByPrice(roomList: ArrayList<Room>, priceRange: IntRange): ArrayList<Room> {
                        return ArrayList(roomList.filter { it.cijena_sobe in priceRange })
                    }

                    selectedPrice = priceRanges[position]


                    if (selectedPrice != "Sve") {
                        if(selectedCapacity != "Sve"){
                            filteredByPrice= filterByPrice(filteredByCapacity, priceRangesMap[selectedPrice]!!)
                            filteredRoomList = filteredByPrice
                        }else{
                            filteredRoomList= filterByPrice(roomList, priceRangesMap[selectedPrice]!!)
                        }

                    } else {
                        filteredRoomList = roomList
                    }
                    recyclerView.adapter = RoomAdapter(filteredRoomList)
                }
            }

            btnResetFilters.setOnClickListener{
                spinner.setSelection(0)
                selectedCapacity = ""
                priceFilterSpinner.setSelection(0)
                filteredRoomList = roomList
                recyclerView.adapter = RoomAdapter(filteredRoomList)
            }
        }
            .addOnFailureListener {
                Toast.makeText( this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    //Jan Ivancic
    private fun showOptions(floatingActionButton: FloatingActionButton, itemView: View) {
        val popupMenu = PopupMenu(itemView.context as AppCompatActivity, floatingActionButton)
        popupMenu.inflate(R.menu.popup_izbornik)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item_1 -> {
                    val intent = Intent(this, AllReservationsActivity::class.java)
                    startActivity(intent)
                    return@OnMenuItemClickListener true
                }
                R.id.menu_item_2 -> {
                    logoutUser()
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener false
                }
            }
        })
        popupMenu.show()
    }

    //Jan Ivancic
    private fun logoutUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}