package ru.chronicker.rsubd.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ru.chronicker.rsubd.DBConstants.DB_NAME
import ru.chronicker.rsubd.DBConstants.DB_VERSION
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formInsert
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formSelect

private const val DB_HELPER = "DB_HELPER"

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val entities = listOf(
        Disease()
    )

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { database ->
            entities.forEach { entity ->
                database.execSQL(ScriptConstructor.formCreate(entity))
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropAllDatabases(db)
        onCreate(db)
    }

    private fun dropAllDatabases(db: SQLiteDatabase?) {
        db?.let { database ->
            entities.map { it.name }
                .forEach { entityName ->
                    database.execSQL(ScriptConstructor.formDrop(entityName))
                }
        }
    }

    /**
     * Метод выборки данных по таблице/сущности
     */
    fun select(entity: Entity, params: Array<String>? = null): List<QueryResult> {
        val result = mutableListOf<QueryResult>()
        val query = formSelect(entity)
        log(query)
        val response = readableDatabase.rawQuery(query, params)
        if (response.moveToFirst()) {
            do {
                result.add(combineFieldsToValues(response, entity.fields))
            } while (response.moveToNext())
        }
        response.close()
        return result
    }

    /**
     * Метод загрузки данных сущности в базу данных
     */
    fun insert(entity: Entity, values: List<Value>) {
        val request = formInsert(entity, values)
        log(request)
        writableDatabase.execSQL(request)
    }

    private fun combineFieldsToValues(resultSet: Cursor, fields: List<Field>): QueryResult {
        return QueryResult(
            fields = fields.mapIndexed { index, field ->
                field to when (field.type) {
                    FieldType.TEXT -> resultSet.getString(index)
                    FieldType.INTEGER -> resultSet.getInt(index)
                    FieldType.REAL -> resultSet.getFloat(index)
                    else -> EMPTY_INT
                }
            }
        )
    }

    private fun log(request: String) {
        Log.d(DB_HELPER, request)
    }
}

data class QueryResult(
    val fields: List<Pair<Field, Any>>
)