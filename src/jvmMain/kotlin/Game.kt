import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Game {
    fun createQuestion(countries : List<CountryData>):Question{
        val sublist = countries.shuffled().take(4)
        return Question(sublist)
    }

    @Composable
    @Preview
    fun QuizGame(countries: List<CountryData>, userName: String, onFailure: ()->Unit) {
        val scoreRepository = ServiceScoreLocator.scoreRepository
        var question by remember { mutableStateOf(createQuestion(countries)) }
        var score by remember { mutableStateOf(Score(userName,0)) }
        var message by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(question.correct.flag, fontSize = 100.sp)
                Text("Aquesta bandera de quin del següents països és?", color = Color.Green)
                Text(score.points.toString(), fontSize = 50.sp, color = Color.Green)

                question.options.forEach { option ->
                    Button(
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        shape = RoundedCornerShape(20.dp),
                        onClick = {
                            if (option == question.correct) {
                                // Handle correct answer
                                message = ("Has acertado")
                                question = createQuestion(countries)
                                score = Score(userName,score.points + 10 )
                            } else {
                                onFailure()
                                // Handle incorrect answer
                                message = ("Incorrecto")
                                scoreRepository.insert(score)
                            }
                            CoroutineScope(Dispatchers.Default).launch {
                                delay(1000)
                                message = ("")
                            }
                        }
                    ) {
                        Text(option.name.common, color = Color.Green)
                    }
                }
                Text(message,  color = Color.Green)
            }
        }
    }

    @Composable
    fun NameScreen(onNameSet : (String) -> Unit) {
        var username by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                Arrangement.spacedBy(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                    value = username,
                    label = { Text("Your Name", color = Color.Green) },
                    onValueChange = { username = it },
                )
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { onNameSet(username) }) {
                    Text("Play!", color = Color.Green)
                }
            }
        }
    }
    @Composable
    fun GameFinished(scores: List<Score>, onRepeat: ()->Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Scores:", color = Color.Green)
                scores.forEach {
                    Text("${it.username} : ${it.points}", color = Color.Green)
                }
                Text("You lose!", color = Color.Green)
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { onRepeat() }) {
                    Text("Try Again", color = Color.Green)
                }
            }
        }
    }

}