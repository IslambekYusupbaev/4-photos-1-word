package com.example.a4pics1word

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.a4pics1word.databinding.ActivityGameBinding
import kotlin.random.Random

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var questions: List<Question>
    private var currentQuestionId = 0
    private var clickedImageId = -1
    private val optionLetters = mutableListOf<TextView>()
    private val answerLetters = mutableListOf<TextView>()
    private val currentAnswers = mutableListOf<Pair<String, TextView>>()
    private val currentOptions = mutableListOf<Char>()
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*binding.showShine.startAnimation(AnimationUtils.loadAnimation(
            this@GameActivity,R.anim.rotation_animation
        ))*/
        binding.apply {
            optionLetters.addAll(
                listOf(
                    tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour,
                    tvOptionFive, tvOptionSix, tvOptionSeven, tvOptionEight,
                    tvOptionNine, tvOptionTen, tvOptionEleven, tvOptionTwelve
                )
            )
            answerLetters.addAll(
                listOf(
                    tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
                    tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8
                )
            )
        }

        optionLetters.forEach { optionTv ->
            optionTv.addTextChangedListener {
                val letter = it.toString()
                optionTv.isEnabled = letter.isNotEmpty()
            }

        }

        binding.apply {

            optionLetters.forEach { tv ->
                tv.setOnClickListener {
                    optionClick(tv)
                }
            }

            answerLetters.forEach { tv ->
                tv.setOnClickListener {
                    answerClick(tv)
                }
            }
            btnContinue.setOnClickListener {
                currentQuestionId++
                val cIndex = currentQuestionId
                pref.edit().putInt("levIndex",cIndex).apply()
                setQuestion()
            }
            btnBack.setOnClickListener {
                finish()
            }

        }

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val currentQuestion = questions[currentQuestionId % Constants.getQuestion().size]
        binding.apply {
            currentAnswers.clear()
            updateAnswer(currentQuestion)
            showContinue(false)
            tvLevel.text = (currentQuestionId + 1).toString()
            ivPic1.setImageResource(currentQuestion.images[0])
            ivPic2.setImageResource(currentQuestion.images[1])
            ivPic3.setImageResource(currentQuestion.images[2])
            ivPic4.setImageResource(currentQuestion.images[3])

            val options = currentQuestion.answer.toMutableList()

            repeat(12 - options.size) {
                options.add(Random.nextInt(65, 90).toChar())
            }
            options.shuffle()
            currentOptions.clear()
            currentOptions.addAll(options)

            options.forEachIndexed { index, letter ->
                optionLetters[index].text = letter.toString()

            }
            answerLetters.forEach {
                it.isVisible = true
                it.isClickable = true
            }
            for (i in currentQuestion.answer.length until answerLetters.size) {
                answerLetters[i].isVisible = false
            }

        }
    }

    private fun showContinue(show: Boolean) {
        binding.apply {
            showShine.isVisible = show
            searchViewBackground.isVisible = show
            btnContinue.isVisible = show
            answerLetters.forEach {
                it.isClickable = false
            }
        }
    }

    private fun optionClick(optionTV: TextView) {
        val currentQuestion = questions[currentQuestionId % Constants.getQuestion().size]
        val index = optionLetters.indexOf(optionTV)
        val letter = currentOptions[index]
        val pairIndex = currentAnswers.indexOfFirst { it.first == "" }
        if (pairIndex == -1) {
            currentAnswers.add(Pair(letter.toString(), optionTV))
        } else {
            currentAnswers[pairIndex] = Pair(letter.toString(), optionTV)
        }
        updateAnswer(currentQuestion)
        optionTV.text = ""


    }

    private fun updateAnswer(question: Question) {
        if (currentAnswers.isEmpty()) {
            answerLetters.forEach {
                it.text = ""
            }
            optionLetters.forEach { option ->
                option.isClickable = true
                return
            }
        }


        currentAnswers.forEachIndexed { index, letter ->
            answerLetters[index].text = letter.first
        }
        if (question.answer.length == currentAnswers.filter { it.first.isNotEmpty() }.size) {
            if (question.answer == currentAnswers.map { it.first }
                    .joinToString("")) {
                showContinue(true)
            }
            optionLetters.forEach { option ->
                option.isClickable = false
            }
        } else {
            optionLetters.forEach { option ->
                option.isClickable = true
            }
        }
    }

    private fun answerClick(answerTV: TextView) {
        val letter = answerTV.text.toString()
        if (letter.isNotEmpty()) {
            answerTV.text = ""
            val index = answerLetters.indexOf(answerTV)
            val pair = currentAnswers[index]
            pair.second.text = pair.first
            currentAnswers[index] = Pair("", pair.second)
            updateAnswer(questions[currentQuestionId % Constants.getQuestion().size])
        }
    }

    private fun animationRotation() {
    }

    private fun forAds() {
        binding.coinAdd.setOnClickListener {
            binding.menuCoins.visibility = View.VISIBLE
            binding.menuCoins.isClickable = true

            binding.remove.setOnClickListener {
                binding.menuCoins.visibility = View.GONE
            }
        }
    }

    private fun picsUpAnimation(){
        binding.apply {
            ivPic1.setOnClickListener {
                clickedImageId = 0
                bigImage.setImageResource(questions[currentQuestionId % Constants.getQuestion().size].images[0])
                bigImage.visibility = View.VISIBLE
                bigImage.startAnimation(
                    AnimationUtils.loadAnimation(this@GameActivity, R.anim.animation_up_one)
                )
            }

            ivPic2.setOnClickListener {
                clickedImageId = 1
                bigImage.setImageResource(questions[currentQuestionId % Constants.getQuestion().size].images[1])
                bigImage.visibility = View.VISIBLE
                bigImage.startAnimation(AnimationUtils.loadAnimation(this@GameActivity,R.anim.animation_up_two)
                )
            }

            ivPic3.setOnClickListener {
                clickedImageId = 2
                bigImage.setImageResource(questions[currentQuestionId % Constants.getQuestion().size].images[2])
                bigImage.visibility = View.VISIBLE
                bigImage.startAnimation(AnimationUtils.loadAnimation(this@GameActivity,R.anim.animation_up_three))
            }
            ivPic4.setOnClickListener {
                clickedImageId = 3
                bigImage.setImageResource(questions[currentQuestionId % Constants.getQuestion().size].images[3])
                bigImage.visibility = View.VISIBLE
                bigImage.startAnimation(AnimationUtils.loadAnimation(this@GameActivity,R.anim.animation_up_four))
            }

        }
    }

    private fun picsDownAnimation(){
        binding.apply {
            bigImage.setOnClickListener {
                when(clickedImageId){
                    0 ->{
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_one
                        ))
                        Handler().postDelayed({
                            bigImage.visibility = View.GONE
                        },  200L)
                    }
                    1 ->{
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_two))
                        Handler().postDelayed({
                            bigImage.visibility = View.GONE
                        },200L) }
                    2->{
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_three))
                        Handler().postDelayed({
                            bigImage.visibility = View.GONE
                        },200L)
                    }
                    3->{
                        bigImage.startAnimation(AnimationUtils.loadAnimation(
                            this@GameActivity,R.anim.animation_down_four))
                        Handler().postDelayed({
                            bigImage.visibility = View.GONE
                        },200L)
                    }
                    }
                }

            }

        }


    override fun onResume() {
        super.onResume()
        pref = getSharedPreferences("pref",Context.MODE_PRIVATE)
        val levIndex = pref.getInt("levIndex",0)
        currentQuestionId = levIndex
        animationRotation()
        forAds()
        picsUpAnimation()
        picsDownAnimation()
        questions = Constants.getQuestion()
        setQuestion()


    }
    }

