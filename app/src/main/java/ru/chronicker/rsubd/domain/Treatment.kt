package ru.chronicker.rsubd.domain

import java.util.*

data class Treatment(
    val id: Int,
    val name: String,
    val treatmentStart: Date,
    val treatmentEnd: Date,
    val ambulanceRequired: Boolean
): Model()