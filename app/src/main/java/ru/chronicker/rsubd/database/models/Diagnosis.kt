package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.*

class Diagnosis : Entity(
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
)