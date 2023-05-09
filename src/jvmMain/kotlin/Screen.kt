sealed interface Screen{
    object StartScreen : Screen
    class GameScreen(val userName: String)  : Screen
    object GameFinished : Screen
}