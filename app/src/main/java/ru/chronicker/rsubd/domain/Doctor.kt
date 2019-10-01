package ru.chronicker.rsubd.domain

class Doctor(
    id: Int,
    firstName: String,
    secondName: String,
    lastName: String,
    val diploma: Diploma
): Person(id, firstName, secondName, lastName)