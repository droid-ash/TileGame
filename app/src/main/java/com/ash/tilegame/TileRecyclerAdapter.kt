package com.ash.tilegame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ash.tilegame.databinding.ListItemBinding

class TileRecyclerAdapter(private val tileClickListener: TileClickListener) :
    RecyclerView.Adapter<TileRecyclerAdapter.TileViewHolder>() {

    private var items: MutableList<Int> = mutableListOf()

    fun setItems(items: MutableList<Int>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TileViewHolder(tileClickListener, binding)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tileNo = items[position]
        holder.passTileNo(tileNo)
    }

    override fun getItemCount(): Int = items.size

    interface TileClickListener {
        fun onTileClicked(tileNo: Int)
    }

    class TileViewHolder(
        private val tileClickListener: TileClickListener,
        private val listItemBinding: ListItemBinding
    ) :
        RecyclerView.ViewHolder(listItemBinding.root), View.OnClickListener {

        private var tileNo: Int = -1

        init {
            listItemBinding.root.setOnClickListener(this)
        }

        fun passTileNo(tileNo: Int) {
            this.tileNo = tileNo
        }

        override fun onClick(v: View?) {
            listItemBinding.text.text = tileNo.toString()
            listItemBinding.root.setBackgroundColor(
                ContextCompat.getColor(
                    listItemBinding.root.context,
                    R.color.grey
                )
            )
            tileClickListener.onTileClicked(tileNo)
            listItemBinding.root.setOnClickListener(null)
        }
    }
}