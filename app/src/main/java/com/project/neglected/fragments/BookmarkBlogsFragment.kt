package com.project.neglected.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.neglected.Adapters.BookmarkBlogAdapter
import com.project.neglected.Adapters.MainBlogAdapter
import com.project.neglected.DataFiles.BookmarkBlogIdDataFile
import com.project.neglected.DataFiles.MainBlogDataFile
import com.project.neglected.R
import com.project.neglected.databinding.FragmentBookmarkBlogsBinding

class BookmarkBlogsFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBlogsBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<MainBlogDataFile>
    private lateinit var BlogIdModel: ArrayList<BookmarkBlogIdDataFile>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBlogsBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf<MainBlogDataFile>()
        BlogIdModel = arrayListOf<BookmarkBlogIdDataFile>()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        getBookmarkBlogId()
        getBookMarkStory()

        return view
    }

    private fun getBookMarkStory() {
//        val mFrameLayout = binding.shimmerLayout
//        mFrameLayout.startShimmerAnimation()


        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val database1 = FirebaseDatabase.getInstance().reference.child("messages")
        database1.orderByChild("MainBlogId").equalTo("2")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                binding.home.progressBar.setVisibility(View.GONE)
                    if (snapshot.exists()) {
                        LinkModel.clear()

                        for (userSnapshot in snapshot.children) {
                            val trueUser =
                                userSnapshot.getValue(MainBlogDataFile::class.java)
                            LinkModel.add(0, trueUser!!)
                        }
                        val recyclerView = binding.recyclerView
                        recyclerView.adapter = BookmarkBlogAdapter(
                            LinkModel
                        )
//                    mFrameLayout.stopShimmerAnimation()
//                    mFrameLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }


            })
    }

    private fun getBookmarkBlogId() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val database1 = FirebaseDatabase.getInstance().reference.child("SavedBlogId").child("$uid")
        database1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    BlogIdModel.clear()
                    for (userSnapshot in snapshot.children) {
                        val trueUser =
                            userSnapshot.getValue(BookmarkBlogIdDataFile::class.java)
                        BlogIdModel.add(0, trueUser!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}