package com.example.triumphtree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class GoalDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_details)

        // Retrieve the selected goal from the Intent
        val selectedGoal = intent.getParcelableExtra<GoalModel>("selectedGoal")

        // Display the details of the selected goal
        val goalDetailsTextView: TextView = findViewById(R.id.goalDetailsTextView)
        goalDetailsTextView.text = "Goal Name: ${selectedGoal?.name}\n" +
                "Details: ${selectedGoal?.details}\n" +
                "Progress: ${selectedGoal?.days} days\n"
    }
}