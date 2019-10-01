package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField
import ru.chronicker.rsubd.domain.Disease

internal const val ID = "ID"
internal const val NAME = "NAME"

/**
 * Маппинг-модель болезни
 */
class DiseaseObj : Entity(
    name = "Disease",
    fields = mutableListOf(
        IntField(
            name = ID,
            primaryKey = true,
            autoIncrement = true
        ),
        Field(
            name = NAME,
            type = FieldType.TEXT
        )
    )
) {

    override fun convertToModel(): Disease =
        Disease(
            id = values[ID] as Int,
            name = values[NAME] as String
        )
}