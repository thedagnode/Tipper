package com.example.tipper

import android.animation.ArgbEvaluator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
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

        // add the gif
        Glide.with(this).load(R.drawable.money_04).into(binding.moneyGifIV)



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
                        if(progress < DEFAULT_ICONS.size) {
                            binding.emoticonIV.setImageResource(DEFAULT_ICONS[progress])
                        }

                        // scale text with increasing percentage
                        //binding.tipPercentTV.textSize = TEXT_SIZE[progress].toFloat()
                        /*
                        seekBar.secondaryProgress = progress
                        val sliderValue = ((progress * 5) + 5).toString()
                        binding.tipPercentTV.text = "$sliderValue%"
                        */
                        calculateTip()
                        updateTipPercentTV(progress)
                    }
                }
            }
        })


    }

    private fun updateTipPercentTV(tipPercent: Int) {

        val sliderValue = ((tipPercent * 5) + 5).toString()
        binding.tipPercentTV.text = "$sliderValue%"

        // text size
        when(tipPercent){
            0 -> binding.tipPercentTV.textSize = 20.0F;
            1 -> binding.tipPercentTV.textSize = 23.0F;
            2 -> binding.tipPercentTV.textSize = 27.0F;
            3 -> binding.tipPercentTV.textSize = 32.0F;
            4 -> binding.tipPercentTV.textSize = 36.0F;
            5 -> binding.tipPercentTV.textSize = 40.0F;

        }

        // text color
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat()/binding.tipSB.max,
            ContextCompat.getColor(this, R.color.worst_tip),
                    ContextCompat.getColor(this, R.color.best_tip)
        )
        binding.tipPercentTV.setTextColor(ColorStateList.valueOf(color as Int))

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
        val progressVal = binding.tipSB.progress
        //
        //val sliderValue = ((progressVal * 5) + 5).toString()
        //binding.tipPercentTV.text = "$sliderValue%"
        //binding.tipPercentTV.textSize = TEXT_SIZE[progressVal].toFloat()
        updateTipPercentTV(progressVal)
        binding.emoticonIV.setImageResource(DEFAULT_ICONS[progressVal])
    }



}