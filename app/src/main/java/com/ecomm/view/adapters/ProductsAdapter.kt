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

class ProductsAdapter(onProductClickListener: ItemClickListener<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    var products = mutableListOf<Product>()
    private val onProductClickListener: ItemClickListener<Product>?

    init {
        this.onProductClickListener = onProductClickListener
    }

    fun setProductList(products: List<Product>) {
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductBinding.inflate(inflater, parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    inner class ProductsViewHolder(val binding: AdapterProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bindItems(product: Product) = with(binding) {

                binding.name.text = product.title

                val sb = StringBuilder()
                sb.append("$").append(product.price.toString())

                binding.price.text = sb.toString()

                Glide.with(binding.root.context)
                    .load(product.image)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(binding.productImage)

               //check db to init fav icon

                if(product.isInWishlist){
                    Constants.selectFav(binding.fav, root.context)
                }else{
                    Constants.unselectFav(binding.fav, root.context)
                }

                binding.fav.setOnClickListener {
                    if(product.isInWishlist){
                        product.isInWishlist = false
                        Constants.unselectFav(binding.fav, root.context)
                    }else{
                        product.isInWishlist = true
                        Constants.selectFav(binding.fav, root.context)
                    }

                    notifyItemChanged(adapterPosition)
                    onProductClickListener?.onFavClick(product)
                }

                binding.root.setOnClickListener {
                    onProductClickListener?.onItemClick(product)
                }
            }
    }
}