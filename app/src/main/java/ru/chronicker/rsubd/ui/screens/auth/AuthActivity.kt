package ru.chronicker.rsubd.ui.screens.auth

import android.util.Log
import kotlinx.android.synthetic.main.activity_auth.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.ui.base.BaseActivity

class AuthActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_auth

    private lateinit var dbHelper: DBHelper

    override fun initViews() {
        dbHelper = DBHelper(this)
        submit_auth_btn.setOnClickListener {
            val password = password_et.text.toString()
            val login = login_et.text.toString()
            dbHelper.authenticate(
                login,
                password,
                {
                    Log.d("auth", "auth succeed for role $it")
                },
                {
                    Log.d("auth", "holy shit")
                }
            )
        }
    }

}