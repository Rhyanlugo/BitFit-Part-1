package com.example.bitfitpart1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BitFitAdapter(private val foodList: MutableList<BitFitItem>) :
	RecyclerView.Adapter<BitFitAdapter.ViewHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val context = parent.context
		val inflater = LayoutInflater.from(context)
		val contactView = inflater.inflate(R.layout.bitfit_calorie_item, parent, false)
		return ViewHolder(contactView)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		val item = foodList[position]
		holder.bind(item)
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	{
		private val foodNameTV: TextView
		private val calorieAmountTV: TextView

		init
		{
			foodNameTV = itemView.findViewById(R.id.food_name)
			calorieAmountTV = itemView.findViewById(R.id.calorie_amount)
		}

		fun bind(item: BitFitItem)
		{
			foodNameTV.text = item.foodName.toString()
			calorieAmountTV.text = item.calorieAmount.toString()
		}
	}

	override fun getItemCount(): Int
	{
		return foodList.size
	}
}