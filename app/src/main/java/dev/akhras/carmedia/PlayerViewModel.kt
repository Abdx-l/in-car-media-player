package dev.akhras.carmedia
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
data class UiState(val moving:Boolean=false,val search:String="")
class PlayerViewModel:ViewModel(){
 private val state=MutableStateFlow(UiState())
 val ui=state.asStateFlow()
 fun setMoving(value:Boolean){state.value=state.value.copy(moving=value)}
 fun setSearch(value:String){if(!state.value.moving)state.value=state.value.copy(search=value)}
}
