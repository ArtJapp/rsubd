package ru.chronicker.rsubd.database.utils

import ru.chronicker.rsubd.Scripts.CREATE
import ru.chronicker.rsubd.Scripts.DROP
import ru.chronicker.rsubd.database.base.Entity

class ScriptConstructor {

    companion object {

        fun formCreate(entity: Entity): String {
            entity.fields.joinToString(separator = ", ") {
                it.formForCreate()
            }.also {
                return CREATE.format(entity.name, it)
            }
        }

        fun formDrop(entityName: String): String {
            return DROP.format(entityName)
        }
    }
}