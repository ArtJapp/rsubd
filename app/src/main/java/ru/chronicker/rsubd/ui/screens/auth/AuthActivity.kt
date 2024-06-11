package ru.chronicker.rsubd.ui.screens.auth

import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.Doctor
import ru.chronicker.rsubd.database.models.Person
import ru.chronicker.rsubd.database.models.Qualification
import ru.chronicker.rsubd.databinding.ActivityAuthBinding
import ru.chronicker.rsubd.ui.base.BaseActivity
import ru.chronicker.rsubd.ui.screens.main.Main2Activity
import ru.chronicker.rsubd.utils.setStatusBarColor

class AuthActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_auth

    private lateinit var dbHelper: DBHelper

    private val submit_auth_btn get() = findViewById<Button>(R.id.submit_auth_btn)
    private val password_et get() = findViewById<TextInputEditText>(R.id.password_et)
    private val login_et get() = findViewById<TextInputEditText>(R.id.login_et)
    private val snackbar_container get() = findViewById<ConstraintLayout>(R.id.snackbar_container)

    override fun initViews() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        dbHelper = DBHelper(this)
//        addSomeTestData()
        submit_auth_btn.setOnClickListener {
            val password = password_et.text.toString()
            val login = login_et.text.toString()
            dbHelper.authenticate(
                login,
                password,
                { role, id ->
                    Log.d("auth", "auth succeed for role $it")
                    configurationStorage.userRole = role
                    configurationStorage.id = id.toString()
                    openMainScreen()
                },
                {
                    Log.d("auth", "auth failed")
                    Snackbar.make(snackbar_container, it, Snackbar.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun addSomeTestData() {
        dbHelper.insert(
            Person(),
            values = listOf(
                Value(0, FieldType.INTEGER),
                Value("admin", FieldType.TEXT),
                Value("admin", FieldType.TEXT),
                Value("admin", FieldType.TEXT),
                Value("admin", FieldType.TEXT),
                Value("admin", FieldType.TEXT),
            )
        )
        dbHelper.insert(
            Qualification(),
            values = listOf(
                Value(0, FieldType.INTEGER),
                Value("Верховный пидор", FieldType.TEXT),
            )
        )
        dbHelper.insert(
            Doctor(),
            values = listOf(
                Value(0, FieldType.INTEGER),
                Value(0, FieldType.INTEGER),
                Value(0, FieldType.INTEGER),
                Value(1, FieldType.INTEGER),
            )
        )
    }

    private fun openMainScreen() {
        startActivity(Intent(this, Main2Activity::class.java))
        finish()
    }
}