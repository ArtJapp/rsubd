package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Диагноз #"

class Diagnosis : Entity(
    name = "Diagnosis",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        ForeignKeyField(
            name = "DISEASE_ID",
            type = FieldType.INTEGER,
            foreignTable = "Disease",
            foreignKey = "id",
            title = "Болезнь",
            isCascade = true
        ),
        ForeignKeyField(
            name = "STATE_ID",
            type = FieldType.INTEGER,
            foreignTable = "State",
            foreignKey = "id",
            title = "Состояние"
        ),
        ForeignKeyField(
            name = "TREATMENT",
            type = FieldType.INTEGER,
            foreignTable = "Treatment",
            foreignKey = "id",
            title = "Лечение"
        ),
        BooleanField(
            name = "IS_DISPANSERIZED",
            title = "Диспансеризован"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == "ID" }
            ?.second
            ?.let {
                TITLE + it.toString()
            }
            ?: EMPTY_STRING
    }

    override fun convertMappedToString(values: List<Pair<String, String>>): String {
        return values.find { it.first == "ID" }
            ?.second
            ?.let {
                TITLE + it
            }
            ?: EMPTY_STRING
    }
}