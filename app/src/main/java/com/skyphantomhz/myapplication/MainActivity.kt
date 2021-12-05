package com.skyphantomhz.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()
    }

    private fun setListener(){
        rgc_segmentDate.onCheckedChangeListener = { _, checkedId ->
            val message = when (checkedId) {
                R.id.rbs_daily -> {
                    "Daily selected"
                }
                R.id.rbs_weekly -> {
                    "weekly selected"
                }
                else -> {
                    "Monthly selected"
                }
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        rgc_segmentDate.setCheck(R.id.rbs_daily)
    }
}