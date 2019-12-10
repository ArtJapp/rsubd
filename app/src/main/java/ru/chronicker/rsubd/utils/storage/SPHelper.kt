package ru.chronicker.rsubd.utils.storage

import android.content.SharedPreferences
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.interactor.UserRole

private const val KEY_USER_ROLE = "KEY_USER_ROLE"
private const val KEY_USER_ID = "KEY_USER_ID"

/**
 * Хранилище для конфигурации приложения
 */
class ConfigurationStorage(
    private val noBackupSharedPref: SharedPreferences
) {

    var userRole: UserRole
        get() = UserRole.getByTitle(
            SettingsUtil.getString(
                noBackupSharedPref,
                KEY_USER_ROLE
            )
        )
        set(value) {
            SettingsUtil.putString(
                noBackupSharedPref,
                KEY_USER_ROLE, value.title
            )
        }

    var id: String
        get() = SettingsUtil.getString(noBackupSharedPref, KEY_USER_ID)
        set(value) = SettingsUtil.putString(noBackupSharedPref, KEY_USER_ID, value)

    fun clear() {
        SettingsUtil.putString(
            noBackupSharedPref,
            KEY_USER_ROLE, EMPTY_STRING
        )
    }
}