package com.example.triumphtree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.iterator
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


const val MA1 = "MainActivity Name"
class MainActivity : AppCompatActivity() {
    lateinit var drawerID: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerID = findViewById(R.id.my_drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawerID, R.string.open, R.string.close)

        drawerID.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView = findViewById(R.id.nav_view)

        val menu : Menu = navView.menu

        val temp = readGoalsFromSharedPreferences()

        for (i in temp) {
            menu.add(i.name)
        }

        Log.d(MA1, "test")

        val items: MutableList<MenuItem> = mutableListOf()

        for (i in menu.iterator()) {
            items.add(i)
            Log.d(MA1, "$i")
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            val position = items.indexOf(menuItem)
            val selectedGoal = temp[position]

            val intent = Intent(this@MainActivity, GoalDetails::class.java)
            intent.putExtra("selectedGoal", selectedGoal)
            startActivity(intent)

            true
        }

        val newGoal = findViewById<Button>(R.id.new_goal)
        newGoal.setOnClickListener {
            val intent = Intent(this, NewGoal::class.java)
            startActivity(intent)
        }

        val goalSettings = findViewById<Button>(R.id.goal_settings)
        goalSettings.setOnClickListener {
            val intent = Intent(this, GoalSettings::class.java)
            startActivity(intent)
        }

        val goalList = findViewById<Button>(R.id.goal_list)
        goalList.setOnClickListener{
            val intent = Intent(this, GoalList::class.java)
            startActivity(intent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app_bar, menu)
        return true

//        val settingsButton = findViewById<Button>(R.id.settings_button)
//        settingsButton.setOnClickListener {
//            val intent = Intent(this, SettingsMenu::class.java)
//            startActivity(intent)
//        }
    }
}