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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.workoutcompanion2.R
import com.example.workoutcompanion2.muscle.Muscle
import java.util.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.workoutcompanion2.muscle.PrimaryMuscleDialog
import com.example.workoutcompanion2.muscle.SecondaryMuscleDialog

private const val TAG = "ExerciseDetailFragment"

class ExerciseDetailFragment : Fragment(), PrimaryMuscleDialog.Callbacks, SecondaryMuscleDialog.Callbacks {

    private lateinit var navController: NavController

    private var exercise = Exercise.newInstance()
    private lateinit var exerciseNameField: EditText

    private var muscleList: MutableList<Muscle> = mutableListOf()
    private lateinit var primaryMuscleArea: ConstraintLayout
    private lateinit var primaryMuscleField: TextView
    private lateinit var primaryMuscleDialog: PrimaryMuscleDialog

    private lateinit var secondaryMuscleArea: ConstraintLayout
    private lateinit var secondaryMuscleField: TextView
    private lateinit var secondaryMuscleDialog: SecondaryMuscleDialog


    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

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
        exerciseNameField = view.findViewById(R.id.name_field)
        saveButton = view.findViewById(R.id.save_button)
        deleteButton = view.findViewById(R.id.delete_button)
        primaryMuscleArea = view.findViewById(R.id.primary_muscle_area)
        primaryMuscleField = view.findViewById(R.id.primary_muscle_field)
        primaryMuscleDialog = PrimaryMuscleDialog()
        secondaryMuscleArea = view.findViewById(R.id.secondary_muscle_area)
        secondaryMuscleField = view.findViewById(R.id.secondary_muscle_field)
        secondaryMuscleDialog = SecondaryMuscleDialog()
        return view
    }

    override fun onStart() {
        super.onStart()
        exerciseNameField.setText(exercise.name)
        exerciseNameField.addTextChangedListener(object: TextWatcher {
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
        primaryMuscleArea.setOnClickListener {
            primaryMuscleDialog.setItemList(muscleList)
            primaryMuscleDialog.show(childFragmentManager, TAG)
        }
        secondaryMuscleArea.setOnClickListener {
            secondaryMuscleDialog.setItemList(muscleList, null)
            secondaryMuscleDialog.show(childFragmentManager, TAG)
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
        ) { muscleList ->
            this.muscleList = muscleList as MutableList<Muscle>
        }

    }

    private fun updateUI() {
        exerciseNameField.setText(exercise.name)
        primaryMuscleField.text = exercise.primaryMuscle?.name ?: "Not Selected"
        secondaryMuscleField.text = exercise.secondaryMuscles.toString()
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

    override fun itemClicked(muscle: Muscle) {
        exercise.primaryMuscle = muscle
        updateUI()
    }

    override fun listSelected(muscleList: List<Muscle>) {
        Log.d(TAG, muscleList.toString())
        exercise.secondaryMuscles = muscleList
        updateUI()
    }
}