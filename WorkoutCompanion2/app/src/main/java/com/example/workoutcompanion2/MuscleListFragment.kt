package com.example.workoutcompanion2

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MuscleListFragment : Fragment() {

    private var muscleAdapter: MuscleAdapter = MuscleAdapter(emptyList())
    private lateinit var muscleRecyclerView: RecyclerView

    private val muscleListViewModel: MuscleListViewModel by lazy {
        ViewModelProvider(this)[MuscleListViewModel::class.java]
    }

    companion object {
        fun newInstance() = MuscleListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_list_fragment, container, false)

        muscleRecyclerView = view.findViewById(R.id.muscle_recycler_view)
        muscleRecyclerView.adapter = muscleAdapter
        muscleRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        muscleAdapter = MuscleAdapter(muscleListViewModel.muscleList)
        muscleRecyclerView.adapter = muscleAdapter
    }

    private inner class MuscleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var muscle: Muscle
        private val nameField: TextView = itemView.findViewById(R.id.name_field)

        fun bind(muscle: Muscle) {
            this.muscle = muscle
            nameField.text = muscle.name
        }
    }

    private inner class MuscleAdapter(val muscleList: List<Muscle>): RecyclerView.Adapter<MuscleHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleHolder {
            val view = layoutInflater.inflate(R.layout.muscle_list_item, parent, false)
            return MuscleHolder(view)
        }

        override fun onBindViewHolder(holder: MuscleHolder, position: Int) {
            val muscle = muscleList[position]
            holder.bind(muscle)
        }

        override fun getItemCount() = muscleList.size
    }

}