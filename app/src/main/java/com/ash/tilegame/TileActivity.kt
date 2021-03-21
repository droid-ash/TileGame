package com.ash.tilegame

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ash.tilegame.databinding.ActivityTileBinding

class TileActivity : AppCompatActivity(), TileRecyclerAdapter.TileClickListener {

    private var tapCount: Int = 0
    private var selectedNo: Int = -1
    private lateinit var dialog: Dialog
    private lateinit var tileViewModel: TileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tileViewModel = ViewModelProvider(this).get(TileViewModel::class.java)
        selectedNo = intent.getIntExtra(KEY_SELECTED_NO, -1)
        supportActionBar?.title = resources.getString(R.string.msg_guess_no, selectedNo)
        setUpRecyclerView(binding)
    }

    private fun setUpRecyclerView(binding: ActivityTileBinding) {
        val recyclerView = binding.recyclerView
        val noOfColumns = calculateNoOfColumns(this, COLUMN_WIDTH)
        val gridLayoutManager = GridLayoutManager(this, noOfColumns)
        recyclerView.layoutManager = gridLayoutManager
        val tileRecyclerAdapter = TileRecyclerAdapter(this)
        recyclerView.adapter = tileRecyclerAdapter
        tileViewModel.tileListLiveData.observe(this, {
            if (it.isNullOrEmpty()) return@observe
            tileRecyclerAdapter.setItems(it)
        })
    }

    override fun onTileClicked(tileNo: Int) {
        tapCount++
        if (tapCount < MAX_TRY && tileNo != selectedNo) return
        if (tileNo == selectedNo) {
            showDialog(
                getString(R.string.title_congrats),
                resources.getString(R.string.msg_won, selectedNo)
            )
        } else {
            showDialog(
                resources.getString(R.string.title_failed),
                resources.getString(R.string.msg_try_limit)
            )
        }
    }

    private fun showDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setTitle(title)
        builder.apply {
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
        }
        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }
}