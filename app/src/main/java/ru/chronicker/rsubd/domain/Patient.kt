package ru.chronicker.rsubd.domain

import java.util.*

class Patient(
    id: Int,
    firstName: String,
    secondName: String,
    lastName: String,
    val birthDate: Date
): Person(id, firstName, secondName, lastName)