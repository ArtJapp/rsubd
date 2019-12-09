package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.Constants
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Пациент #"

/**
 * Маппинг-модель пациента
 */
class Patient : Entity(
    name = "Patient",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        DateField(
            name = "BIRTH_DATE",
            title = "Дата рождения"
        ),
        ForeignKeyField(
            name = "PERSON_ID",
            type = FieldType.INTEGER,
            foreignTable = "Person",
            foreignKey = "id",
            title = "Персональные данные",
            isCascade = true
        ),
        ForeignKeyField(
            name = "STATUS_ID",
            type = FieldType.INTEGER,
            foreignTable = "SocialStatus",
            foreignKey = "id",
            title = "Социальный статус"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == "PERSON_ID" }
            ?.second
            ?.let {
                TITLE + it.toString()
            }
            ?: EMPTY_STRING
    }

    override fun deepConvertToString(values: List<Pair<Field, Any>>, dbHelper: DBHelper): String {
        return values.find { it.first.name == "PERSON_ID" }
            ?.second
            ?.let { value ->
                dbHelper.select("Person", mapOf(Constants.ID to "$value"))
                    .map { it.fields }
                    .map {
                        Person().convertToString(it)
                    }
                    .firstOrNull()
            }
            ?: EMPTY_STRING
    }
}