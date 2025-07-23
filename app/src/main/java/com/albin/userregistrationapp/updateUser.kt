package com.albin.userregistrationapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albin.userregistrationapp.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class updateUser : AppCompatActivity() {

    lateinit var updateBinding: ActivityUpdateUserBinding
    var database: FirebaseDatabase= FirebaseDatabase.getInstance()
    var reference: DatabaseReference=database.reference.child("Users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        updateBinding= ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        getAndSetData()

        updateBinding.UpdateBtn.setOnClickListener {

            val updatedName=updateBinding.updatenameET.text.toString()
            val updatedMail=updateBinding.updatemailET.text.toString()
            val updatedAddress=updateBinding.updateaddressET.text.toString()
            var id=intent.getStringExtra("id")

            var userMap=mutableMapOf<String, Any>()

            userMap["address"]=updatedAddress
            userMap["email"]=updatedMail
            userMap["userID"]=id.toString()
            userMap["username"]=updatedName


            reference.child(id.toString()).updateChildren(userMap)


            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
    fun getAndSetData(){
        var name=intent.getStringExtra("name")
        var id=intent.getStringExtra("id")
        var mail=intent.getStringExtra("mail")
        var address=intent.getStringExtra("address")


        updateBinding.updatenameET.setText(name.toString())
        updateBinding.updatemailET.setText(mail.toString())
        updateBinding.updateaddressET.setText(address.toString())
    }
}