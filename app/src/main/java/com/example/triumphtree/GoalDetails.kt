package com.example.triumphtree

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class GoalDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_details)

        // Retrieve the selected goal from the Intent
        val selectedGoal = intent.getParcelableExtra<GoalModel>("selectedGoal")

        // Display the details of the selected goal
        val goalNameTextView: TextView = findViewById(R.id.goalName)
        goalNameTextView.text = "Name: ${selectedGoal?.name}"

        val goalDescriptionTextView: TextView = findViewById(R.id.goalDescription)
        goalDescriptionTextView.text = "Description: ${selectedGoal?.details}"

        val goalProgressTextView: TextView = findViewById(R.id.goalProgress)
        goalProgressTextView.text = "Progress: ${selectedGoal?.days}"

        val deleteButton: Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener{


            val intent = Intent(this@GoalDetails, GoalList::class.java)
            startActivity(intent)
        }

        val addProgressButton: Button = findViewById(R.id.addProgressButton)
        addProgressButton.setOnClickListener{


        }
    }
}