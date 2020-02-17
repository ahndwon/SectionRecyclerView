package com.example.sectionrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            Header(),
            Child(),
            Child(),
            Child(),
            Child(),
            Header(),
            Child(),
            Header(),
            Child(),
            Child(),
            Child(),
            Header(),
            Child(),
            Child(),
            Child(),
            Child(),
            Header(),
            Child()
        )

        sectionRecyclerView.adapter = SectionRecyclerViewAdapter().apply {
            this.items = items
        }

        sectionRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }
}
