package com.example.triumphtree

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

const val GS1 = "GoalSettings Name"
class GoalSettings : AppCompatActivity() {

    private lateinit var goalNameEditText: EditText
    private lateinit var goalDescEditText: EditText
    private lateinit var goalThresholdEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_settings)

        val selectedGoal = intent.getParcelableExtra<GoalModel>("selectedGoal")

        Log.d(GS1, "$selectedGoal")

        goalNameEditText = findViewById(R.id.settings_name_edit_text)
        goalNameEditText.setText(selectedGoal?.name)

        goalDescEditText = findViewById(R.id.settings_description_edit_text)
        goalDescEditText.setText(selectedGoal?.details)

        goalThresholdEditText = findViewById(R.id.settings_threshold_edit_text)
        goalThresholdEditText.setText(selectedGoal?.threshold.toString())

        val changesButton = findViewById<Button>(R.id.apply_changes_button)
        val reminderSwitch = findViewById<SwitchCompat>(R.id.on_off_switch)
        val frequencySpinner = findViewById<Spinner>(R.id.notification_frequency)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.frequency_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        frequencySpinner.adapter = adapter

        frequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                Toast.makeText(this@GoalSettings, item, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        reminderSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked -> // Show/hide the spinner based on the switch state
            if (isChecked) {
                reminderSwitch.text = "On"
                frequencySpinner.visibility = View.VISIBLE
            } else {
                reminderSwitch.text = "Off"
                frequencySpinner.visibility = View.GONE
            }
        })

        changesButton.setOnClickListener {
            selectedGoal?.let {
                saveUpdatedGoal(it)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun saveUpdatedGoal(updatedGoal: GoalModel) {
        // Read the current list of goals from SharedPreferences
        val goalsList = readGoalsFromSharedPreferences().toMutableList()

        // Find the index of the goal to be updated based on the name
        val index = goalsList.indexOfFirst { it.name == updatedGoal.name }

        var testGoal = goalNameEditText.text?.toString()?.let {
            goalDescEditText.text?.toString()?.let {
                GoalModel(goalNameEditText.text?.toString()!!,
                    goalDescEditText.text?.toString()!!, "null", goalThresholdEditText.text.toString().toInt(), updatedGoal.days)
            }
        }

        Log.d(GS1, "updated (test) goal, $testGoal")

        if (index != -1) {

            Log.d(GS1, "previous goal, ${goalsList[index]}")
            Log.d(GS1, "updated goal, $updatedGoal")
            // Replace the old goal with the updated goal
            if (testGoal != null) {
                goalsList[index] = testGoal
            }

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