package com.example.workoutcompanion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_EXERCISE_ID = "exercise_id"

class ExerciseDetailFragment(): Fragment() {

    private lateinit var exercise: Exercise
    private lateinit var nameField: EditText
    private lateinit var equipmentField: EditText
    private lateinit var saveButton: Button

    private val exerciseDetailViewModel: ExerciseDetailViewModel by lazy {
        ViewModelProvider(this).get(ExerciseDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = Exercise()
        val exerciseId: UUID = arguments?.getSerializable(ARG_EXERCISE_ID) as UUID
        exerciseDetailViewModel.loadExercise(exerciseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_detail, container, false)

        nameField = view.findViewById(R.id.exercise_detail_name)
        equipmentField = view.findViewById(R.id.exercise_detail_equipment)
        saveButton = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            exerciseDetailViewModel.saveExercise(exercise)
            activity?.onBackPressed()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        val nameWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                exercise.name = p0.toString()

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        nameField.addTextChangedListener(nameWatcher)

        val equipmentWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                exercise.equipment = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        equipmentField.addTextChangedListener(equipmentWatcher)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseDetailViewModel.exerciseLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { exercise ->
                exercise?.let {
                    this.exercise = exercise
                    updateUI()
                }
            }
        )
    }

    private fun updateUI() {
        nameField.setText(exercise.name)
        equipmentField.setText(exercise.equipment)
    }

    companion object {
        fun newInstance(): ExerciseDetailFragment {
            return ExerciseDetailFragment()
        }

        fun newInstance(exerciseId: UUID): ExerciseDetailFragment {
            val args = Bundle().apply {
                putSerializable(ARG_EXERCISE_ID, exerciseId)
            }
            return ExerciseDetailFragment().apply {
                arguments = args
            }
        }
    }
}