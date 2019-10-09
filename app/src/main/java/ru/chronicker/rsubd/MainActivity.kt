package ru.chronicker.rsubd

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntValue
import ru.chronicker.rsubd.database.base.TextValue
import ru.chronicker.rsubd.database.models.Disease

class MainActivity : AppCompatActivity() {

    private val dbHelper = DBHelper(this)
    private var ids: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        loadData()
        insertData.setOnClickListener {
            insertMoreData()
            loadData()
        }
    }

    private fun getValue(value: Any, field: Field): String {
        return when(field.type) {
            FieldType.TEXT -> value as String
            FieldType.INTEGER -> (value as Int).toString()
            FieldType.REAL -> (value as Float).toString()
            else -> EMPTY_STRING
        }
    }

    private fun insertSomeData() {
        dbHelper.insert(Disease(), listOf(IntValue(1), TextValue("Вавка")))
        dbHelper.insert(Disease(), listOf(IntValue(2), TextValue("Ранка")))
        dbHelper.insert(Disease(), listOf(IntValue(3), TextValue("Болячка")))
    }

    private fun insertMoreData() {
        dbHelper.insert(Disease(), listOf(IntValue(ids), TextValue("Вавка")))
    }

    private fun loadData() {
        dbHelper.select(Disease())
            .also {
                ids = it.size + 1
            }
            .map { result ->
                result.fields
                    .map { (field, value) ->
                        Pair(field.name, getValue(value, field))
                    }
                    .let {(field, value) ->
                        "$field: $value"
                    }
            }
            .joinToString("\n")
            .also {
                text.text = it
            }
    }
}
