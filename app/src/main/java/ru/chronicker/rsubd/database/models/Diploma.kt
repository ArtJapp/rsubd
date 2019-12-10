package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.Constants
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Диплом №"

class Diploma : Entity(
    name = "Diploma",
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
            name = "SPECIALIZATION_ID",
            type = FieldType.INTEGER,
            foreignTable = "Specialization",
            foreignKey = "id",
            title = "Специализация",
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

//    override fun deepConvertToString(values: List<Pair<Field, Any>>, dbHelper: DBHelper): String {
//        return values.find { it.first.name == "PERSON_ID" }
//            ?.second?.toString()
//            ?.let { value ->
//                dbHelper.select("Person", mapOf(Constants.ID to "$value"))
//                    .map { it.fields }
//                    .map {
//                        Person().convertToString(it)
//                    }
//                    .firstOrNull()
//            }
//            ?: EMPTY_STRING
//    }
}