package ru.chronicker.rsubd.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

class Person : Entity(
    name = "Person",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        Field(
            name = "FIRST_NAME",
            type = FieldType.TEXT
        ),
        Field(
            name = "SECOND_NAME",
            type = FieldType.TEXT
        ),
        Field(
            name = "LAST_NAME",
            type = FieldType.TEXT
        )
    )
)