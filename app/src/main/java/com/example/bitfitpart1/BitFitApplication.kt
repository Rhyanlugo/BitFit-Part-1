package com.example.bitfitpart1

import android.app.Application

class BitFitApplication : Application()
{
	val db by lazy { BitFitDatabase.getInstance(this) }
}