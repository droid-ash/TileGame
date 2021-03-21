package com.ash.tilegame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ash.tilegame.databinding.ListItemBinding

class TileRecyclerAdapter(private val tileClickListener: TileClickListener) :
    RecyclerView.Adapter<TileRecyclerAdapter.TileViewHolder>() {

    private var items: List<Tile> = mutableListOf()

    fun setItems(items: List<Tile>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TileViewHolder(vhListener, binding)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.passTileNo(items[position])
    }

    override fun getItemCount(): Int = items.size

    private val vhListener = object : TileViewHolder.Listener {

        override fun onClicked(position: Int) {
            tileClickListener.onTileClicked(items[position], position)
        }

    }

    interface TileClickListener {
        fun onTileClicked(tile: Tile, position: Int)
    }

    class TileViewHolder(
        private val listener: Listener,
        private val listItemBinding: ListItemBinding
    ) : RecyclerView.ViewHolder(listItemBinding.root), View.OnClickListener {

        private lateinit var tile: Tile

        init {
            listItemBinding.root.setOnClickListener(this)
        }

        fun passTileNo(tile: Tile) {
            this.tile = tile
            if (tile.isSelected) {
                listItemBinding.text.text = tile.tileNumber.toString()
                listItemBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        listItemBinding.root.context,
                        R.color.grey
                    )
                )
            } else {
                listItemBinding.text.text = listItemBinding.root.context.getString(R.string.tap)
                listItemBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        listItemBinding.root.context,
                        R.color.white
                    )
                )
            }
        }

        override fun onClick(v: View?) {
            if (tile.isSelected) return
            listener.onClicked(adapterPosition)
        }

        interface Listener {

            fun onClicked(position: Int)

        }

    }

}
