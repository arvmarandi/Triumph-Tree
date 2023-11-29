package com.example.triumphtree

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

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

            if (selectedGoal != null) {
                addProgress(selectedGoal)
            }

            if (selectedGoal != null) {
                saveUpdatedGoal(selectedGoal)
            }

        }

    }

    private fun addProgress(goalModel: GoalModel)
    {
        goalModel.days = goalModel.days + 1
    }

    private fun saveUpdatedGoal(updatedGoal: GoalModel) {
        // Read the current list of goals from SharedPreferences
        val goalsList = readGoalsFromSharedPreferences().toMutableList()

        // Find the index of the goal to be updated
        val index = goalsList.indexOfFirst { it == updatedGoal }

        if (index != -1) {
            // Replace the old goal with the updated goal
            goalsList[index] = updatedGoal

            // Save the updated list to SharedPreferences
            saveGoalsToSharedPreferences(goalsList)
        }
    }


    private fun readGoalsFromSharedPreferences(): List<GoalModel>
    {

        val sharedPreferences =
            getSharedPreferences("Goals", MODE_PRIVATE)

        // Retrieve the JSON string from SharedPreferences
        val jsonGoalsList = sharedPreferences.getString("GoalsList", null)

        // Convert the JSON string back to a list of GoalModel using Gson
        val type = object : TypeToken<List<GoalModel>>() {}.type
        return Gson().fromJson(jsonGoalsList, type) ?: emptyList()
    }

    private fun saveGoalsToSharedPreferences(goalsList: List<GoalModel>) {
        val sharedPreferences = getSharedPreferences("Goals", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the goals list to JSON using Gson
        val jsonGoalsList = Gson().toJson(goalsList)

        // Save the JSON string to SharedPreferences
        editor.putString("GoalsList", jsonGoalsList)
        editor.apply()
    }

}