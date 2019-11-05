package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.IntField

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
)