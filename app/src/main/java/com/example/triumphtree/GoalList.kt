package com.example.triumphtree

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


/*
    GoalList activity is a dynamically growing list that will populate as the user is putting in
    goals. It saves goals and their details into a data class and throws them into a JSON and then
    reads them to populate the list in activity_goal_list.xml.
 */
const val GL1 = "GoalList LifeCycle"
const val GL2 = "GoalList SharedPref"
class GoalList : AppCompatActivity(){

    private lateinit var listView: ListView
    private lateinit var arrayAdapter: ArrayAdapter<GoalModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_list)
        Log.d(GL1, "onCreate")

        listView = findViewById(R.id.goalListView)

        // Read goals from SharedPreferences
        val goalsList = readGoalsFromSharedPreferences()


        // Create an array adapter to display the goals in the ListView
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            goalsList
        )

        listView.adapter = arrayAdapter

        // Set the OnItemClickListener for the ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedGoal = goalsList[position]
            // Handle click on the selected goal, show details, etc.

            // Create an Intent to start the GoalDetailsActivity
            val intent = Intent(this@GoalList, GoalDetails::class.java)
            intent.putExtra("selectedGoal", selectedGoal)
            startActivity(intent)
        }
    }


    private fun saveGoalsToSharedPreferences()
    {
        Log.d(GL2, "saveGoalsToSharedPreferences")

        val sharedPreferences = getSharedPreferences("Goals", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the goals list to JSON using Gson
        val goalsList = mutableListOf<GoalModel>()
        for (i in 0 until arrayAdapter.count) {
            goalsList.add(arrayAdapter.getItem(i)!!)
        }

        val jsonGoalsList = Gson().toJson(goalsList)

        // Save the JSON string to SharedPreferences
        editor.putString("GoalsList", jsonGoalsList)
        editor.apply()
    }


    private fun readGoalsFromSharedPreferences(): List<GoalModel>
    {
        Log.d(GL2, "readGoalsFromPreferences")
        val sharedPreferences =
            getSharedPreferences("Goals", MODE_PRIVATE)

        // Retrieve the JSON string from SharedPreferences
        val jsonGoalsList = sharedPreferences.getString("GoalsList", null)

        // Convert the JSON string back to a list of GoalModel using Gson
        val type = object : TypeToken<List<GoalModel>>() {}.type
        return Gson().fromJson(jsonGoalsList, type) ?: emptyList()
    }

    override fun onPause() {
        super.onPause()
        Log.d(GL1, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(GL1, "onDestroy")
        saveGoalsToSharedPreferences()
    }

    override fun onResume() {
        super.onResume()
        Log.d(GL1, "onResume")
    }
}
