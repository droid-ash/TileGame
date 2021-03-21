package com.ash.tilegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ash.tilegame.databinding.ActivityTileBinding

class TileActivity : AppCompatActivity() {

    private lateinit var tileViewModel: TileViewModel
    private lateinit var adapter: TileRecyclerAdapter

    private val uiDataObserver : Observer<TileModel> = Observer { model ->
        onUIDataChanged(model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tileViewModel = ViewModelProvider(this).get(TileViewModel::class.java)
        tileViewModel.uiLiveData.observe(this, uiDataObserver)
        val selectedNo = intent.getIntExtra(KEY_SELECTED_NO, -1)
        supportActionBar?.title = resources.getString(R.string.msg_guess_no, selectedNo)
        tileViewModel.setSelectedNumber(selectedNo)
        setUpRecyclerView(binding)
        tileViewModel.onUIStarted()
    }

    private fun setUpRecyclerView(binding: ActivityTileBinding) {
        val recyclerView = binding.recyclerView
        val noOfColumns = calculateNoOfColumns(this, COLUMN_WIDTH)
        val gridLayoutManager = GridLayoutManager(this, noOfColumns)
        recyclerView.layoutManager = gridLayoutManager
        adapter = TileRecyclerAdapter(adapterListener)
        recyclerView.adapter = adapter
    }

    private val adapterListener: TileRecyclerAdapter.TileClickListener = object : TileRecyclerAdapter.TileClickListener {

        override fun onTileClicked(tile: Tile, position: Int) {
            tileViewModel.onTileClicked(tile, position)
        }

    }

    private fun showDialog(title: String, message: String) = AlertDialog.Builder(this).apply {
        setMessage(message)
        setTitle(title)
        setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
    }.create().apply {
        setCancelable(false)
        show()
    }

    private fun onUIDataChanged(model: TileModel) {

        when(model.action) {

            ACTION_NONE -> Unit

            ACTION_UPDATE_UI -> adapter.setItems(model.list)

            ACTION_SHOW_SUCCESS_DIALOG -> showDialog(
                getString(R.string.title_congrats),
                getString(R.string.msg_won, model.selectedNo)
            )

            ACTION_SHOW_FAILED_DIALOG -> showDialog(
                getString(R.string.title_failed),
                getString(R.string.msg_try_limit)
            )

            else -> Unit

        }

    }

}
