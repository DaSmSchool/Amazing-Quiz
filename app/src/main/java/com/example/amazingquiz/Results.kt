package com.example.amazingquiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

class Results: Fragment() {

    lateinit var quizOutcomeValue: TextView
    lateinit var quizCorrectValue: TextView
    lateinit var quizPercentValue: TextView
    lateinit var quizToHomeButton: Button
    lateinit var quizToStatsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        quizOutcomeValue = view.findViewById(R.id.quizOutcomeValue)
        quizCorrectValue = view.findViewById(R.id.amountCorrectValue)
        quizPercentValue = view.findViewById(R.id.percentScoreValue)
        quizToHomeButton = view.findViewById(R.id.homeFromResultsButton)
        quizToStatsButton = view.findViewById(R.id.statsFromResultsButton)

        quizToHomeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_results_to_homePage)
        }

        quizToStatsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_results_to_stats)
        }

        if (prefs.getBoolean("passedQuiz", false)) {
            quizOutcomeValue.text = "PASS"
        } else {
            quizOutcomeValue.text = "FAIL"
        }

        var answered = prefs.getInt("amountAnsweredThisQuiz", 0)
        var correct = prefs.getInt("amountCorrectThisQuiz", 0)

        quizCorrectValue.text = correct.toString()
        quizPercentValue.text = "${(correct.toDouble()/answered) * 100}%"

    }

}