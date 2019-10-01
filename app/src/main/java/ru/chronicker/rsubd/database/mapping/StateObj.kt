package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField
import ru.chronicker.rsubd.domain.Model
import ru.chronicker.rsubd.domain.State

/**
 * Маппинг-модель состояния пациента
 */
class StateObj : Entity(
    name = "State",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        Field(
            name = "NAME",
            type = FieldType.TEXT
        )
    )
) {
//    override fun convertToModel(): State =
//        State(
//            id = values["ID"] as Int,
//            name = values["NAME"] as String
//        )
}