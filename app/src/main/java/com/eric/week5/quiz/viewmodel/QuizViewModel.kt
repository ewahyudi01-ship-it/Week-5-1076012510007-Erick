package com.eric.week5.quiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.eric.week5.quiz.model.QuizAnswerResult
import com.eric.week5.quiz.model.QuizQuestion
import com.eric.week5.quiz.model.listSemuaPertanyaan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

enum class quizStates {
    MAINMENU,
    RUNNING,
    GAMEOVER
}
class QuizViewModel : ViewModel() {

    private val _quizState = MutableStateFlow<quizStates>(quizStates.MAINMENU)
    val quizState: StateFlow<quizStates> = _quizState.asStateFlow()

    private val _teksUtama = MutableStateFlow<String?>("Mini Quiz")
    val teksUtama: StateFlow<String?> = _teksUtama.asStateFlow()


    private val _teksSecondary = MutableStateFlow<String?>("How many question do you want? (1 - 5)")
    val teksSecondary: StateFlow<String?> = _teksSecondary.asStateFlow()


    private val _teksButton = MutableStateFlow<String?>("Start Quiz")
    val teksButton : StateFlow<String?> = _teksButton.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    private val _totalIndexSoalSalahJawab = MutableStateFlow<Int?>(0)
    val totalIndexSoalSalahJawab: StateFlow<Int?> = _totalIndexSoalSalahJawab.asStateFlow()


    private val _indexSoalSaatIni = MutableStateFlow<Int?>(0)
    val indexSoalSaatIni: StateFlow<Int?> = _indexSoalSaatIni.asStateFlow()


    private val _listSelectedQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val listSelectedQuestions: StateFlow<List<QuizQuestion>> = _listSelectedQuestions.asStateFlow()


    private val _listIndexAnsweredWrong = MutableStateFlow<List<Int>>(emptyList())
    val listIndexAnsweredWrong: StateFlow<List<Int>> = _listIndexAnsweredWrong.asStateFlow()


    private val _listAnsweredQuestion = MutableStateFlow<List<QuizAnswerResult>>(emptyList())
    val listAnsweredQuestion: StateFlow<List<QuizAnswerResult>> = _listAnsweredQuestion.asStateFlow()


    private val _opsiJawabanUser = MutableStateFlow<List<String?>>(emptyList())
    val opsiJawabanUser: StateFlow<List<String?>> = _opsiJawabanUser.asStateFlow()



    fun quizMainMenu() {
        _quizState.value = quizStates.MAINMENU
        _teksUtama.value = "Mini Quiz"
        _teksButton.value = "Start Quiz"
    }

    fun quizStarted(jumlahSoal: Int) {

        if (jumlahSoal > 5) {
            _errorMessage.value ="Maximum is 5 questions"
            return
        } else if (jumlahSoal < 1) {
            _errorMessage.value ="You must pick atleast 1 questions"
            return
        } else {
            _errorMessage.value =""        }

        _quizState.value = quizStates.RUNNING
        _listSelectedQuestions.value = listSemuaPertanyaan.shuffled().take(jumlahSoal)

        nextQuestion()
    }
    fun nextQuestion(){
        val indexBaru = (_indexSoalSaatIni.value ?: 0) + 1

        if (indexBaru <= _listSelectedQuestions.value.size){
            _indexSoalSaatIni.value = indexBaru
            _teksButton.value = "Next"
            _teksUtama.value = "Question $indexBaru"
            _teksSecondary.value = "${listSelectedQuestions.value[indexBaru-1].pertanyaan}"

            _opsiJawabanUser.value = listSelectedQuestions.value[indexBaru-1].opsi

        } else {
            quizGameOver()
        }


    }

    fun quizGameOver() {
        _quizState.value = quizStates.GAMEOVER
        _teksUtama.value = "Quiz Finished!"

        val indexJumlahSoal = _listSelectedQuestions.value.size
        val indexSoalSalah = listIndexAnsweredWrong.value.size ?: return
        val jumlahSoalYangBenar = indexJumlahSoal - indexSoalSalah
        _totalIndexSoalSalahJawab.value = indexSoalSalah

            _teksSecondary.value = "Your Score: ${jumlahSoalYangBenar}/${indexJumlahSoal}"

        _teksButton.value = "Restart Quiz"
        _indexSoalSaatIni.value = 0
    }

    fun checkAnswer(opsiSoalSekarangScreen: List<String?>, index: Int) {
        val inputUser = opsiSoalSekarangScreen.get(index)
        var indexSoal = indexSoalSaatIni.value ?: return
        val indexList = indexSoal - 1


        if (listSelectedQuestions.value[indexList].jawabanBenar != inputUser){
            _listIndexAnsweredWrong.update { currentList ->
                currentList + indexList }

            val result = QuizAnswerResult(listSelectedQuestions.value[indexList], inputUser)

            _listAnsweredQuestion.update { currentList ->
                currentList + result
            }
        }
    }

    fun resetData() {
        _listIndexAnsweredWrong.value = emptyList()
        _listAnsweredQuestion.value = emptyList()
        _opsiJawabanUser.value = emptyList()
        _listSelectedQuestions.value = emptyList()
        _totalIndexSoalSalahJawab.value = 0
    }


}
