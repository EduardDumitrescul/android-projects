package com.example.workoutcompanion

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ExerciseListFragment"

class ExerciseListFragment: Fragment() {

    interface Callbacks {
        fun onNewExercise()
        fun onExerciseClicked(exercise_id: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var newExerciseButton: Button
    private var exerciseAdapter: ExerciseAdapter? = ExerciseAdapter(emptyList())

    private val exerciseListViewModel: ExerciseListViewModel by lazy {
        ViewModelProvider(this).get(ExerciseListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_list, container, false)

        exerciseRecyclerView = view.findViewById(R.id.exercise_recycler_view) as RecyclerView
        exerciseRecyclerView.layoutManager = LinearLayoutManager(context)
        exerciseRecyclerView.adapter = exerciseAdapter

        newExerciseButton = view.findViewById(R.id.exercise_new_button)
        newExerciseButton.setOnClickListener {
            callbacks?.onNewExercise()

        }

        return view
    }

    private fun updateUI(exercises: List<Exercise>) {
        exerciseAdapter = ExerciseAdapter(exercises)
        exerciseRecyclerView.adapter = exerciseAdapter
    }

    private inner class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var exercise: Exercise

        val nameTextView: TextView = itemView.findViewById(R.id.exercise_name)
        val equipmentTextView: TextView = itemView.findViewById(R.id.exercise_equipment)

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(exercise: Exercise) {
            this.exercise = exercise
            nameTextView.text = this.exercise.name
            equipmentTextView.text = this.exercise.equipment
        }

        override fun onClick(p0: View?) {
            callbacks?.onExerciseClicked(exercise.id_exercise)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseListViewModel.exerciseListLiveData.observe(
            viewLifecycleOwner,
            Observer { exercises->
                exercises?.let {
                    Log.d(TAG, "onViewCreated() ${exercises.size}")
                    updateUI(exercises)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class ExerciseAdapter(var exercises: List<Exercise>): RecyclerView.Adapter<ExerciseHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
            Log.d(TAG, "ExerciseAdapter ${exercises.size}")
            val view = layoutInflater.inflate(R.layout.fragment_exercise, parent, false)
            return ExerciseHolder(view)
        }

        override fun getItemCount() = exercises.size

        override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
            val exercise = exercises[position]
            holder.bind(exercise)
        }
    }

    companion object {
        fun newInstance(): ExerciseListFragment {
            return ExerciseListFragment()
        }
    }
}