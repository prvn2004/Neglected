package com.project.neglected

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        actionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Handler(Looper.getMainLooper()).postDelayed({
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val GoogleLog = preferences.getString("login", "out")
            if (GoogleLog.equals("Login")) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}