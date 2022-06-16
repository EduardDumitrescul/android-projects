package com.example.programmertyccon.upgrades

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.programmertyccon.Player
import com.example.programmertyccon.R
import com.example.programmertyccon.utils.NumberFormatUtil

private const val TAG = "SkillUpgradesList"

class SkillListAdapter(private val upgradesList: List<SkillUpgrade>): RecyclerView.Adapter<SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upgrade_item, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.bind(upgradesList[position])
    }

    override fun getItemCount(): Int = upgradesList.size

}
class SkillViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val player = Player.getInstance()
    private var skillUpgrade: SkillUpgrade = SkillUpgrade()

    private val imageView: ImageView = itemView.findViewById(R.id.imageview)
    private val titleField: TextView = itemView.findViewById(R.id.title_textfield)
    private val priceField: TextView = itemView.findViewById(R.id.price_textfield)
    private val effectField: TextView = itemView.findViewById(R.id.effect_textfield)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(upgrade: SkillUpgrade) {
        skillUpgrade = upgrade
        titleField.text = skillUpgrade.title
        priceField.text = NumberFormatUtil.toString(skillUpgrade.price)
        effectField.text = NumberFormatUtil.toString(skillUpgrade.effect)
    }

    override fun onClick(v: View?) {
        if(player.currentMoney < skillUpgrade.price) {
            Log.d(TAG, "onClick()")
            if (v != null) {
                Toast.makeText(v.context, "not enough money!", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            player.upgradeSkill(skillUpgrade.index)
        }
    }
}