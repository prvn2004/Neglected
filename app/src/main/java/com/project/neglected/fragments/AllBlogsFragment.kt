package com.project.neglected.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.neglected.Adapters.MainBlogAdapter
import com.project.neglected.DataFiles.BookmarkBlogIdDataFile
import com.project.neglected.DataFiles.MainBlogDataFile
import com.project.neglected.R
import com.project.neglected.databinding.FragmentAllBlogsBinding


class AllBlogsFragment : Fragment() {
    private lateinit var binding: FragmentAllBlogsBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<MainBlogDataFile>
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBlogsBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance().reference

        LinkModel = arrayListOf<MainBlogDataFile>()
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.imageView.setImageResource(R.drawable.tax_room)

        getMessages()
        val view = binding.root

        return view
    }

    private fun getMessages() {
        val mFrameLayout = binding.shimmerLayout
        mFrameLayout.startShimmer()
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val database1 = FirebaseDatabase.getInstance().reference.child("messages")
        database1.orderByChild("MainBlogTime").addValueEventListener(object : ValueEventListener {
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
                    recyclerView.adapter = MainBlogAdapter(
                        LinkModel,
                        object : MainBlogAdapter.OptionsMenuClickListener {
                            // implement the required method
                            override fun onOptionsMenuClicked(position: Int) {
                                // this method will handle the onclick options click
                                // it is defined below
                                performOptionsMenuClick(position, LinkModel)
                            }
                        })
                    mFrameLayout.stopShimmer()
                    mFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })
    }

    private fun performOptionsMenuClick(position: Int, LinkModel: ArrayList<MainBlogDataFile>) {
        // create object of PopupMenu and pass context and view where we want
        // to show the popup menu
        val popupMenu =
            PopupMenu(activity, binding.recyclerView[position].findViewById(R.id.dots_menu))
        // add the menu
        popupMenu.inflate(R.menu.item_popup_menu)
        // implement on menu item click Listener
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.Share -> {
                        Toast.makeText(activity, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    // in the same way you can implement others
                    R.id.Bookmark -> {
                        userNewBookmarks(2)
                        Toast.makeText(activity, "Item Bookmarked", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.Report -> {
                        // define
                        Toast.makeText(activity, "Item 3 clicked", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

    private fun userNewBookmarks(MainBlogId: Int) {
        val BlogId1 = BookmarkBlogIdDataFile(MainBlogId)
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        database.child("Users").child("SavedBlogId").child("$uid").setValue(BlogId1)
    }

}