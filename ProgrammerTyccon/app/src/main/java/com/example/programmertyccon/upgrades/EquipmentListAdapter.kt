package com.example.programmertyccon.upgrades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.programmertyccon.R

private const val TAG = "SkillUpgradesList"

class EquipmentListAdapter(private val upgradesList: List<EquipmentUpgrade>): RecyclerView.Adapter<EquipmentListAdapter.EquipmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upgrade_item, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(upgradesList[position])
    }

    override fun getItemCount(): Int = upgradesList.size

    inner class EquipmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var equipmentUpgrade: EquipmentUpgrade = EquipmentUpgrade()

        private val imageView: ImageView = itemView.findViewById(R.id.imageview)
        private val titleField: TextView = itemView.findViewById(R.id.title_textfield)
        private val priceField: TextView = itemView.findViewById(R.id.price_textfield)
        private val effectField: TextView = itemView.findViewById(R.id.effect_textfield)

        fun bind(upgrade: EquipmentUpgrade) {
            equipmentUpgrade = upgrade
            titleField.text = equipmentUpgrade.title
            priceField.text = equipmentUpgrade.price.toString()
            effectField.text = equipmentUpgrade.effect.toString()
        }
    }

}
