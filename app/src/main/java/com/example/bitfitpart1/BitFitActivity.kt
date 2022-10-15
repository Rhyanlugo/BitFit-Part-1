package com.example.bitfitpart1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

const val TAG = "BitFitActivity"

class BitFitActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.bitfit_activity_detail)

		val foodInput = findViewById<EditText>(R.id.food_input)
		val caloriesInput = findViewById<EditText>(R.id.calories_input)
		val saveFoodBtn = findViewById<Button>(R.id.save_food_button)

		saveFoodBtn.setOnClickListener {
			val foodName = foodInput.text.toString()
			val calorieAmount = caloriesInput.text.toString()

			if (foodName.isNotEmpty() && calorieAmount.isNotEmpty() && calorieAmount.isDigitsOnly())
			{
				// Close Keyboard
				this.currentFocus?.let { view ->
					val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
					imm?.hideSoftInputFromWindow(view.windowToken, 0)
				}

				val item = BitFitItem(foodName, calorieAmount)
				val data = Intent()
				data.putExtra(TAG, item)
				setResult(RESULT_OK, data)
				finish()
			}
			else
			{
				Toast.makeText(
					applicationContext,
					"Incorrect input or missing input.",
					Toast.LENGTH_SHORT
				).show()
			}
		}
	}

}