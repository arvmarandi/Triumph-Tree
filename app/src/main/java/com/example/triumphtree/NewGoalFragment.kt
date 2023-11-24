package com.example.triumphtree

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.gson.Gson

interface OnGoalAddedListener {
    fun onGoalAdded(goal: GoalModel)
}


class NewGoalFragment : Fragment() {
    private var onGoalAddedListener: OnGoalAddedListener? = null

    // Define your goals list
    private val goalsList = mutableListOf<GoalModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGoalAddedListener) {
            onGoalAddedListener = context
        } else {
            throw ClassCastException("$context must implement OnGoalAddedListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_new_goal, container, false)


        val finishButton = view.findViewById<Button>(R.id.finish_button)
        finishButton.setOnClickListener {

            //TODO: Change the elements in goalName, Details, and Emblem
            //      to correspond with the info you receive from the NewGoal
            //      activity

            // Get the data from UI elements
            val goalName = "GoalName"// Get the goal name from your UI
            val goalDetails = "GoalDetails"// Get the goal details from your UI
            val goalEmblem = "GoalEmblem"// Get the goal emblem from your UI

            // Create a GoalModel object
            val newGoal = GoalModel(goalName, goalDetails, goalEmblem)

            // Add the new goal to the list
            goalsList.add(newGoal)

            // Save the updated list to SharedPreferences
            saveGoalsToSharedPreferences()

            // Notify the listener (in this case, the GoalList activity) that a new goal is added
            onGoalAddedListener?.onGoalAdded(newGoal)

            // Navigate back to the main activity
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    private fun saveGoalsToSharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences("Goals", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the goals list to JSON using Gson
        val jsonGoalsList = Gson().toJson(goalsList)

        // Save the JSON string to SharedPreferences
        editor.putString("GoalsList", jsonGoalsList)
        editor.apply()
    }
}
