package dev.akhras.carmedia
import org.junit.Assert.*
import org.junit.Test
class PlayerViewModelTest{
 @Test fun movingBlocksText(){val model=PlayerViewModel();model.setSearch("jazz");model.setMoving(true);model.setSearch("other");assertEquals("jazz",model.ui.value.search);model.setMoving(false);model.setSearch("other");assertEquals("other",model.ui.value.search)}
}
