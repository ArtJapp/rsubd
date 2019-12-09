package ru.chronicker.rsubd.database.base

import android.util.Log
import ru.chronicker.rsubd.EMPTY_BLOB
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_REAL
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.DBHelper
import java.io.Serializable

/**
 * Общая модель сущности в базе данных
 */
abstract class Entity(
    open val name: String = EMPTY_STRING,
    open val fields: MutableList<Field> = mutableListOf()
) : Serializable {
    val values: MutableMap<String, Any> = mutableMapOf()

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

    /**
     * Преобразовывает значения одной сущности в элемент единую строку
     * Используется в дропдауне для значений внешних ключей
     */
    open fun convertToString(values: List<Pair<Field, Any>>): String =
        EMPTY_STRING

    open fun deepConvertToString(values: List<Pair<Field, Any>>, dbHelper: DBHelper): String =
        convertToString(values)

    open fun convertMappedToString(values: List<Pair<String, String>>): String =
        EMPTY_STRING

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