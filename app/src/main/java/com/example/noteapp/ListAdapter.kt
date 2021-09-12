package com.example.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private val mContext : Context,
    private val mOnProductClickedListener: OnProductClickedListener,
    private  val mProductList: ArrayList<Model> = ArrayList()
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val productName : TextView = itemView.findViewById(R.id.product_name)
        val productPrice : TextView = itemView.findViewById(R.id.product_price)
        val productDelete : ImageView = itemView.findViewById(R.id.delete_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list,parent,false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val model = mProductList[position]

            model.price = model.price.substringAfterLast("")
            mOnProductClickedListener.onUpdate(position,model)
        }

        holder.productDelete.setOnClickListener {
            val position = holder.adapterPosition
            val model = mProductList[position]
            mOnProductClickedListener.onDelete(model)
        }

        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = mProductList[position]

        holder.productName.text = product.name
        holder.productPrice.text = "Rs. ${product.price}"
    }

    override fun getItemCount(): Int {

        return mProductList.size
    }

    fun addProduct(model: Model){
        mProductList.add(model)
        notifyItemInserted(mProductList.size)
    }

    fun updateProduct(model : Model?){

        if (model == null) return

        for (item in mProductList) {
            if (item.id == model.id){
                val position = mProductList.indexOf(model)
                mProductList[position] = model
                notifyItemChanged(position)
                break
            }
        }
    }

    fun removeProduct(model: Model){
        val position = mProductList.indexOf(model)
        mProductList.remove(model)
        notifyItemRemoved(position)
    }

    fun getNextItemId() : Int {
        var id = 1
        if(mProductList.isNotEmpty()){
            id = mProductList.last().id + 1
        }
        return id
    }

}