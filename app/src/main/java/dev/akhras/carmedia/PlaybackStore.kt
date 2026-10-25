package dev.akhras.carmedia
import androidx.room.*
@Entity data class PlaybackState(@PrimaryKey val id:Int=1,val uri:String,val position:Long)
@Dao interface PlaybackDao{
 @Query("SELECT * FROM PlaybackState WHERE id=1") suspend fun load():PlaybackState?
 @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun save(state:PlaybackState)
}
@Database(entities=[PlaybackState::class],version=1,exportSchema=false)
abstract class PlaybackStore:RoomDatabase(){abstract fun playback():PlaybackDao}
