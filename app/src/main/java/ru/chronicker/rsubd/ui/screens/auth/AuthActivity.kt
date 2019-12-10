package ru.chronicker.rsubd.ui.screens.auth

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_auth.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.ui.base.BaseActivity
import ru.chronicker.rsubd.ui.screens.main.Main2Activity
import ru.chronicker.rsubd.utils.setStatusBarColor

class AuthActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_auth

    private lateinit var dbHelper: DBHelper

    override fun initViews() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        dbHelper = DBHelper(this)
        submit_auth_btn.setOnClickListener {
            val password = password_et.text.toString()
            val login = login_et.text.toString()
            dbHelper.authenticate(
                login,
                password,
                {
                    Log.d("auth", "auth succeed for role $it")
                    configurationStorage.userRole = it
                    openMainScreen()
                },
                {
                    Log.d("auth", "auth failed")
                    Snackbar.make(snackbar_container, it, Snackbar.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun openMainScreen() {
        startActivity(Intent(this, Main2Activity::class.java))
        finish()
    }
}