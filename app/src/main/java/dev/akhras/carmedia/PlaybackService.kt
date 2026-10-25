package dev.akhras.carmedia
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.*
import androidx.room.Room
import kotlinx.coroutines.*
class PlaybackService:MediaSessionService(){
 private lateinit var player:ExoPlayer
 private lateinit var session:MediaSession
 private lateinit var store:PlaybackStore
 private val scope=CoroutineScope(SupervisorJob()+Dispatchers.Main)
 override fun onCreate(){
  super.onCreate()
  store=Room.databaseBuilder(this,PlaybackStore::class.java,"playback.db").build()
  player=ExoPlayer.Builder(this).build().apply{
   setAudioAttributes(AudioAttributes.Builder().setUsage(C.USAGE_MEDIA).setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(),true)
   setHandleAudioBecomingNoisy(true)
  }
  session=MediaSession.Builder(this,player).build()
  scope.launch{
   val saved=withContext(Dispatchers.IO){store.playback().load()}
   val first=saved?.uri?:"https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
   player.setMediaItems(listOf(MediaItem.fromUri(first),MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")))
   player.prepare();player.seekTo(saved?.position?:0)
   while(isActive){delay(5000);save()}
  }
 }
 private suspend fun save(){
  val state=PlaybackState(uri=player.currentMediaItem?.localConfiguration?.uri.toString(),position=player.currentPosition)
  withContext(Dispatchers.IO){store.playback().save(state)}
 }
 override fun onGetSession(controllerInfo:MediaSession.ControllerInfo):MediaSession=session
 override fun onDestroy(){
  val state=PlaybackState(uri=player.currentMediaItem?.localConfiguration?.uri.toString(),position=player.currentPosition)
  scope.cancel();runBlocking(Dispatchers.IO){store.playback().save(state)};session.release();player.release();store.close();super.onDestroy()
 }
}
