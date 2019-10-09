package ru.chronicker.rsubd.database.base

import android.util.Log
import ru.chronicker.rsubd.EMPTY_BLOB
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_REAL
import ru.chronicker.rsubd.EMPTY_STRING

/**
 * Общая модель сущности в базе данных
 */
abstract class Entity(
    val name: String,
    val fields: MutableList<Field>
) {
    val values: MutableMap<String, Value> = mutableMapOf()

    init {
        fields.forEach { field ->
            when (field.type) {
                FieldType.INTEGER -> putEmptyInt(field.name)
                FieldType.TEXT -> putEmptyString(field.name)
                FieldType.REAL -> putEmptyReal(field.name)
                FieldType.BLOB -> putEmptyBlob(field.name)
                else -> Log.d(name, "null field?")
            }
        }
    }

    private fun putEmptyInt(fieldName: String) {
        values[fieldName] = Value(EMPTY_INT, FieldType.INTEGER)
    }

    private fun putEmptyString(fieldName: String) {
        values[fieldName] = Value(EMPTY_STRING, FieldType.TEXT)
    }

    private fun putEmptyReal(fieldName: String) {
        values[fieldName] = Value(EMPTY_REAL, FieldType.REAL)
    }

    private fun putEmptyBlob(fieldName: String) {
        values[fieldName] = Value(EMPTY_BLOB, FieldType.BLOB)
    }
}