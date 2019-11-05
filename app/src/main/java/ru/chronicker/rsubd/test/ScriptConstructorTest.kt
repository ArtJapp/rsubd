package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.database.models.*

/**
 * Генерация скрипта для создания всех таблиц базы данных
 * Необходимо соблюдать именно такой порядок
 */
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

fun insertConstructionTest() {
    val params = listOf(
        Value(1, FieldType.INTEGER),
        Value("Вавка", FieldType.TEXT)
    )
    println(ScriptConstructor.formInsert(Disease(), params))
}

fun updateConstructionTest() {
    val params = listOf(
        Value(1, FieldType.INTEGER),
        Value("Вавка", FieldType.TEXT)
    )
    println(ScriptConstructor.formUpdate(Disease(), params))
}