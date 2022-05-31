package com.example.workoutcompanion2.muscle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutcompanion2.R

private val TAG = "SecondaryMuscleDialog"

class SecondaryMuscleDialog(): DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: Button
    private lateinit var muscleList: List<Muscle>
    private var muscleMap: MutableMap<Muscle, Boolean> = mutableMapOf()

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun listSelected(muscleList: List<Muscle>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = parentFragment as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.muscle_secondary_dialog, container)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter(muscleList)
        saveButton = view.findViewById(R.id.save_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveButton.setOnClickListener {
            getSelectedMuscles()
            dialog?.cancel()
        }
    }

    private fun getSelectedMuscles() {
        val list: MutableList<Muscle> = mutableListOf()
        for(pair in muscleMap) {
            if(pair.value)
                list.add(pair.key)
        }
        callbacks?.listSelected(list)
    }

    fun setItemList(muscleList: List<Muscle>, selectedList: List<Muscle>?) {
        this.muscleList = muscleList
        muscleMap.clear()
        selectedList?.forEach {
            muscleMap[it] = true
        }
    }

    private inner class Adapter(muscleList: List<Muscle>): RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.muscle_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(muscleList[position])
        }

        override fun getItemCount(): Int = muscleList.size

    }

    private inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var muscle: Muscle
        private var isSelected: Boolean = false
        private val textView: TextView = itemView.findViewById(R.id.name_field)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(muscle: Muscle) {
            this.muscle = muscle
            textView.text = muscle.name
        }

        override fun onClick(view: View?) {
            if(muscleMap[muscle] == true) {
                muscleMap[muscle] = false
                view?.setBackgroundColor(Color.WHITE)
            }
            else {
                muscleMap[muscle] = true
                view?.setBackgroundColor(Color.LTGRAY)
            }
            Log.d(TAG, "ViewHolder.onClick()")
        }

    }
}