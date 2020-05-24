package com.ahndwon.sectionrecyclerview

interface OnItemMoveSectionableComparator {
    fun onItemMove(from : Sectionable, to: Sectionable) : Boolean
}