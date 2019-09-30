package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.models.*

fun createConstructionTest() {
    println(ScriptConstructor.formCreate(Disease()))
    println(ScriptConstructor.formCreate(State()))
    println(ScriptConstructor.formCreate(Treatment()))
    println(ScriptConstructor.formCreate(Diagnosis()))

    println(ScriptConstructor.formCreate(Person()))
    println(ScriptConstructor.formCreate(SocialStatus()))
    println(ScriptConstructor.formCreate(Patient()))

    println(ScriptConstructor.formCreate(Specialization()))
    println(ScriptConstructor.formCreate(Qualification()))
    println(ScriptConstructor.formCreate(Doctor()))
    println(ScriptConstructor.formCreate(Diploma()))

    println(ScriptConstructor.formCreate(History()))
}