package com.example.recyclerviewdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.opengl.ETC1.getHeight
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.security.auth.callback.Callback

private val TAG = "RecyclerViewDialog"

class RecyclerViewDialog(): DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private val stringList: List<String> = listOf("string one", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five", "string two", "string three", "string four", "string five")
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_recycler_view, container)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter(stringList)

        return view
    }

    interface Callbacks {
        fun itemClicked(string: String)
    }

    private inner class Adapter(stringList: List<String>): RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(stringList[position])
        }

        override fun getItemCount() = stringList.size

    }

    private inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var string: String = ""
        private val textView: TextView = itemView.findViewById(R.id.text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(string: String) {
            this.string = string
            textView.text = string
        }

        override fun onClick(p0: View?) {
            callbacks?.itemClicked(string)
        }
    }


}