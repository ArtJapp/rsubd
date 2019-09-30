package ru.chronicker.rsubd.database.base

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.SPACE
import ru.chronicker.rsubd.Scripts.AUTOINCREMENT
import ru.chronicker.rsubd.Scripts.FOREIGN_KEY_INSTRUCTION
import ru.chronicker.rsubd.Scripts.PRIMARY_KEY

enum class FieldType(val title: String) {
    NULL("NULL"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    TEXT("TEXT"),
    BLOB("BLOB")
}

open class Field(
    val name: String,
    val type: FieldType,
    val primaryKey: Boolean = false
) {
    open fun formForCreate(): String {
        return "$name ${type.title}"
            .addWithSpaceIf(primaryKey, PRIMARY_KEY)
    }
}

class IntField(
    name: String,
    primaryKey: Boolean = false,
    val autoIncrement: Boolean = false
) : Field(
    name,
    FieldType.INTEGER,
    primaryKey
) {

    override fun formForCreate(): String {
        return super.formForCreate()
            .addWithSpaceIf(autoIncrement, AUTOINCREMENT)
    }
}

class ForeignKeyField(
    name: String,
    type: FieldType,
    val foreignTable: String,
    val foreignKey: String
): Field(name, type) {

    fun getForeignKeyInstruction(): String {
        return FOREIGN_KEY_INSTRUCTION.format(name, foreignTable, foreignKey)
    }
}

fun String.addWithSpaceIf(condition: Boolean, value: String): String =
    this.addIf(condition, SPACE + value)

fun String.addIf(condition: Boolean, value: String): String =
        this.plus(value.takeIf { condition }
            ?: EMPTY_STRING)
