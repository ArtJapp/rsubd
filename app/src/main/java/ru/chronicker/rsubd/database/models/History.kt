package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Запись о посещении #"

/**
 * Маппинг-модель истории посещения пациентом врача,
 * а также поставленный диагноз
 */
class History : Entity(
    name = "History",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        ForeignKeyField(
            name = "DOCTOR_ID",
            type = FieldType.INTEGER,
            foreignTable = "Doctor",
            foreignKey = "id",
            title = "Доктор",
            isCascade = true
        ),
        ForeignKeyField(
            name = "PATIENT_ID",
            type = FieldType.INTEGER,
            foreignTable = "Patient",
            foreignKey = "id",
            title = "Пациент",
            isCascade = true
        ),
        ForeignKeyField(
            name = "DIAGNOSIS_ID",
            type = FieldType.INTEGER,
            foreignTable = "Diagnosis",
            foreignKey = "id",
            title = "Диагноз",
            isCascade = true
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