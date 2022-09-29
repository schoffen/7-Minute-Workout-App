package com.example.a7minuteworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {
    private var binding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbBMIActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.tbBMIActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if(checkBMIInputValues()){
                val height = binding?.etHeight?.text?.toString()!!.toDouble()
                val weight = binding?.etWeight?.text?.toString()!!.toDouble()

                val bmi = calculateBMI(height, weight)
                displayBMI(bmi)
            }
        }
    }

    private fun checkBMIInputValues() : Boolean {
        return binding?.tiHeight?.isNotEmpty() == true &&
            binding?.tiWeight?.isNotEmpty() == true
    }

    private fun calculateBMI(height : Double, weight : Double) : Double {
        return weight / (height / 100).pow(2.0)
    }

    private fun displayBMI(bmi : Double){
        // Round BMI to only 2 decimals
        val bmiRound =
            BigDecimal(bmi).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMINumber?.text = bmiRound
        binding?.tvBMICategory?.visibility = View.VISIBLE
        binding?.tvBMIMessage?.visibility = View.VISIBLE
        when(bmi){
            in 0.0..18.4 -> {
                binding?.tvBMICategory?.text = "Thinness"
                binding?.tvBMIMessage?.text = getString(R.string.bmiOverweightMinor)
            }

            in 18.5..24.9 -> {
                binding?.tvBMICategory?.text = "Normal"
                binding?.tvBMIMessage?.text = getString(R.string.bmiOverweightMinor)
            }

            in 25.0..29.9 -> {
                binding?.tvBMICategory?.text = "Overweight"
                binding?.tvBMIMessage?.text = getString(R.string.bmiOverweightPlus)
            }

            in 30.0..100.0 -> {
                binding?.tvBMICategory?.text = "Obese"
                binding?.tvBMIMessage?.text = getString(R.string.bmiOverweightPlus)
            }
        }
    }
}