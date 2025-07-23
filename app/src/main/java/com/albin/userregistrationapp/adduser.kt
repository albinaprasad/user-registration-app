package com.albin.userregistrationapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albin.userregistrationapp.databinding.ActivityAdduserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class adduser : AppCompatActivity() {

   lateinit var adduserBinding: ActivityAdduserBinding
   var database: FirebaseDatabase=FirebaseDatabase.getInstance()
    var reference: DatabaseReference=database.reference.child("Users")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        adduserBinding= ActivityAdduserBinding.inflate(layoutInflater)
        setContentView(adduserBinding.root)

adduserBinding.SignInBtn.setOnClickListener {

    var name=adduserBinding.nameET.text.toString()
    var email=adduserBinding.mailET.text.toString()
    var address=adduserBinding.addressET.text.toString()

    var id :String=reference.push().key.toString()

    val user=Users(id,name,email,address)

//add data to the firebase
    reference.child(id).setValue(user)
    finish()
    }


    }
}