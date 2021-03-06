package com.example.workoutcompanion2.muscle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG = "MuscleListFragment"

class MuscleListFragment : Fragment() {

    private var muscleAdapter: MuscleAdapter = MuscleAdapter(emptyList())
    private lateinit var muscleRecyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    private val muscleListViewModel: MuscleListViewModel by lazy {
        ViewModelProvider(this)[MuscleListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_list_fragment, container, false)

        muscleRecyclerView = view.findViewById(R.id.muscle_recycler_view)
        muscleRecyclerView.adapter = muscleAdapter
        muscleRecyclerView.layoutManager = LinearLayoutManager(context)
        addButton = view.findViewById(R.id.add_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        muscleListViewModel.muscleListLiveData.observe(viewLifecycleOwner) { muscleList ->
            updateUI(muscleList)
        }
        addButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(MuscleDetailFragment.ARG_MUSCLE_ID, muscleListViewModel.newMuscle())
            findNavController().navigate(R.id.action_muscle_list_fragment_to_muscleDetailFragment, bundle)
        }
    }

    private fun updateUI(muscleList: List<Muscle>) {
        muscleAdapter = MuscleAdapter(muscleList)
        muscleRecyclerView.adapter = muscleAdapter
    }

    private inner class MuscleHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var muscle: Muscle
        private val nameField: TextView = itemView.findViewById(R.id.name_field)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(muscle: Muscle) {
            this.muscle = muscle
            nameField.text = muscle.name
        }

        override fun onClick(p0: View?) {
            val bundle = Bundle()
            bundle.putSerializable(MuscleDetailFragment.ARG_MUSCLE_ID, muscle.id)
            findNavController().navigate(R.id.action_muscle_list_fragment_to_muscleDetailFragment, bundle)
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

    companion object {
        fun newInstance() = MuscleListFragment()
    }

}