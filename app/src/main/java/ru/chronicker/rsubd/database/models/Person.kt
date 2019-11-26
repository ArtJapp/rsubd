package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val ID = "ID"
private const val FIRST_NAME = "FIRST_NAME"
private const val SECOND_NAME = "SECOND_NAME"
private const val LAST_NAME = "LAST_NAME"
private const val LOGIN = "LOGIN"
private const val PASSWORD = "PASSWORD"

/**
 * Общая маппинг-модель человека
 */
class Person : Entity(
    name = "Person",
    fields = mutableListOf(
        IntField(
            name = ID,
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        Field(
            name = FIRST_NAME,
            type = FieldType.TEXT,
            title = "Имя"
        ),
        Field(
            name = SECOND_NAME,
            type = FieldType.TEXT,
            title = "Отчество",
            isRequired = false
        ),
        Field(
            name = LAST_NAME,
            type = FieldType.TEXT,
            title = "Фамилия"
        ),
        Field(
            name = LOGIN,
            type = FieldType.TEXT,
            title = "login"
        ),
        PasswordField(
            name = PASSWORD,
            title = "Пароль"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        val firstName = values.find { it.first.name == FIRST_NAME }?.second?.toString() ?: EMPTY_STRING
        val secondName = values.find { it.first.name == SECOND_NAME }?.second?.toString() ?: EMPTY_STRING
        val lastName = values.find { it.first.name == LAST_NAME }?.second?.toString() ?: EMPTY_STRING
        return "$firstName $secondName $lastName"
    }
}