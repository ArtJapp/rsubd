package ru.chronicker.rsubd.database.base

import ru.chronicker.rsubd.EMPTY_STRING
import java.io.Serializable

open class Value(
    val value: Any,
    val type: FieldType
): Serializable {

    val isInt: Boolean
        get() = type == FieldType.INTEGER

    val isText: Boolean
        get() = type == FieldType.TEXT

    val isReal: Boolean
        get() = type == FieldType.REAL

    fun wrap(): String {
        return ("'".takeIf { isText } ?: EMPTY_STRING)
            .plus(value)
            .plus("'".takeIf { isText } ?: EMPTY_STRING)
    }
}

class IntValue(value: Int) : Value(value, FieldType.INTEGER)

class TextValue(value: String) : Value(value, FieldType.TEXT)

class RealValue(value: Double) : Value(value, FieldType.REAL)
