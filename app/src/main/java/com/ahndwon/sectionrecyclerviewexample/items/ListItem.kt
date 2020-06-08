package com.ahndwon.sectionrecyclerviewexample.items

data class ListItem(
    val category: String,
    val text: String,
    val id : Int,
    val test : Test = Test()
)

data class Test(
    val body: String = "body",
    val shit : String = "shit"
)