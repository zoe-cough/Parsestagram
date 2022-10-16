package com.example.parsestagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check if a user is logged in and take to MainActivity if yes
        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.btnlogin).setOnClickListener {
            val uname = findViewById<EditText>(R.id.etusername).text.toString()
            val pword = findViewById<EditText>(R.id.etpassword).text.toString()
            loginUser(uname, pword)
        }

        findViewById<Button>(R.id.btnsignup).setOnClickListener {
            val uname = findViewById<EditText>(R.id.etusername).text.toString()
            val pword = findViewById<EditText>(R.id.etpassword).text.toString()
            signUpUser(uname, pword)
        }
    }

    private fun signUpUser(uname: String, pword: String) {
        val user = ParseUser()

        user.setUsername(uname)
        user.setPassword(pword)

        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "successfully signed up user")
                Toast.makeText(this, "Signup success!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Signup error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(uname: String, pword: String) {
        ParseUser.logInInBackground(uname, pword, ({user, e->
            if (user != null) {
                Log.i(TAG, "successfully logged in user")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}