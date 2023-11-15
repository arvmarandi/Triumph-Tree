package com.example.triumphtree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.new_goal)
        button.setOnClickListener {
            val intent = Intent(this, NewGoal::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.goal_settings)
        button.setOnClickListener {
            val intent = Intent(this, GoalSettings::class.java)
            startActivity(intent)
        }

    }
}