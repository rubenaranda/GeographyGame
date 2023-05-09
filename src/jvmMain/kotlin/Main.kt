import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Preview
fun App () {
    val countryRepository = ServiceLocator.countryRepository
    val scoreRepository = ServiceScoreLocator.scoreRepository
    val game = Game()
    var screen by remember { mutableStateOf<Screen>(Screen.StartScreen) }

    MaterialTheme {
        val currentScreen = screen
        when(currentScreen){
            Screen.StartScreen -> game.NameScreen(onNameSet = { username ->
                screen = Screen.GameScreen(username)
            })
            is Screen.GameScreen -> game.QuizGame(countryRepository.list(), currentScreen.userName, onFailure = {screen = Screen.GameFinished})
            is Screen.GameFinished -> game.GameFinished(scoreRepository.list(), onRepeat = {screen = Screen.StartScreen})
        }
    }

}
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}


