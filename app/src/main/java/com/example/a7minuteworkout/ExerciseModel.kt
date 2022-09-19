package com.example.a7minuteworkout

class ExerciseModel (
    private var id : Int,
    private var name : String,
    private var images : ArrayList<Int>,
    private var isCompleted : Boolean,
    private var isSelected : Boolean
){
    fun getId() : Int {
        return id
    }

    fun setId(id : Int) {
        this.id = id
    }

    fun getName() : String {
        return name
    }

    fun setName(name : String) {
        this.name = name
    }

    fun getImages() : ArrayList<Int> {
        return images
    }

    fun setImages(images : ArrayList<Int>){
        this.images = images
    }

    fun getIsCompleted() : Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted : Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected() : Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected : Boolean) {
        this.isSelected = isSelected
    }
}