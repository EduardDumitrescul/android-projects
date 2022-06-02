package com.example.programmertyccon.Upgrades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.programmertyccon.R

private val TAG = "AssistantUpgradesList"

class AssistantListAdapter(private val upgradesList: List<AssistantUpgrade>): RecyclerView.Adapter<AssistantListAdapter.AssistantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssistantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upgrade_item, parent, false)
        return AssistantViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssistantViewHolder, position: Int) {
        holder.bind(upgradesList[position])
    }

    override fun getItemCount(): Int = upgradesList.size

    inner class AssistantViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var assistantUpgrade: AssistantUpgrade = AssistantUpgrade()

        private val imageView: ImageView = itemView.findViewById(R.id.imageview)
        private val titleField: TextView = itemView.findViewById(R.id.title_textfield)
        private val infoField: TextView = itemView.findViewById(R.id.info_textfield)
        private val priceField: TextView = itemView.findViewById(R.id.price_textfield)
        private val effectField: TextView = itemView.findViewById(R.id.effect_textfield)

        init {

        }

        fun bind(upgrade: AssistantUpgrade) {
            assistantUpgrade = upgrade
            titleField.text = assistantUpgrade.title
            infoField.text = assistantUpgrade.info
            priceField.text = assistantUpgrade.price.toString()
            effectField.text = assistantUpgrade.effect.toString()
        }
    }

}
