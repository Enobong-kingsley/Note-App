package com.example.noteapp

interface OnProductClickedListener {
    fun onUpdate (position : Int, model: Model)

    fun onDelete (model: Model)
}