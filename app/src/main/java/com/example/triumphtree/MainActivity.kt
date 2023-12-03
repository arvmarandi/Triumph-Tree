package com.example.triumphtree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

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

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menuItem1 -> Toast.makeText(applicationContext, "Clicked Item 1", Toast.LENGTH_LONG).show()
                R.id.menuItem2 -> Toast.makeText(applicationContext, "Clicked Item 2", Toast.LENGTH_LONG).show()
                R.id.menuItem3 -> Toast.makeText(applicationContext, "Clicked Item 3", Toast.LENGTH_LONG).show()
            }
            true
        }

//        val toolbar: Toolbar = findViewById(R.id.app_bar)
//        setSupportActionBar(toolbar)

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