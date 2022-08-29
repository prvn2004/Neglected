package com.project.neglected

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.neglected.databinding.ActivityBlogFullViewBinding

class BlogFullViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlogFullViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogFullViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.back.setOnClickListener {
            closeStory()
        }
        binding.shareStory.setOnClickListener {
            shareStory()
            Toast.makeText(this, "sharing story", Toast.LENGTH_SHORT).show()
        }
    }

    private fun closeStory(){
        this.finish()
    }

    private fun shareStory(){
        val shareIntent = Intent(Intent.ACTION_SEND)
        val Title = binding.mainTitle.text.toString()
        val publisher = binding.FullBlogPublisher.text.toString()
        val content = binding.mainContent.text.toString()
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Read This story - $Title, -- --  $content - $publisher")
        startActivity(Intent.createChooser(shareIntent, "Share link using"))
    }
}