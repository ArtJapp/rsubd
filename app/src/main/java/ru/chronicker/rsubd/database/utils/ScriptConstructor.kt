package ru.chronicker.rsubd.database.utils

import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.Scripts.CREATE
import ru.chronicker.rsubd.Scripts.DELETE
import ru.chronicker.rsubd.Scripts.DROP
import ru.chronicker.rsubd.Scripts.INSERT
import ru.chronicker.rsubd.Scripts.SELECT
import ru.chronicker.rsubd.Scripts.SELECT_WITH_CONDITION
import ru.chronicker.rsubd.Scripts.UPDATE
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.Value

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

        /**
         * Генерация скрипта на чтение всех полей сущности
         */
        fun formSelect(entity: Entity, conditions: Map<String, String> = emptyMap()): String {
            return entity.fields
                .joinToString(separator = ", ") { field -> field.name }
                .let { params ->
                    (SELECT.takeIf { conditions.isEmpty() } ?: SELECT_WITH_CONDITION)
                    .format(
                        params,
                        "main." + entity.name,
                        formConditions(conditions)
                    )
                }
        }

        fun formInsert(entity: Entity, values: List<Value>): String {
            return values.joinToString(", ") { it.wrap() }
                .let {  parameters ->
                    INSERT.format(entity.name, parameters)
                }
        }

        fun formUpdate(entity: Entity, values: List<Value>): String {
            val conditions: MutableMap<String, Value> = mutableMapOf()
            val entityValues: MutableMap<String, Value> = mutableMapOf()
            entity.fields.forEachIndexed { index, field ->
                if (field.primaryKey) {
                    conditions[field.name] = values[index]
                } else {
                    entityValues[field.name] = values[index]
                }
            }
            val condition = conditions.map { (key, value) -> "$key = ${value.wrap()}" }
                .joinToString(separator = ", ")
            return entityValues.map { (key, value) -> "$key = ${value.wrap()}" }
                .joinToString(separator = ", ")
                .let {
                    UPDATE.format(entity.name, it, condition)
                }
        }

        fun formDelete(entity: Entity, values: List<Value>): String {
            val conditions: MutableMap<String, Value> = mutableMapOf()
            conditions[ID] = values[0]
            val condition = conditions.map { (key, value) -> "$key = ${value.wrap()}" }
                .joinToString(separator = ", ")
            return DELETE.format(entity.name, condition)
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

        private fun formConditions(values: Map<String, String>): String {
            return values.map { (key, value) -> "$key = $value" }
                .joinToString(", ")
        }
    }
}