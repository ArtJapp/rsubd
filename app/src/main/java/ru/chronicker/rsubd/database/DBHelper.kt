package ru.chronicker.rsubd.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.chronicker.rsubd.DBConstants.DB_NAME
import ru.chronicker.rsubd.DBConstants.DB_VERSION
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.models.Disease

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
}