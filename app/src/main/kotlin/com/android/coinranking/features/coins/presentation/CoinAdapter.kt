package com.android.coinranking.features.coins.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.coinranking.R
import kotlinx.android.synthetic.main.adapter_coin_item.view.*

class CoinAdapter(val list: List<CoinModel>): RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
    val layout = LayoutInflater.from(parent.context)
      .inflate(R.layout.adapter_coin_item, parent, false)
    return CoinViewHolder(layout)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
    val item = list.get(position)
    holder.bindItems(item)
  }

  inner class CoinViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bindItems(coin: CoinModel) {
      itemView.coin_item_name.text = coin.name
      itemView.coin_item_desc.text = coin.description
      itemView.coin_item_price.text = "${coin.price}$"
    }
  }
}