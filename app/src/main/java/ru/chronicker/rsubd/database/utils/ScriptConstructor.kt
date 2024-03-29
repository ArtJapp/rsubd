package ru.chronicker.rsubd.database.utils

import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.Scripts.AUTH_QUERY
import ru.chronicker.rsubd.Scripts.CREATE
import ru.chronicker.rsubd.Scripts.CREATE_VIEW
import ru.chronicker.rsubd.Scripts.DELETE
import ru.chronicker.rsubd.Scripts.DROP
import ru.chronicker.rsubd.Scripts.DROP_VIEW
import ru.chronicker.rsubd.Scripts.GET_MAX_ID
import ru.chronicker.rsubd.Scripts.GET_ROLE
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
            return values
                .joinToString(", ") { it.wrap() }
                .let { value ->
                    entity.fields
                        .joinToString(", ") { it.name }
                        .let { parameters ->
                            INSERT.format(entity.name, parameters, value)
                        }
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

        fun formQueryMaxId(entity: Entity): String {
            return GET_MAX_ID.format(entity.name)
        }

        fun formCreateView(name: String, baseScript: String): String {
            return CREATE_VIEW.format(name, baseScript)
        }

        fun formDropView(name: String): String {
            return DROP_VIEW.format(name)
        }

        fun formAuthQuery(login: String, password: String): String {
            return AUTH_QUERY.format(login, password)
        }

        fun formGetRoleQuery(id: String): String {
            return GET_ROLE.format(id, id, id)
        }

        fun formAuthViewsQuery(): List<String> {
            return listOf(
                formCurrentPatients(),
                formCurrentDoctors(),
                formCurrentAdmins()
            )
        }

        private fun formCurrentPatients(): String {
            return "CREATE VIEW CURRENT_PATIENTS AS\n" +
                    "SELECT DISTINCT Patient.PERSON_ID\n" +
                    "FROM Patient LEFT JOIN History H on Patient.ID = H.PATIENT_ID LEFT JOIN Diagnosis D on H.DIAGNOSIS_ID = D.ID LEFT JOIN Treatment T on D.TREATMENT = T.ID\n" +
                    "WHERE date(TREATMENT_START, 'unixepoch') <= date('now') and date(TREATMENT_END, 'unixepoch') >= date('now');"
        }

        private fun formCurrentDoctors(): String {
            return "CREATE VIEW CURRENT_DOCTORS AS\n" +
                    "SELECT DISTINCT PERSON_ID\n" +
                    "FROM Doctor\n" +
                    "WHERE PERSON_ID not in CURRENT_PATIENTS;"
        }

        private fun formCurrentAdmins(): String {
            return "CREATE VIEW CURRENT_HEADS AS\n" +
                    "SELECT DISTINCT PERSON_ID\n" +
                    "FROM Doctor LEFT JOIN Person P on Doctor.PERSON_ID = P.ID\n" +
                    "WHERE Doctor.IS_HEAD = 1 AND Doctor.PERSON_ID in CURRENT_DOCTORS;"
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
                }.let { instructions ->
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