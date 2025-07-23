package com.albin.userregistrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albin.userregistrationapp.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomePageBinding
    var auth: FirebaseAuth= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        homeBinding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        //register
        homeBinding.SignUpBtn.setOnClickListener {

            val intent= Intent(this, SignUp::class.java)
            startActivity(intent)

        }
        //login
        homeBinding.SignInBtn.setOnClickListener {

            var userMail=homeBinding.username.text.toString()
            var passwrd=homeBinding.pass.text.toString()

            auth.signInWithEmailAndPassword(userMail,passwrd).addOnCompleteListener {

                task->

                if (task.isSuccessful)
                {
                    Toast.makeText(applicationContext,"LOGIN sucessfull....", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,"LOGIN Failed....", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

    override fun onStart() {
        super.onStart()

        val user=auth.currentUser

//        if (user!=null)
//        {
//            val intent= Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

    }
}