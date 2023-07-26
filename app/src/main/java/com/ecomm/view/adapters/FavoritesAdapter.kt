package com.ecomm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomm.ItemClickListener
import com.ecomm.R
import com.ecomm.databinding.AdapterProductBinding
import com.ecomm.models.Product
import com.ecomm.utils.Constants
import java.lang.StringBuilder

class FavoritesAdapter(onProductClickListener: ItemClickListener<Product>): RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    var products = mutableListOf<Product>()
    private val onProductClickListener: ItemClickListener<Product>?
    init {
        this.onProductClickListener = onProductClickListener
    }

    fun setProductList(products: List<Product>){
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    inner class FavoriteViewHolder(val binding: AdapterProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(product: Product) = with(binding) {
            binding.name.text = product.title

            val sb = StringBuilder()
            sb.append("$").append(product.price)

            binding.price.text = sb.toString()

            Glide.with(binding.root.context)
                .load(product.image)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(binding.productImage)

            binding.root.setOnClickListener {
                onProductClickListener?.onItemClick(products[adapterPosition])
            }

            if(products[adapterPosition].isInWishlist!!){
                Constants.selectFav(binding.fav, root.context)
            }else{
                Constants.unselectFav(binding.fav, root.context)
            }

            binding.fav.setOnClickListener {
                products[adapterPosition].isInWishlist = false
                Constants.unselectFav(binding.fav, root.context)
                notifyItemChanged(adapterPosition)
                onProductClickListener?.onFavClick(product)
            }
        }
    }
}