package com.example.a4pics1word

object Constants {

    fun getQuestion(): List<Question> {
        return listOf(
            Question(
                id = 0,
                images = listOf(
                    R.drawable.photo1_1,
                    R.drawable.photo1_2,
                    R.drawable.photo1_3,
                    R.drawable.photo1_4
                ),
                answer = "COLD"
            ),
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo2_1,
                    R.drawable.photo2_2,
                    R.drawable.photo2_3,
                    R.drawable.photo2_4
                ),
                answer = "LOUD"
            ),
            Question(
                id = 2,
                images = listOf(
                    R.drawable.photo3_1,
                    R.drawable.photo3_2,
                    R.drawable.photo3_3,
                    R.drawable.photo3_4
                ),
                answer = "HOT"
            ),
            Question(
                id = 3,
                images = listOf(
                    R.drawable.photo4_1,
                    R.drawable.photo4_2,
                    R.drawable.photo4_3,
                    R.drawable.photo4_4
                ),
                answer = "MUSIC"
            ),
            Question(
                id = 4,
                images = listOf(
                    R.drawable.photo5_1,
                    R.drawable.photo5_2,
                    R.drawable.photo5_3,
                    R.drawable.photo5_4
                ),
                answer = "DOT"
            ),

            )
    }
}