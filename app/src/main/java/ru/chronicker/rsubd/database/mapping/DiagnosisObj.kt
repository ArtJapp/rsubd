package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.*
import ru.chronicker.rsubd.domain.Diagnosis
import ru.chronicker.rsubd.domain.Model

class DiagnosisObj : Entity(
    name = "Diagnosis",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        ForeignKeyField(
            name = "DISEASE_ID",
            type = FieldType.INTEGER,
            foreignTable = "Disease",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "STATE_ID",
            type = FieldType.INTEGER,
            foreignTable = "State",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "TREATMENT",
            type = FieldType.INTEGER,
            foreignTable = "Treatment",
            foreignKey = "id"
        ),
        IntField(
            name = "IS_DISPANSERIZED"
        )
    )
) {
//    override fun convertToModel(): Diagnosis =
//        Diagnosis(
//            id = values["ID"] as Int,
//            treatment = values["NAME"] as String
//        )
}