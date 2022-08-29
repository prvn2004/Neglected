package com.project.neglected

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.project.neglected.fragments.LoginPage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        actionBar?.hide()

        showFragment(LoginPage())

    }
    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragLayout, fragment)
        fram.commit()
    }
}