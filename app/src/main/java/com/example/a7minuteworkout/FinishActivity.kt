package com.example.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityFinishBinding
import java.util.*

class FinishActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityFinishBinding? = null
    private var tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)
        speakOut("Congratulations!")

        setSupportActionBar(binding?.tbFinish)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.flFinish?.setOnClickListener {
            startActivity(Intent(this@FinishActivity, MainActivity::class.java))
        }
    }

    private fun speakOut(text : String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onBackPressed() {
        startActivity(Intent(this@FinishActivity, MainActivity::class.java))
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