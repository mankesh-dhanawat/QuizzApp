package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        var quesModeList:List<QuesModel> = listOf()
        var time:String = ""
    }
    lateinit var binding:ActivityQuizBinding

    var currentIndex = 0
    var selectedAns = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.optionA.setOnClickListener(this)
        binding.optionB.setOnClickListener(this)
        binding.optionC.setOnClickListener(this)
        binding.optionD.setOnClickListener(this)
        binding.next.setOnClickListener(this)

        loadQues()
        startTimer()
    }
    private fun startTimer(){
        val totalTime= time.toInt()*60*1000L

        object : CountDownTimer(totalTime, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished/1000
                val min = seconds/60
                val sec = seconds%60
                binding.timeLeft.text = String.format("%02d:%02d", min, sec)
            }
            override fun onFinish() {
                // time up
            }
        }.start()

    }
    @SuppressLint("SetTextI18n")
    private fun loadQues(){
        if(currentIndex== quesModeList.size){
            finishQuiz()
            return
        }
        binding.quesIndicator.text = "Question ${currentIndex+1}/${quesModeList.size}"
        binding.progressIndicator.progress = currentIndex*100/quesModeList.size

        binding.question.text = quesModeList[currentIndex].ques
        binding.optionA.text = quesModeList[currentIndex].options[0]
        binding.optionB.text = quesModeList[currentIndex].options[1]
        binding.optionC.text = quesModeList[currentIndex].options[2]
        binding.optionD.text = quesModeList[currentIndex].options[3]

    }



    override fun onClick(v: View?) {
        binding.apply {
            optionA.setBackgroundColor(getColor(R.color.GreenLight))
            optionB.setBackgroundColor(getColor(R.color.GreenLight))
            optionC.setBackgroundColor(getColor(R.color.GreenLight))
            optionD.setBackgroundColor(getColor(R.color.GreenLight))

            optionA.setTextColor(getColor(R.color.white))
            optionB.setTextColor(getColor(R.color.white))
            optionC.setTextColor(getColor(R.color.white))
            optionD.setTextColor(getColor(R.color.white))
        }

        val clickedBtn = v as Button

        if(clickedBtn.id==R.id.next){
            if(selectedAns== quesModeList[currentIndex].correct) {
                score++
                Log.i("score is ",score.toString())
            }
            currentIndex++
            loadQues()
        }
        else {
            selectedAns = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.Yellow))
            clickedBtn.setTextColor(getColor(R.color.black))
        }
    }

    private fun finishQuiz() {
        val totalQues = quesModeList.size
        val percentage = score*100/totalQues

        val bindingDialog = ScoreDialogBinding.inflate(layoutInflater)
        bindingDialog.progressBar.progress = percentage
        bindingDialog.percentageTxt.text = "$percentage%"
        bindingDialog.messageTxt.text = "$score out of $totalQues questions are correct"

        if(percentage>=60){
            bindingDialog.title.text ="Woohoo! Congrats! You Passed!!!"
            bindingDialog.title.setTextColor(getColor(R.color.GreenDark))
        }
        else{
            bindingDialog.title.text ="Oops! No Bad, Need an Improvement!"
            bindingDialog.title.setTextColor(getColor(R.color.Red))
        }
        bindingDialog.finishBtn.setOnClickListener {
            finish()
        }

        AlertDialog.Builder(this).setView(bindingDialog.root).setCancelable(false).show()
    }
}