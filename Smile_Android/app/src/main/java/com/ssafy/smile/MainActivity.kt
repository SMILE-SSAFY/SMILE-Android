package com.ssafy.smile

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.smile.databinding.ActivityMainBinding


// TODO : presentation 패키지 내부로 옮기기.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setToolBar(isUsed: Boolean, isBackUsed: Boolean, title: String?) {
        binding.apply {
            if(isUsed) {
                setSupportActionBar(toolbar)
                supportActionBar!!.setDisplayShowTitleEnabled(false)
                tvToolbarTitle.text = title
                if (isBackUsed) {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
                }
            } else {
                toolbar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_no_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }
}