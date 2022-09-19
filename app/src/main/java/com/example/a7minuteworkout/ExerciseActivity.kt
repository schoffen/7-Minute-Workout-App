package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.core.view.marginTop
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding : ActivityExerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress : Int = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        exerciseList = Constants.defaultExerciseList()

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRestView()
    }

    private fun setupRestView(){

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvNextExercise?.text = exerciseList!![currentExercisePosition+1].getName()

        binding?.progressBar?.progress = 100
        binding?.progressBar?.max = 10
        binding?.tvTitle?.text = getString(R.string.restText)
        binding?.ivExerciseImage?.visibility = View.GONE

        setRestProgressBar()
    }

    private fun setupExerciseView(){
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.tvNextExercise?.visibility = View.INVISIBLE

        binding?.progressBar?.progress = 100
        binding?.progressBar?.max = 30
        binding?.tvTitle?.text = exerciseList!![currentExercisePosition].getName()
        binding?.ivExerciseImage?.visibility = View.VISIBLE

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBar?.progress = exerciseProgress
        var currentExerciseImage = 0

        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBar?.progress = 31 - exerciseProgress
                binding?.tvTimer?.text = (31 - exerciseProgress).toString()

                // Exercise image animation
                if(exerciseList!![currentExercisePosition].getImages().size > 1){
                    binding?.ivExerciseImage?.setImageResource(
                        exerciseList!![currentExercisePosition].
                        getImages()[currentExerciseImage])

                    if(currentExerciseImage == 0){
                        currentExerciseImage++
                    }else{
                        currentExerciseImage--
                    }
                }else{
                    binding?.ivExerciseImage?.setImageResource(
                        exerciseList!![currentExercisePosition].
                        getImages()[0])
                }
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    //TODO implement complete screen
                }
            }
        }.start()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding = null
    }
}