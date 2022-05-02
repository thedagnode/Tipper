package com.example.tipper

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.tipper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // initialize
        startup()

        // every time the amount edit text is changed, run calculateTip function
        // so UI is updated
        binding.amountET.doAfterTextChanged{
            calculateTip()
        }



        binding.tipSB.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (progress >= 0 && progress <= binding.tipSB.max) {

                        //val progressString = (progress).toString()
                        //binding.tipPercentET.setText("$progressString%") // the TextView Reference

                        // change emoticon
                        // "if-statement" for safety so we don't try to pick an image out of bounds
                        // in this case we cant, I have 6 steps and 6 icons
                        if(progress<6) {
                            binding.emoticonIV.setImageResource(DEFAULT_ICONS[progress])
                        }

                        // scale text with increasing percentage
                        binding.tipPercentET.textSize = TEXT_SIZE[progress].toFloat()

                        seekBar.secondaryProgress = progress
                        var sliderValue = ((progress * 5) + 5).toString()

                        binding.tipPercentET.text = "$sliderValue%"
                        calculateTip()
                    }
                }
            }
        })


    }





    fun calculateTip(){
        //val tip = binding.tipRS.valueFrom
        if(binding.amountET.text.isEmpty()){
            binding.amountET.hint = "0"
            binding.totalTV.text = "0"
            return
        }

        //val tip = binding.tipSB.progress.toString().toDouble()
        val sliderValue = ((binding.tipSB.progress * 5) + 5).toString().toDouble()
        val amount = binding.amountET.text.toString().toDouble()
        val total = amount * (1 + (sliderValue/100))

        binding.totalTV.text = String.format("%.2f", total)
        //binding.totalTV.text = "${total.toString()}"
    }


    fun startup(){
        //binding.tipSB.incrementProgressBy(5)
        binding.tipSB.max = 5
        binding.tipSB.progress = 2
        var progressVal = binding.tipSB.progress
        //
        var sliderValue = ((progressVal * 5) + 5).toString()
        binding.tipPercentET.text = "$sliderValue%"
        binding.tipPercentET.textSize = TEXT_SIZE[progressVal].toFloat()
        binding.emoticonIV.setImageResource(DEFAULT_ICONS[progressVal])
    }


}