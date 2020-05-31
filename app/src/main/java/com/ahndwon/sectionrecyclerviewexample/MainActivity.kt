package com.ahndwon.sectionrecyclerviewexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ahndwon.sectionrecyclerview.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment())
            .commit()
    }

}
