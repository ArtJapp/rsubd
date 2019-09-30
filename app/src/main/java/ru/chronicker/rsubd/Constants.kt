package ru.chronicker.rsubd

object DBConstants {
    const val DB_NAME = "polyclinic_db"
    const val DB_VERSION = 1
}

const val EMPTY_STRING = ""
const val EMPTY_INT = 0
const val EMPTY_REAL = 0.0
val EMPTY_BLOB = Any()
const val SPACE = " "

object Scripts {
    const val PRIMARY_KEY = "PRIMARY KEY"
    const val AUTOINCREMENT = "AUTOINCREMENT"
    const val CREATE = "CREATE TABLE %s(%s)"
    const val DROP = "DROP TABLE IF EXISTS %s"
}