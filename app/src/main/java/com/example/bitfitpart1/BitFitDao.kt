package com.example.bitfitpart1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BitFitDao
{
	@Query("SELECT * FROM bitfit_table")
	fun getAll(): Flow<List<BitFitEntity>>

	@Insert
	fun insertAll(bitFitItems: List<BitFitEntity>)

	@Insert
	fun insert(bitFitItem: BitFitEntity)

	@Query("DELETE FROM bitfit_table")
	fun deleteAll()
}