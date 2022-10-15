package com.example.bitfitpart1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
	private var foodList = mutableListOf<BitFitItem>()
	private var totalCalories = 0.0
	private var numOfFoodItems = 0

//	override fun onCreate(savedInstanceState: Bundle?)
//	{
//		super.onCreate(savedInstanceState)
//		setContentView(R.layout.activity_main)
//
//		val recyclerView = findViewById<RecyclerView>(R.id.bitfit_recycler_view)
//		val addFoodBtn = findViewById<Button>(R.id.add_new_food_button)
//		val averageCalories = findViewById<TextView>(R.id.caloric_average_display)
//
//		val adapter = BitFitAdapter(foodList)
//		recyclerView.adapter = adapter
//
//		recyclerView.layoutManager = LinearLayoutManager(this).also {
//			val divider = DividerItemDecoration(this, it.orientation)
//			recyclerView.addItemDecoration(divider)
//		}
//
//		lifecycleScope.launch {
//			(application as BitFitApplication).db.dao().getAll().collect { databaseList ->
//				databaseList.map { entity ->
//					BitFitItem(
//						entity.foodName,
//						entity.calorieAmount
//					).also {
//						totalCalories += it.calorieAmount?.toInt() ?: 0
//						numOfFoodItems += 1
//					}
//				}.also { mappedList ->
//					foodList.clear()
//					foodList.addAll(mappedList)
//					adapter.notifyDataSetChanged()
//					averageCalories.text = "Average Number of Calories: ${
//						String.format(
//							"%.2f",
//							totalCalories / numOfFoodItems
//						)
//					}"
//				}
//			}
//		}
//
//		var activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
//			ActivityResultContracts.StartActivityForResult()
//		) { result ->
//			// If the user comes back to this activity from EditActivity
//			// with no error or cancellation
//			if (result.resultCode == Activity.RESULT_OK)
//			{
//				val data = result.data
//				// Get the data passed from EditActivity
//				if (data != null)
//				{
//					val newItem = data.extras!!.getSerializable(TAG) as BitFitItem
//					foodList.add(newItem)
//					newItem.let { item ->
//						lifecycleScope.launch(IO) {
//							(application as BitFitApplication).db.dao().insert(
//								BitFitEntity(
//									foodName = item.foodName,
//									calorieAmount = item.calorieAmount.toString()
//								)
//							)
//						}
//					}
//					totalCalories += newItem.calorieAmount?.toInt() ?: 0
//					numOfFoodItems += 1
//					averageCalories.text =
//						"Average number of calories: ${
//							String.format(
//								"%.2f",
//								totalCalories / numOfFoodItems
//							)
//						}"
//				}
//			}
//		}
//
//		addFoodBtn.setOnClickListener {
//			val intent = Intent(this, BitFitActivity::class.java)
//			activityResultLauncher.launch(intent)
//		}
//	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val recyclerView = findViewById<RecyclerView>(R.id.bitfit_recycler_view)
		val addFoodBtn = findViewById<Button>(R.id.add_new_food_button)
		val averageCalories = findViewById<TextView>(R.id.caloric_average_display)

		val adapter = BitFitAdapter(foodList)
		recyclerView.adapter = adapter

		recyclerView.layoutManager = LinearLayoutManager(this).also {
			val divider = DividerItemDecoration(this, it.orientation)
			recyclerView.addItemDecoration(divider)
		}

		lifecycleScope.launch {
			(application as BitFitApplication).db.dao().getAll().collect { databaseList ->
				databaseList.map { entity ->
					BitFitItem(
						entity.foodName,
						entity.calorieAmount
					).also {
						totalCalories += it.calorieAmount?.toInt() ?: 0
						numOfFoodItems += 1
					}
				}.also { mappedList ->
					foodList.clear()
					foodList.addAll(mappedList)
					adapter.notifyDataSetChanged()
					averageCalories.text =
						"Average number of calories: ${
							String.format(
								"%.2f",
								totalCalories / numOfFoodItems
							)
						}"
				}
			}
		}

		var editActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
			ActivityResultContracts.StartActivityForResult()
		) { result ->
			// If the user comes back to this activity from EditActivity
			// with no error or cancellation
			if (result.resultCode == Activity.RESULT_OK)
			{
				val data = result.data
				// Get the data passed from EditActivity
				if (data != null)
				{
					val newItem = data.extras!!.getSerializable(TAG) as BitFitItem
					foodList.add(newItem)
					newItem.let { item ->
						lifecycleScope.launch(IO) {
							(application as BitFitApplication).db.dao().insert(
								BitFitEntity(
									foodName = item.foodName,
									calorieAmount = item.calorieAmount
								)
							)
						}
					}
					totalCalories += newItem.calorieAmount?.toInt() ?: 0
					numOfFoodItems += 1
					averageCalories.text =
						"Average number of calories: ${
							String.format(
								"%.2f",
								totalCalories / numOfFoodItems
							)
						}"
				}
			}
		}

		addFoodBtn.setOnClickListener {
			val intent = Intent(this, BitFitActivity::class.java)
			editActivityResultLauncher.launch(intent)
		}

	}
}