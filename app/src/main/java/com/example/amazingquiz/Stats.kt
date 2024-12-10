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

class Stats : Fragment() {

    lateinit var statsToHomeButton: Button
    lateinit var passRateText: TextView
    lateinit var totalCorrectText: TextView
    lateinit var percentTotalScoreText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        statsToHomeButton = view.findViewById(R.id.homeFromStatsButton)
        passRateText = view.findViewById(R.id.quizPassRateValue)
        totalCorrectText = view.findViewById(R.id.totalCorrectValue)
        percentTotalScoreText = view.findViewById(R.id.totalPercentValue)

        statsToHomeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_stats_to_homePage)
        }

        var totalAnswered: Int = prefs.getInt("totalAmountAnswered", 0)
        var totalCorrect: Int = prefs.getInt("totalAmountCorrect", 0)
        var totalPassed: Int = prefs.getInt("totalPasses", 0)
        var totalTakes: Int = prefs.getInt("totalTakes", 0)

        var passRate: Double
        var percentScore: Double

        if (totalTakes == 0) {
            passRate = 0.0
        } else {
            passRate = 100* totalPassed.toDouble() / totalTakes
        }

        if (totalCorrect == 0) {
            percentScore = 0.0
        } else {
            percentScore = 100 * totalCorrect.toDouble() / totalAnswered
        }

        passRateText.text = "${String.format("%.2f", passRate)}%"
        totalCorrectText.text = totalCorrect.toString()
        percentTotalScoreText.text = "${String.format("%.2f", percentScore)}%"

    }

}