package com.example.triumphtree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

const val NG1 = "NewGoal"
class NewGoal : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_goal)

        Log.d(NG1, "onCreate")

        val finishButton = findViewById<Button>(R.id.finish_button)
        val frequencySpinner = findViewById<Spinner>(R.id.frequency_button)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.frequency_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        frequencySpinner.adapter = adapter

        // Need these to save goals
        nameEditText = findViewById(R.id.name_edit_text)
        descriptionEditText = findViewById(R.id.description_edit_text)

        finishButton.setOnClickListener {
            saveGoal()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        frequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                Toast.makeText(this@NewGoal, item, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun saveGoal()
    {
        val sharedPreferences = getSharedPreferences("Goals", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name = nameEditText.text?.toString()
        val description = descriptionEditText.text?.toString()

        val goalList = readGoalsFromSharedPreferences().toMutableList()

        var newGoal = name?.let {
            description?.let {
                GoalModel(it, description, "null")
            }
        }

        newGoal?.let {
            goalList.add(it)
        }

        val jsonGoalsList = Gson().toJson(goalList)

        editor.putString("GoalsList", jsonGoalsList)
        editor.apply()

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

}


