package ru.chronicker.rsubd.domain

class Diagnosis (
    val id: Int,
    val treatment: Treatment,
    val disease: Disease,
    val state: State
): Model()