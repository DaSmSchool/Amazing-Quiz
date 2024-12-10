package com.example.amazingquiz

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.findNavController

class Question : Fragment() {

    var questionStrings: Array<String> = arrayOf(
        "What programming language are we using in this class?",
        "Where is our classroom?",
        "How long do most class periods last in this school?",
        "Who teaches this class?",
        "What assignment number is Project 3?"
    )

    var answerStrings: Array<Array<String>> = arrayOf(
        arrayOf(
            "Java",
            "Kotlin",
            "C++",
            "Lua"
        ),
        arrayOf(
            "2N2",
            "7C1",
            "1N3",
            "1C1"
        ),
        arrayOf(
            "41 minutes",
            "43 minutes",
            "40 minutes",
            "44 minutes"
        ),
        arrayOf(
            "Mr. Das",
            "Ms. Turin",
            "Mr. Miller",
            "Mr. Holmer"
        ),
        arrayOf(
            "056",
            "053",
            "057",
            "055"
        )
    )

    var correctAnswers: Array<Int> = arrayOf(
        1,2,0,2,3
    )

    lateinit var answerGroup: RadioGroup
    lateinit var answerChoice0: RadioButton
    lateinit var answerChoice1: RadioButton
    lateinit var answerChoice2: RadioButton
    lateinit var answerChoice3: RadioButton
    lateinit var submitButton: Button
    lateinit var questionTitle: TextView

    var questionsAmount: Int = 5
    var questionsAnswered: Int = 0
    var questionsCorrect: Int = 0
    var chosenInd: Int = -1
    var quizPassed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionsAnswered = 0
        questionsCorrect = 0
        quizPassed = false

        answerGroup = view.findViewById(R.id.answerGroup)
        answerChoice0 = view.findViewById(R.id.answerButton0)
        answerChoice1 = view.findViewById(R.id.answerButton1)
        answerChoice2 = view.findViewById(R.id.answerButton2)
        answerChoice3 = view.findViewById(R.id.answerButton3)
        questionTitle = view.findViewById(R.id.questionText)
        submitButton = view.findViewById(R.id.quizSubmitButton)

        answerGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) return@setOnCheckedChangeListener
            var answerChosen: RadioButton = view.findViewById(checkedId)

            for (answerChoiceInd in 0..<answerStrings[questionsAnswered].size) {
                if (answerChosen.text.toString().contentEquals(answerStrings[questionsAnswered][answerChoiceInd])) {
                    chosenInd = answerChoiceInd
                }
            }
        }

        submitButton.setOnClickListener {
            if (chosenInd != -1) {

                if (chosenInd == correctAnswers[questionsAnswered]) questionsCorrect += 1
                questionsAnswered += 1
                chosenInd = -1
                updateHistory(false)

                if (questionsCorrect.toDouble()/questionsAmount >= 0.65) {
                    quizPassed = true
                }

                if (questionsAnswered < questionStrings.size) {
                    loadQuestion(questionsAnswered)
                } else {
                    updateTotalHistory()
                    view.findNavController().navigate(R.id.action_question_to_results)
                }
            }
        }

        updateHistory(true)
        loadQuestion(0)

    }

    fun loadQuestion(qNum: Int) {
        answerGroup.clearCheck()
        questionTitle.text = "Q${questionsAnswered+1}: ${questionStrings[qNum]}"
        answerChoice0.text = answerStrings[qNum][0]
        answerChoice1.text = answerStrings[qNum][1]
        answerChoice2.text = answerStrings[qNum][2]
        answerChoice3.text = answerStrings[qNum][3]
    }

    fun updateHistory(fresh: Boolean) {
        val prefs: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val prefEditor: SharedPreferences.Editor = prefs.edit()

        if (fresh) {
            prefEditor.putInt("amountAnsweredThisQuiz", 0)
            prefEditor.putInt("amountCorrectThisQuiz", 0)
            prefEditor.putBoolean("passedQuiz", false)
        } else {
            prefEditor.putInt("amountAnsweredThisQuiz", questionsAnswered)
            prefEditor.putInt("amountCorrectThisQuiz", questionsCorrect)
            prefEditor.putBoolean("passedQuiz", quizPassed)
        }

        prefEditor.apply()

    }

    fun updateTotalHistory() {
        val prefs: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        prefEditor.putInt("totalAmountAnswered", prefs.getInt("totalAmountAnswered", 0)+questionsAnswered)
        prefEditor.putInt("totalAmountCorrect", prefs.getInt("totalAmountCorrect", 0)+questionsCorrect)
        if (quizPassed) {
            prefEditor.putInt("totalPasses", prefs.getInt("totalPasses", 0)+1)
        }
        prefEditor.putInt("totalTakes", prefs.getInt("totalTakes", 0)+1)
        prefEditor.apply()
    }

}