package com.project.neglected.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.neglected.BlogFullViewActivity
import com.project.neglected.DataFiles.MainBlogDataFile
import com.project.neglected.R
import com.project.neglected.databinding.BlogListItemBinding
import kotlin.coroutines.coroutineContext

class MainBlogAdapter(
    val LinkList: ArrayList<MainBlogDataFile>,
    private var optionsMenuClickListener: OptionsMenuClickListener
) :

    RecyclerView.Adapter<MainBlogAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------

    private lateinit var binding: BlogListItemBinding

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            BlogListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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
            binding.MainClickingScreen.setOnClickListener {
                val intent = Intent(it.context, BlogFullViewActivity::class.java)
                it.context.startActivity(intent)
            }
            binding.dotsMenu.setOnClickListener {
                optionsMenuClickListener.onOptionsMenuClicked(position)
            }
        }
    }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {
        return LinkList.size
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(
        ItemViewBinding: BlogListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: MainBlogDataFile, position: Int, list: ArrayList<MainBlogDataFile>) {
            val context = itemView.getContext();
            binding.BlogTitle.text = Link.getTitle().toString()
            binding.category.text = Link.getCategory().toString()
            binding.time2.text = Link.getTime().toString()
            binding.imageView2.setImageResource(R.drawable.blog_back_photo)
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

}