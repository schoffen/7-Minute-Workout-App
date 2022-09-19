package com.example.a7minuteworkout

object Constants {
    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val buttKick = ExerciseModel(
            1,
            "Butt Kicking",
            arrayListOf(R.drawable.buttkick_1, R.drawable.buttkick_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(buttKick)

        val jumpJack = ExerciseModel(
            2,
            "Jumping Jacks",
            arrayListOf(R.drawable.jumpjack_1, R.drawable.jumpjack_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(jumpJack)

        val lunge = ExerciseModel(
            3,
            "Lunges",
            arrayListOf(R.drawable.lunge_1, R.drawable.lunge_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(
            4,
            "Plank",
            arrayListOf(R.drawable.plank_1),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(plank)

        val pushUp = ExerciseModel(
            5,
            "Push Ups",
            arrayListOf(R.drawable.pushup_1, R.drawable.pushup_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(pushUp)

        val sitUp = ExerciseModel(
            6,
            "Sit Ups",
            arrayListOf(R.drawable.situp_1, R.drawable.situp_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(sitUp)

        val squat = ExerciseModel(
            7,
            "Squat",
            arrayListOf(R.drawable.squat_1, R.drawable.squat_2),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(squat)

        val wallSit = ExerciseModel(
            8,
            "Wall Sit",
            arrayListOf(R.drawable.wallsit_1),
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(wallSit)

        return exerciseList
    }
}