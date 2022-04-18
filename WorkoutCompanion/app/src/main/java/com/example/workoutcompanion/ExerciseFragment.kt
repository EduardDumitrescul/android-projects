package com.example.workoutcompanion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.*
import java.util.concurrent.ExecutionException

private const val ARG_EXERCISE_ID = "exercise_id"

class ExerciseFragment(): Fragment() {

    private lateinit var exercise: Exercise
    private lateinit var nameField: EditText
    private lateinit var equipmentField: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = Exercise()
        val exerciseId: UUID = arguments?.getSerializable(ARG_EXERCISE_ID) as UUID

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        nameField = view.findViewById(R.id.exercise_name)
        equipmentField = view.findViewById(R.id.exercise_equipment)
        saveButton = view.findViewById(R.id.save_button)

        return view
    }

    companion object {
        fun newInstance(): ExerciseFragment {
            return ExerciseFragment()
        }

        fun newInstance(exerciseId: UUID): ExerciseFragment {
            val args = Bundle().apply {
                putSerializable(ARG_EXERCISE_ID, exerciseId)
            }
            return ExerciseFragment().apply {
                arguments = args
            }
        }
    }
}