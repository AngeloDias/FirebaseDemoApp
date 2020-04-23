package br.com.training.android.firebasedemoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mAuth = FirebaseAuth.getInstance()
    }

    fun loginOnEvent(view: View) {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        loginToFirebase(email, password)
    }

    fun loginToFirebase(email: String, pass: String) {
        mAuth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {task ->

            if(task.isSuccessful) {
                Toast.makeText(applicationContext,"Successful login", Toast.LENGTH_LONG).show()
                val currUser = mAuth!!.currentUser

                Log.d("Login: ", currUser!!.uid)
            } else {
                Toast.makeText(applicationContext,"Fail login", Toast.LENGTH_LONG).show()
            }

        }
    }
    
    override fun onStart() {
        super.onStart()
        val currUser = mAuth!!.currentUser

        if(currUser != null) {
            val intent = Intent(this, ControlActivity::class.java)

            startActivity(intent)
        }

    }
}
