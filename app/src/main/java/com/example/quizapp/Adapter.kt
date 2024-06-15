package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemViewBinding

class Adapter (val quizList:List<QuizModel>):RecyclerView.Adapter<Adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.quizTitle.text = quizList[position].title
        holder.binding.quizSubtitle.text = quizList[position].subtitle
        holder.binding.quizTime.text= "${quizList[position].time} min"

        holder.binding.root.setOnClickListener{
            QuizActivity.quesModeList = quizList[position].quesList
            QuizActivity.time = quizList[position].time
            val intent = Intent(holder.binding.root.context, QuizActivity::class.java)
            holder.binding.root.context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemViewBinding):RecyclerView.ViewHolder(binding.root)
}