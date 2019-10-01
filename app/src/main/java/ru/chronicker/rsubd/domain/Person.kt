package ru.chronicker.rsubd.domain

open class Person(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val lastName: String
) : Model()