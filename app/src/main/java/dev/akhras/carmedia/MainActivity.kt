package dev.akhras.carmedia
import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.session.*
class MainActivity:ComponentActivity(){
 private var controller:MediaController?=null
 override fun onCreate(savedInstanceState:Bundle?){
  super.onCreate(savedInstanceState)
  val future=MediaController.Builder(this,SessionToken(this,ComponentName(this,PlaybackService::class.java))).buildAsync()
  setContent{
   val model:PlayerViewModel=viewModel();val state by model.ui.collectAsState()
   var connected by remember{mutableStateOf(false)}
   DisposableEffect(Unit){future.addListener({controller=future.get();connected=true},ContextCompat.getMainExecutor(this@MainActivity));onDispose{MediaController.releaseFuture(future);controller=null}}
   MaterialTheme{Column(Modifier.padding(24.dp),verticalArrangement=Arrangement.spacedBy(18.dp)){
    Text("Car Media",style=MaterialTheme.typography.headlineLarge)
    Text(if(connected)"Ready" else "Connecting")
    TextField(value=state.search,onValueChange=model::setSearch,label={Text("Playlist filter")},enabled=!state.moving)
    Row{Text("Moving mode (demo input)");Switch(checked=state.moving,onCheckedChange=model::setMoving)}
    Row(horizontalArrangement=Arrangement.spacedBy(12.dp)){
     Button(onClick={controller?.seekToPreviousMediaItem()},modifier=Modifier.height(64.dp),enabled=connected){Text("Previous")}
     Button(onClick={controller?.play()},modifier=Modifier.height(64.dp),enabled=connected){Text("Play")}
     Button(onClick={controller?.pause()},modifier=Modifier.height(64.dp),enabled=connected){Text("Pause")}
     Button(onClick={controller?.seekToNextMediaItem()},modifier=Modifier.height(64.dp),enabled=connected){Text("Next")}
    }
    Button(onClick={controller?.let{it.seekTo(it.currentPosition+10000)}},modifier=Modifier.height(64.dp),enabled=connected){Text("Seek +10 seconds")}
   }}
  }
 }
}
