package com.ksw.movielist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ksw.movielist.R
import com.ksw.movielist.ui.details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 299534)
            this.startActivity(intent)
        }
    }
}