package ru.chronicker.rsubd.domain

class Doctor(
    id: Int,
    firstName: String,
    secondName: String,
    lastName: String,
    val diploma: Diploma,
    val qualification: Qualification
): Person(id, firstName, secondName, lastName)