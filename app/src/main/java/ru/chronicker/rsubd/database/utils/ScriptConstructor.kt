package ru.chronicker.rsubd.database.utils

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.Scripts.CREATE
import ru.chronicker.rsubd.Scripts.DROP
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.ForeignKeyField

/**
 * Конструктор скриптов
 */
class ScriptConstructor {

    companion object {

        /**
         * Генерация скрипта на создание сущности
         */
        fun formCreate(entity: Entity): String {
            with(entity.fields) {
                return sortFields(this)
                    .joinToString(separator = ", ") {
                        it.formForCreate()
                    }
                    .let {
                        CREATE.format(
                            entity.name,
                            it + constructForeignInstructions(this)
                        )
                    }
            }
        }

        /**
         * Генерация скрипта на удаление сущности
         */
        fun formDrop(entityName: String): String {
            return DROP.format(entityName)
        }

        private fun sortFields(fields: List<Field>): List<Field> {
            return fields.sortedWith(Comparator<Field> { firstField, secondField ->
                if (firstField is ForeignKeyField) {
                    return@Comparator 1
                }
                if (secondField is ForeignKeyField) {
                    return@Comparator -1
                }
                return@Comparator 0
            })
        }

        private fun constructForeignInstructions(fields: List<Field>): String {
            return fields.filterIsInstance<ForeignKeyField>()
                .joinToString(separator = ", ") {
                    it.getForeignKeyInstruction()
                }.let {  instructions ->
                    // дополняем запятой в начале строки, если она непуста
                    ", ".takeIf { instructions.isNotBlank() }
                        ?.plus(instructions)
                        ?: EMPTY_STRING
                }
        }
    }
}