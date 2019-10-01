package ru.chronicker.rsubd.database.base

import ru.chronicker.rsubd.EMPTY_BLOB
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_REAL
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.domain.Model

/**
 * Общая модель сущности в базе данных
 */
abstract class Entity(
    val name: String,
    val fields: MutableList<Field>
) {
    val values: MutableMap<String, Any> = mutableMapOf()

    init {
        fields.forEach { field ->
            when (field.type) {
                FieldType.INTEGER -> putEmptyInt(field.name)
                FieldType.TEXT -> putEmptyString(field.name)
                FieldType.REAL -> putEmptyReal(field.name)
                FieldType.BLOB -> putEmptyBlob(field.name)
                else -> print("$name contains null field?")
            }
        }
    }

    private fun putEmptyInt(fieldName: String) {
        values[fieldName] = EMPTY_INT
    }

    private fun putEmptyString(fieldName: String) {
        values[fieldName] = EMPTY_STRING
    }

    private fun putEmptyReal(fieldName: String) {
        values[fieldName] = EMPTY_REAL
    }

    private fun putEmptyBlob(fieldName: String) {
        values[fieldName] = EMPTY_BLOB
    }
}