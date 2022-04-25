package com.example.workoutcompanion2.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExerciseListFragment : Fragment() {

    private var exerciseAdapter = ExerciseAdapter(emptyList())
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    private val exerciseListViewModel: ExerciseListViewModel by lazy {
        ViewModelProvider(this)[ExerciseListViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_list_fragment, container, false)

        exerciseRecyclerView  = view.findViewById(R.id.exercise_recycle_view) as RecyclerView
        exerciseRecyclerView.adapter = exerciseAdapter
        exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

        addButton = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putSerializable(ExerciseDetailFragment.ARG_EXERCISE_ID, exerciseListViewModel.newExercise())
            view.findNavController().navigate(R.id.action_exercise_list_fragment_to_exerciseDetailFragment, bundle)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        exerciseListViewModel.exerciseListLiveData.value?.let {
           // updateUI(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseListViewModel.exerciseListLiveData.observe(
            viewLifecycleOwner
        ) { exerciseList ->
            exerciseList?.let {
                updateUI(exerciseList)
            }
        }
    }

    private fun updateUI(exerciseList: List<Exercise>) {
        exerciseAdapter = ExerciseAdapter(exerciseList)
        exerciseRecyclerView.adapter = exerciseAdapter
    }



    private inner class ExerciseAdapter(val exerciseList: List<Exercise>): RecyclerView.Adapter<ExerciseHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
            val view = layoutInflater.inflate(R.layout.exercise_list_item, parent, false)
            return ExerciseHolder(view)
        }

        override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
            val exercise = exerciseList[position]
            holder.bind(exercise)
        }

        override fun getItemCount() = exerciseList.size
    }

    private class ExerciseHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var exercise: Exercise
        private val nameField: TextView = itemView.findViewById(R.id.name_field)
        private val infoField: TextView = itemView.findViewById(R.id.info_field)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(exercise: Exercise) {
            this.exercise = exercise
            nameField.text = exercise.name
            infoField.text = exercise.info
        }

        override fun onClick(view: View) {
            val bundle = Bundle()
            bundle.putSerializable(ExerciseDetailFragment.ARG_EXERCISE_ID, exercise.id)
            Navigation.findNavController(view).navigate(R.id.action_exercise_list_fragment_to_exerciseDetailFragment, bundle)
        }
    }

    companion object {
        fun newInstance() = ExerciseListFragment()
    }
}