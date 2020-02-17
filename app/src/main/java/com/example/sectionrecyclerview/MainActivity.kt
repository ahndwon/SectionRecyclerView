package com.example.sectionrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            Header("HEADER - 1"),
            Child("this is body - 1"),
            Child("this is body - 2"),
            Child("this is body - 3"),
            Child("this is body - 4"),
            Header("HEADER - 2"),
            Child("this is body - 1"),
            Header("HEADER - 3"),
            Child("this is body - 1"),
            Child("this is body - 2"),
            Child("this is body - 3"),
            Header("HEADER - 4"),
            Child("this is body - 1"),
            Child("this is body - 2"),
            Child("this is body - 3"),
            Child("this is body - 4"),
            Header("HEADER - 5"),
            Child("this is body - 1")
        )

        sectionRecyclerView.adapter = SectionRecyclerViewAdapter().apply {
            this.items = items
        }

        sectionRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }
}
