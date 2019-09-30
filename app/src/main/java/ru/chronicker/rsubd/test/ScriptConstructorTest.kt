package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.models.Disease
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.models.Diagnosis
import ru.chronicker.rsubd.models.State

fun createConstructionTest() {
    println(ScriptConstructor.formCreate(Disease()))
    println(ScriptConstructor.formCreate(State()))
    println(ScriptConstructor.formCreate(Diagnosis()))
}