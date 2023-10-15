package com.example.noteit.utils

import com.example.noteit.R
import kotlin.random.Random

object AppConstants{
    const val DATABASE_NAME = "note_database"
}

fun randomColors(): Int{

    var colorList = ArrayList<Int>()
    colorList.add(R.color.red_light)
    colorList.add(R.color.green_light)
    colorList.add(R.color.pink_light)
    colorList.add(R.color.blue_light)
    colorList.add(R.color.purple_light)
    colorList.add(R.color.orange_light)
    colorList.add(R.color.cyan_light)

    val seed = System.currentTimeMillis().toInt()
    val randromColor = Random(seed).nextInt(colorList.size)
    return colorList[randromColor]

}