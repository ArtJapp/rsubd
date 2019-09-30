package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

fun testFields() {
    val fields = listOf(
        Field(
            "fieldA",
            type = FieldType.TEXT
        ),
        Field(
            "fieldB",
            type = FieldType.TEXT,
            primaryKey = true
        ),
        Field(
            "fieldC",
            type = FieldType.TEXT,
            primaryKey = false
        ),
        IntField("fieldD"),
        IntField("fieldE", autoIncrement = true),
        IntField("fieldF", autoIncrement = false),
        IntField("fieldG", primaryKey = true),
        IntField("fieldH", primaryKey = false),
        IntField(
            "fieldI",
            primaryKey = false,
            autoIncrement = true
        ),
        IntField(
            "fieldK",
            primaryKey = true,
            autoIncrement = true
        )
    )

    fields.forEach {
        println(it.formForCreate())
    }
}