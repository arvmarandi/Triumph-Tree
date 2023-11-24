package com.example.triumphtree

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

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
