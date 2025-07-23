package com.albin.userregistrationapp

import android.R
import android.R.menu
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.albin.userregistrationapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var adapt: userAdapter
    var userList = ArrayList<Users>()

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference = database.reference.child("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            enableEdgeToEdge()
            mainBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mainBinding.root)

            mainBinding.floatbtn.setOnClickListener {
                val intent = Intent(this, adduser::class.java)
                startActivity(intent)
            }

            dataRetrieve()

        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate: ${e.message}")
            e.printStackTrace()
        }



    }



    fun dataRetrieve() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    userList.clear()

                    for (eachuser in snapshot.children) {
                        val data = eachuser.getValue(Users::class.java)
                        if (data != null) {
                            userList.add(data)
                        }
                    }

                    // Initialize adapter with data
                    adapt = userAdapter(this@MainActivity, userList)
                    mainBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainBinding.recyclerView.adapter = adapt

                    // Setup swipe to delete
                    setupSwipeToDelete()

                } catch (e: Exception) {

                    e.printStackTrace()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setupSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION && position < userList.size) {
                        val id = adapt.getuserId(position)
                        reference.child(id).removeValue()
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error deleting user: ${e.message}")
                }
            }
        }).attachToRecyclerView(mainBinding.recyclerView)
    }



}