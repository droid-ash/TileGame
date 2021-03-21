package com.ash.tilegame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ash.tilegame.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val numberArr = resources.getStringArray(R.array.spinner_tile_numbers)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numberArr)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this
    }

    override fun onRestart() {
        super.onRestart()
        binding.spinner.setSelection(0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(position).toString()
        val selectedNo = selectedItem.stringToInt()
        if (selectedNo == -1) {
            showShortToast(this, getString(R.string.msg_select_no))
            return
        }
        navigateToTileActivity(selectedNo)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showShortToast(this, getString(R.string.msg_select_no))
    }

    private fun navigateToTileActivity(selectedNo: Int) {
        val intent = Intent(this, TileActivity::class.java)
        intent.putExtra(KEY_SELECTED_NO, selectedNo)
        startActivity(intent)
    }
}