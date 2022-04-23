package com.example.workoutcompanion2.muscle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R
import java.util.*

class MuscleDetailFragment : Fragment() {

    private var muscle: Muscle = Muscle.newInstance()
    private val muscleDetailViewModel: MuscleDetailViewModel by lazy {
        ViewModelProvider(this)[MuscleDetailViewModel::class.java]
    }
    private lateinit var nameField: EditText
    private lateinit var saveButton: Button
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            muscleDetailViewModel.loadMuscle(it.getSerializable(MuscleDetailFragment.ARG_MUSCLE_ID) as UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_detail_fragment, container, false)
        nameField = view.findViewById(R.id.name_field)
        saveButton = view.findViewById(R.id.save_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        saveButton.setOnClickListener {
            muscleDetailViewModel.saveMuscle(muscle)
            view.findNavController().navigateUp()
        }
        nameField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                muscle.name = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        muscleDetailViewModel.muscleLiveData.observe(viewLifecycleOwner) {
            it.let {
                muscle = it
                updateUI()
            }

        }
    }

    private fun updateUI() {
        nameField.setText(muscle.name)
    }

    companion object {
        const val ARG_MUSCLE_ID = "muscle-id"
        fun newInstance() = MuscleDetailFragment()
    }

}