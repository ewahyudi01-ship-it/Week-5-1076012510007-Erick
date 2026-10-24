package com.eric.week5.quiz.model

data class QuizQuestion(
    val pertanyaan: String,
    val opsi: List<String>,
    val jawabanBenar: String
)

val question1 = QuizQuestion(
    pertanyaan = "Berapa jumlah kontinent dunia?",
    opsi = listOf(
        "2",
        "4",
        "6",
        "7",
        "5"
    ),
    jawabanBenar = "7"
)

val question2 = QuizQuestion(
    pertanyaan = "Ibukota Indonesia?",
    opsi = listOf(
        "Jakarta",
        "Nusantara",
        "Surabaya",
        "Malang",
        "Solo"
    ),
    jawabanBenar = "Jakarta"
)

val question3 = QuizQuestion(
    pertanyaan = "Siapakah penemu Google?",
    opsi = listOf(
        "Tim Berners-Lee",
        "Orville & Wilbur Wright",
        "Herodotus",
        "Larry Page & Sergey Brin",
        "Johannes Gutenberg"
    ),
    jawabanBenar = "Larry Page & Sergey Brin"
)

val question4 = QuizQuestion(
    pertanyaan = "Lagu Nasional Indonesia Raya?",
    opsi = listOf(
        "Indonesia Raya",
        "Sajojo",
        "Soleram",
        "Rasa Sayange",
        "Bungong Jeumpa"
    ),
    jawabanBenar = "Indonesia Raya"
)

val question5 = QuizQuestion(
    pertanyaan = "Subnet mask pada ip 191.168.1.0 adalah...",
    opsi = listOf(
        "Tidak ada satupun yang benar",
        "255.255.255.255",
        "255.255.255.0",
        "255.255.0.0",
        "255.0.0.0"
    ),
    jawabanBenar = "255.255.0.0"
)
val listSemuaPertanyaan: List<QuizQuestion> = listOf<QuizQuestion>(
    question1, question2, question3, question4, question5
)