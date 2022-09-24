package com.example.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbFinish)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            startActivity(Intent(this@FinishActivity, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@FinishActivity, MainActivity::class.java))
    }

}