package com.project.neglected

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.project.neglected.databinding.ActivityHomeBinding
import com.project.neglected.fragments.AllBlogsFragment
import com.project.neglected.fragments.BookmarkBlogsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar?.hide()
        binding.bottomNaviagtion.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Blogs -> setCurrentFragment(AllBlogsFragment())

                R.id.profile -> setCurrentFragment(AllBlogsFragment())

                R.id.Bookmarks -> setCurrentFragment(BookmarkBlogsFragment())
            }
            true
        }
        setCurrentFragment(AllBlogsFragment())
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_container, fragment)
            commit()
        }
    }
}