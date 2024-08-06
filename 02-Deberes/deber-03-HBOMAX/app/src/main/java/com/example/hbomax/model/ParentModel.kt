package com.example.hbomax.model

data class ParentModel (
    val title: String,
    val childModels: List<ChildModel>
)