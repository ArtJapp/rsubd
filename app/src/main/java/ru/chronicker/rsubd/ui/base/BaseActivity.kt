package ru.chronicker.rsubd.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.chronicker.rsubd.utils.storage.ConfigurationStorage

const val APP_PREFERENCES = "APP_PREFERENCES"

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var configurationStorage: ConfigurationStorage

    abstract val layoutId: Int
    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        configurationStorage = ConfigurationStorage(applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        initViews()
    }
}