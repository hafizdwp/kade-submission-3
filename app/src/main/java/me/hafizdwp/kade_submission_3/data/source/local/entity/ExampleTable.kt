package me.hafizdwp.kade_submission_3.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author hafizdwp
 * 24/07/2019
 **/

@Entity(tableName = ExampleTable.TABLE_NAME)
data class ExampleTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0,

    @ColumnInfo(name = COLUMN_MOVIE_ID)
    var movie_id: Int? = 0,

    @ColumnInfo(name = COLUMN_TVSHOW_ID)
    var tvshow_id: Int? = 0,

    @ColumnInfo(name = COLUMN_TITLE)
    var title: String?,

    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var poster_path: String?,

    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    var backdrop_path: String?,

    // self-made variable
    @ColumnInfo(name = COLUMN_LIST_GENRE)
    var listGenre: String? //List<String>?
) {
    companion object {
        const val TABLE_NAME = "table_example"
        const val COLUMN_ID = "_id"
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_TVSHOW_ID = "tvshow_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_LIST_GENRE = "list_genre"
//
//        fun from(movieResponse: MovieResponse): ExampleTable {
//            with(movieResponse) {
//                return ExampleTable(
//                        movie_id = id,
//                        title = title,
//                        poster_path = poster_path,
//                        backdrop_path = backdrop_path,
//                        listGenre = listGenre.toJson()
//                )
//            }
//        }
//
//        fun from(tvShowResponse: TvShowResponse): ExampleTable {
//            with(tvShowResponse) {
//                return ExampleTable(
//                        tvshow_id = id,
//                        title = name,
//                        poster_path = poster_path,
//                        backdrop_path = backdrop_path,
//                        listGenre = listGenre.toJson()
//                )
//            }
//        }
    }
}