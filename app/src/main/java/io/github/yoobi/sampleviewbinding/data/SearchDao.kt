package io.github.yoobi.sampleviewbinding.data

import androidx.room.*

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity ORDER BY uid DESC LIMIT 50")
    fun getSearch(): List<SearchEntity>

    @Query("SELECT * FROM SearchEntity WHERE search_text = :searchText")
    fun getSearchText(searchText: String): SearchEntity?

    // Keep the last 50 search, delete the rest
    @Query("DELETE FROM SearchEntity WHERE uid < (SELECT uid FROM SearchEntity ORDER BY uid DESC LIMIT 1 OFFSET 49)")
    fun deleteExtras()

    @Delete
    fun delete(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
    fun deleteAll()

}
