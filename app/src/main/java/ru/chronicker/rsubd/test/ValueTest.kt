package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.Value

fun testValueWrapping() {
    listOf(
        Value(1, FieldType.INTEGER),
        Value(1, FieldType.TEXT),
        Value("1", FieldType.INTEGER),
        Value("1", FieldType.TEXT)
    ).forEach {
        println(it.wrap())
    }
}

fun main() {
    testValueWrapping()
}