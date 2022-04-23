package com.example.workoutcompanion2.exercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.workoutcompanion2.R
import java.util.*

private const val TAG = "ExerciseDetailFragment"

class ExerciseDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private var exercise = Exercise.newInstance()
    private lateinit var nameField: EditText
    private lateinit var saveButton: Button

    private val exerciseDetailViewModel: ExerciseDetailViewModel by lazy {
        ViewModelProvider(this)[ExerciseDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            exerciseDetailViewModel.loadExercise(it.getSerializable(ARG_EXERCISE_ID) as UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_detail_fragment, container, false)
        nameField = view.findViewById(R.id.name_field)
        saveButton = view.findViewById(R.id.save_button)

        return view
    }

    override fun onStart() {
        super.onStart()
        nameField.setText(exercise.name)
        nameField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                exercise.name = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        saveButton.setOnClickListener {
            exerciseDetailViewModel.saveExercise(exercise)
            navController.navigateUp()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        exerciseDetailViewModel.exerciseLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { exercise ->
                exercise.let {
                    this.exercise = exercise
                    updateUI()
                }
            }

        )
    }

    private fun updateUI() {
        nameField.setText(exercise.name)
    }

    companion object {
        const val ARG_EXERCISE_ID = "exercise-id"

        @JvmStatic
        fun newInstance(exerciseId: UUID) =
            ExerciseDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_EXERCISE_ID, exerciseId)
                }
            }
    }
}