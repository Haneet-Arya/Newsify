package com.haneetarya.newsify

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewsListAdapter( private val listener: NewsViewClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        /* Layout Inflator is used to convert xml to a view and NewsViewHolder needs a ItemView*/
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.prgrsBar.visibility=View.VISIBLE
        // Binding data with itemview
        val currentItem = items[position]
        holder.titleView.text=currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageurl).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.prgrsBar.visibility=View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {

                holder.prgrsBar.visibility=View.GONE
                return false
            }

        }).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size

    }
    fun updateNews(updatedItems: ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
        // used to notify that data is changed and all function needs to be recalled
    }


}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView= itemView.findViewById(R.id.image)
    val author: TextView= itemView.findViewById(R.id.author)
    val prgrsBar: ProgressBar=itemView.findViewById(R.id.prgrsBar)
}
interface NewsViewClicked{
    fun onItemClicked(item: News)

}