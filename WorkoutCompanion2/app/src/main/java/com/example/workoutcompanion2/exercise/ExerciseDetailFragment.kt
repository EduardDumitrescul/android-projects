package com.example.workoutcompanion2.exercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R
import com.example.workoutcompanion2.muscle.Muscle
import java.util.*
import android.widget.ArrayAdapter as ArrayAdapter1
import android.widget.SpinnerAdapter as WidgetSpinnerAdapter

private const val TAG = "ExerciseDetailFragment"

class ExerciseDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private var exercise = Exercise.newInstance()
    private lateinit var nameField: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var primarySpinner: Spinner

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
        deleteButton = view.findViewById(R.id.delete_button)
        primarySpinner = view.findViewById(R.id.primary_muscle_spinner)


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
        deleteButton.setOnClickListener {
            exerciseDetailViewModel.deleteExercise(exercise)
            navController.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        exerciseDetailViewModel.exerciseLiveData.observe(
            viewLifecycleOwner
        ) { exercise ->
            exercise.let {
                this.exercise = exercise
                updateUI()
            }
        }
        exerciseDetailViewModel.muscleListLiveData.observe(
            viewLifecycleOwner
        ) {
            val muscleNameList: MutableList<String> = mutableListOf()
            it.forEach { muscle ->
                Log.d(TAG, "sdd string")
                muscleNameList.add(muscle.name)
            }
            val adapter = android.widget.ArrayAdapter<String>(view.context, R.layout.muscle_list_item_spinner, R.id.muscle_list_item_spinner, muscleNameList)
            primarySpinner.adapter = adapter
        }

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