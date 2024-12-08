package com.example.amazingquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController

class HomePage : Fragment() {

    // nav
    lateinit var quizButton: Button
    lateinit var statsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizButton = view.findViewById(R.id.takeQuizButton)
        statsButton = view.findViewById(R.id.homeStatsButton)

        quizButton.setOnClickListener {
            Log.i("QuizTest", "going to quiz")
            view.findNavController().navigate(R.id.action_homePage_to_question)
        }

        statsButton.setOnClickListener {
            Log.i("QuizTest", "going to stats")
            view.findNavController().navigate(R.id.action_homePage_to_stats)
        }


    }

}