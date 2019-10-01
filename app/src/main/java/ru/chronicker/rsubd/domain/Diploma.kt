package ru.chronicker.rsubd.domain

data class Diploma(
    val id: Int,
    val specialization: Specialization
) : Model()