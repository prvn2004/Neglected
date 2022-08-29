package com.project.neglected.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.neglected.BlogFullViewActivity
import com.project.neglected.DataFiles.MainBlogDataFile
import com.project.neglected.R
import com.project.neglected.databinding.BlogListItemBinding
import com.project.neglected.databinding.BookmarkBlogItemListBinding

class BookmarkBlogAdapter(
    val LinkList: ArrayList<MainBlogDataFile>,
//    private var optionsMenuClickListener: OptionsMenuClickListener
) :

    RecyclerView.Adapter<BookmarkBlogAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------

    private lateinit var binding: BookmarkBlogItemListBinding

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            BookmarkBlogItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(
            binding
        ) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        val Link = LinkList[position]
        holder.friction(Link, position, LinkList)
//        Glide.with(holder.itemView.context).load(LinkList.get(position).getLink()).centerInside()
//            .into(binding.imageView7)
        with(holder){
            with(Link){
//                binding.MainClickingScreen.setOnClickListener {
//                    val intent = Intent(it.context, BlogFullViewActivity::class.java)
//                    it.context.startActivity(intent)
//                }
//                binding.dotsMenu.setOnClickListener {
//                    optionsMenuClickListener.onOptionsMenuClicked(position)
//                }
            }
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {
        return LinkList.size
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(
        ItemViewBinding: BookmarkBlogItemListBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: MainBlogDataFile, position: Int, list: ArrayList<MainBlogDataFile>) {
            val context = itemView.getContext();
            binding.MainTitle.text = Link.getTitle().toString()
            binding.Category.text = Link.getCategory().toString()
            binding.Time.text = Link.getTime().toString()
            binding.imageView.setImageResource(R.drawable.blog_back_photo)
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

}