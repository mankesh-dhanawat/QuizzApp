package com.example.quizapp

data class QuizModel(
    val id:String,
    val title:String,
    val subtitle:String,
    val time:String,
    val quesList:List<QuesModel>
){
    constructor() : this("","", "","", emptyList())
}

data class QuesModel(
    val ques:String,
    val options: List<String>,
    val correct: String
){
    constructor():this("", emptyList(),"")
}