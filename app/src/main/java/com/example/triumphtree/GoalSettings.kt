package com.example.triumphtree

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


class GoalSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_settings)

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

        changesButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

    }
}