package com.eric.week5.quiz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eric.week5.quiz.viewmodel.QuizViewModel
import com.eric.week5.quiz.viewmodel.quizStates


@Composable
fun quiz (viewModel: QuizViewModel = viewModel()) {

    val teksUtama by viewModel.teksUtama.collectAsState()
    val teksSecondary by viewModel.teksSecondary.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val stateQuizCurrent by viewModel.quizState.collectAsState()
    val teksButton by viewModel.teksButton.collectAsState()
    val opsiSoalSekarangScreen by viewModel.opsiJawabanUser.collectAsState()
    val listQuestionAnsweredResult by viewModel.listAnsweredQuestion.collectAsState()
    val totalIndexSoalSalahJawab by viewModel.totalIndexSoalSalahJawab.collectAsState()

    var jumlahSoalDiTampilkan by rememberSaveable { mutableStateOf("") }
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }

    Box (
        Modifier.fillMaxSize()
            .background(Color(250,248,254))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 80.dp)
                .padding(horizontal = 17.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("${teksUtama ?: ""}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Thin
               )

            Text("${teksSecondary ?: ""}",
                modifier = Modifier.padding(top = 6.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.Light
            )
        if ( stateQuizCurrent == quizStates.MAINMENU) {
            OutlinedTextField(
                value = jumlahSoalDiTampilkan,
                onValueChange = {
                    jumlahSoalDiTampilkan = it
                },
                placeholder = {
                    Text(
                        "Number of questions",
                        fontSize = 13.sp,
                        color = Color(151, 162, 182),
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(250, 248, 254)),
                shape = RoundedCornerShape(6.dp)
            )
        }
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                Modifier.fillMaxWidth()
            ) {

            }

            if (stateQuizCurrent == quizStates.RUNNING) {
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    itemsIndexed(opsiSoalSekarangScreen) { index,item ->

                        Button(
                            onClick = {
                                selectedIndex = index
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (selectedIndex == index) {
                                            Color(70, 90, 134)
                                        } else {
                                            Color(220, 226, 249)
                                        }
                            )
                        ) {
                            Text(item.toString(),
                                color = if (selectedIndex == index) {
                                    Color.White
                                } else {
                                    Color.Black
                                }

                            )
                        }

                    }
                }
            }
            if (stateQuizCurrent == quizStates.GAMEOVER) {

                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    itemsIndexed(listQuestionAnsweredResult) { index, item ->
                        Text("Q${index+1}: ${item.question.pertanyaan}",
                            modifier = Modifier.padding(top = 10.dp))
                        Text("Your answer: ${item.selectedAnswer}",
                            fontSize = 12.sp,
                            color = Color(194,86,81)
                        )
                        Text("Correct answer: ${item.question.jawabanBenar}",
                            fontSize = 12.sp,
                            color = Color(70, 90, 134)
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color(210, 210, 210)
                        )

                    }
                }
            }
            if (totalIndexSoalSalahJawab == 0 && stateQuizCurrent == quizStates.GAMEOVER) {
                Text("Perfect! No wrong answers.",
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    color = Color(70, 90, 134))
            }

            FloatingActionButton(
                onClick = {

                    when (stateQuizCurrent){
                        quizStates.MAINMENU -> {
                            val jumlahSoalDipilih = jumlahSoalDiTampilkan.toIntOrNull()

                            if (jumlahSoalDipilih != null) {
                                viewModel.quizStarted(jumlahSoalDipilih)
                            }
                        }
                        quizStates.RUNNING -> {
                            viewModel.checkAnswer(opsiSoalSekarangScreen, selectedIndex)
                            selectedIndex = -1
                            viewModel.nextQuestion()

                        }

                        quizStates.GAMEOVER -> {
                            viewModel.resetData()
                            viewModel.quizMainMenu()
                        }
                    }

                },
                containerColor =
                if (jumlahSoalDiTampilkan.isNotEmpty() && stateQuizCurrent == quizStates.MAINMENU){
                    Color(70, 90, 134)
                } else if (selectedIndex != -1) {
                    Color(70, 90, 134)
                } else if (stateQuizCurrent == quizStates.GAMEOVER) {
                    Color(70, 90, 134)
                } else {
                    Color(220, 226, 249)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(    top = 15.dp)
                    .size(45.dp),

                ) {
                Text("${teksButton}",
                    color = Color.White)
            }

        }

    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun viewquiz(){
    quiz()
}