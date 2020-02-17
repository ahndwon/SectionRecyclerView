package com.example.sectionrecyclerview

import android.view.View

interface Section {
    fun bind(itemView: View)
    fun viewType(): Int
}
