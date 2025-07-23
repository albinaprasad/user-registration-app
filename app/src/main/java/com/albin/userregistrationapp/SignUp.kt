package com.albin.userregistrationapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.albin.userregistrationapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    lateinit var signUpBinding: ActivitySignUpBinding
    var auth: FirebaseAuth= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        signUpBinding= ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(signUpBinding.root)

        signUpBinding.SignUpBtn.setOnClickListener {

            createUser()
        }



    }

    fun createUser()
    {
        var username=signUpBinding.username.text.toString()
        var password=signUpBinding.pass.text.toString()

        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener{

            task->

            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"sign-up sucessfull..", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(applicationContext,"Error while sign-up..", Toast.LENGTH_SHORT).show()

            }

        }
    }
}