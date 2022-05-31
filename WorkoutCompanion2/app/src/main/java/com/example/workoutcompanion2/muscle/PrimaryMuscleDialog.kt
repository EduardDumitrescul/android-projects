package com.example.workoutcompanion2.muscle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R

private val TAG = "PrimaryMuscleDialog"

class PrimaryMuscleDialog(): DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var muscleList: List<Muscle>
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = parentFragment as Callbacks
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_primary_dialog, container)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter(muscleList)

        return view
    }

    fun setItemList(muscleList: List<Muscle>) {
        this.muscleList = muscleList
    }

    interface Callbacks {
        fun itemClicked(muscle: Muscle)
    }

    private inner class Adapter(muscleList: List<Muscle>): RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.muscle_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(muscleList[position])
        }

        override fun getItemCount() = muscleList.size

    }

    private inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var muscle: Muscle
        private val textView: TextView = itemView.findViewById(R.id.name_field)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(muscle: Muscle) {
            this.muscle = muscle
            textView.text = muscle.name
        }

        override fun onClick(p0: View?) {
            callbacks?.itemClicked(muscle)
            this@PrimaryMuscleDialog.dismiss()
        }
    }


}