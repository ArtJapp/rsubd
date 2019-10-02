package ru.chronicker.rsubd.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.chronicker.rsubd.DBConstants.DB_NAME
import ru.chronicker.rsubd.DBConstants.DB_VERSION
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.database.utils.QueryResponse
import ru.chronicker.rsubd.database.utils.ScriptConstructor

private val UNKNOWN_ERROR = "Unknown Error"

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

    fun selectAll(
        entity: Entity,
        transformer: (Cursor) -> Entity,
        response: QueryResponse<List<Entity>>
    ) {
        val sqLiteDatabase = readableDatabase

        var cursor: Cursor? = null
        val result = mutableListOf<Entity>()
        try {
            cursor = sqLiteDatabase.query(entity.name, null, null, null, null, null, null)

            if (cursor.moveToFirst()) {
                do {
                    result.add(transformer.invoke(cursor))
                } while (cursor.moveToNext())

                response.onSuccess(result)
            } else {
                response.onError("There are no student in database")
            }

        } catch (e: Exception) {
            response.onError(e.message ?: UNKNOWN_ERROR)
        } finally {
            sqLiteDatabase.close()
            cursor?.close()
        }
    }
}