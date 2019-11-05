package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.*

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
        IntField(
            name = "IS_DISPANSERIZED",
            title = "Диспансеризован"
        )
    )
)