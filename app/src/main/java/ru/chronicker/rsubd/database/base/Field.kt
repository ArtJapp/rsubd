package ru.chronicker.rsubd.database.base

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.SPACE
import ru.chronicker.rsubd.Scripts.AUTOINCREMENT
import ru.chronicker.rsubd.Scripts.CASCASE_DELETE
import ru.chronicker.rsubd.Scripts.CASCASE_UPDATE
import ru.chronicker.rsubd.Scripts.FOREIGN_KEY_INSTRUCTION
import ru.chronicker.rsubd.Scripts.PRIMARY_KEY
import java.io.Serializable

/**
 * Перечисление доступных типов данных
 */
enum class FieldType(val title: String) {
    NULL("NULL"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    TEXT("TEXT"),
    BLOB("BLOB")
}

/**
 * Общая модель поля, включает в себя название, тип
 * и флаг, является ли главным ключом
 */
open class Field(
    val name: String,
    val type: FieldType,
    val primaryKey: Boolean = false,
    val title: String = EMPTY_STRING
) : Serializable {

    /**
     * Преобразование содержимого поля в строку для запроса
     * на создание сущности
     */
    open fun formForCreate(): String {
        return "$name ${type.title}"
            .addWithSpaceIf(primaryKey, PRIMARY_KEY)
    }
}

/**
 * Модель поля с целочисленным значением.
 * Может использоваться для хранения ID, дат и булеанов.
 */
class IntField(
    name: String,
    primaryKey: Boolean = false,
    title: String = EMPTY_STRING,
    val autoIncrement: Boolean = false
) : Field(
    name = name,
    type = FieldType.INTEGER,
    primaryKey = primaryKey,
    title = title
), Serializable {

    override fun formForCreate(): String {
        return super.formForCreate()
            .addWithSpaceIf(autoIncrement, AUTOINCREMENT)
    }
}

/**
 * Модель поля с внешним ключом.
 * Включает в себя название таблицы и поля, на которые нужно сослаться
 */
class ForeignKeyField(
    name: String,
    type: FieldType,
    title: String = EMPTY_STRING,
    val foreignTable: String,
    val foreignKey: String,
    val isCascade: Boolean = false
) : Field(
    name = name,
    type = type,
    title = title
), Serializable {

    /**
     * Преобразование содержимого поля в инструкцию
     * для создания внешнего ключа
     */
    fun getForeignKeyInstruction(): String {
        return FOREIGN_KEY_INSTRUCTION.format(name, foreignTable, foreignKey)
            .addWithSpaceIf(isCascade, CASCASE_UPDATE)
            .addWithSpaceIf(isCascade, CASCASE_DELETE)
    }
}

fun String.addWithSpaceIf(condition: Boolean, value: String): String =
    this.addIf(condition, SPACE + value)

fun String.addIf(condition: Boolean, value: String): String =
    this.plus(value.takeIf { condition }
        ?: EMPTY_STRING)
