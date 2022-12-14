package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.databinding.DialogCustomExitBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? = null


    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0
    var restTimerDuration : Long = 10

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress : Int = 0
    var exerciseTimerDuration : Long = 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null

    private var player : MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        setSupportActionBar(binding?.toolbarExercise)

        exerciseList = Constants.defaultExerciseList()

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomExitBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){
        playFinishSound()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvNextExercise?.text = exerciseList!![currentExercisePosition+1].getName()

        binding?.progressBar?.progress = 100
        binding?.progressBar?.max = 10
        binding?.tvTitle?.text = getString(R.string.restText)
        binding?.ivExerciseImage?.visibility = View.INVISIBLE

        setRestProgressBar()

        player?.setOnCompletionListener {
            speakOut(binding?.tvTitle?.text.toString())
        }
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
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

        speakOut(binding?.tvTitle?.text.toString())
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBar?.progress = exerciseProgress
        var currentExerciseImage = 0

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000){
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
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    playFinishSound()
                    val intent = Intent(
                        this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun speakOut(text : String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playFinishSound(){
        try{
            val soundURI = Uri.parse(
                "android.resource://com.example.a7minuteworkout/raw/" + R.raw.start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
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

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Failed Missing Data")
            }else{
                Log.e("TTS", "Failed")
            }
        }
    }
}