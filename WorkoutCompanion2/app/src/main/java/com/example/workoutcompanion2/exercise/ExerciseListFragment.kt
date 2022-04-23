package com.example.workoutcompanion2.exercise

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R
import kotlinx.coroutines.handleCoroutineException

class ExerciseListFragment : Fragment() {

    private var exerciseAdapter = ExerciseAdapter(emptyList())
    private lateinit var exerciseRecyclerView: RecyclerView
    private val exerciseListViewModel: ExerciseListViewModel by lazy {
        ViewModelProvider(this)[ExerciseListViewModel::class.java]
    }

    private lateinit var viewModel: ExerciseListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_list_fragment, container, false)

        exerciseRecyclerView  = view.findViewById(R.id.exercise_recycle_view) as RecyclerView
        exerciseRecyclerView.adapter = exerciseAdapter
        exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseListViewModel.exerciseListLiveData.observe(
            viewLifecycleOwner,
            Observer { exerciseList ->
                exerciseList?.let {
                    updateUI(exerciseList)
                }
            }
        )
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
            Navigation.findNavController(view).navigate(R.id.action_exercise_list_fragment_to_exerciseDetailFragment)
        }
    }

    companion object {
        fun newInstance() = ExerciseListFragment()
    }
}