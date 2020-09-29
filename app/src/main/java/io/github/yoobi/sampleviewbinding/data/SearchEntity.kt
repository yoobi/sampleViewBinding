package io.github.yoobi.sampleviewbinding.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(@PrimaryKey(autoGenerate = true) val uid: Int = 0,
                        @ColumnInfo(name = "search_text") val searchText: String)
