package com.example.workoutcompanion2.muscle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R

class MuscleDetailFragment : Fragment() {

    private val muscleDetailViewModel: MuscleDetailViewModel by lazy {
        ViewModelProvider(this)[MuscleDetailViewModel::class.java]
    }
    private lateinit var nameField: EditText
    private lateinit var saveButton: Button

    companion object {
        fun newInstance() = MuscleDetailFragment()
    }

    private lateinit var viewModel: MuscleDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_detail_fragment, container, false)
        nameField = view.findViewById(R.id.name_field)
        saveButton = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            view.findNavController().navigateUp()
        }

        return view
    }

}