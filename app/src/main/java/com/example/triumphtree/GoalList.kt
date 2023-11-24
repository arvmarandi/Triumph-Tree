package com.example.triumphtree

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


/*
    GoalList activity is a dynamically growing list that will populate as the user is putting in
    goals. It saves goals and their details into a data class and throws them into a JSON and then
    reads them to populate the list in activity_goal_list.xml. This is done through a factory model
    where a new goal is made from the NewGoal activity, which then makes the NewGoalFragment.kt make
    a data class with the information from the NewGoal activity, saves it in the shared preferences
    for later loading.
 */
class GoalList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_list)

        // Read goals from SharedPreferences
        val goalsList = readGoalsFromSharedPreferences()

        // Display the goals in a ListView
        val listView: ListView = findViewById(R.id.goalListView)

        // Create an array adapter to display the goals in the ListView
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            goalsList.map { it.name }
        )

        listView.adapter = arrayAdapter

        // You can add a click listener to the list items if you want to perform some action when an item is clicked
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedGoal = goalsList[position]
            // Handle click on the selected goal, show details, etc.
        }

    }


    private fun readGoalsFromSharedPreferences(): List<GoalModel> {
        val sharedPreferences =
            getSharedPreferences("Goals", MODE_PRIVATE)

        // Retrieve the JSON string from SharedPreferences
        val jsonGoalsList = sharedPreferences.getString("GoalsList", null)

        // Convert the JSON string back to a list of GoalModel using Gson
        val type = object : TypeToken<List<GoalModel>>() {}.type
        return Gson().fromJson(jsonGoalsList, type) ?: emptyList()
    }
}
