package ru.chronicker.rsubd.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType

abstract class BaseFragment<T : Entity, M : ItemModel> : Fragment() {

    abstract val screenName: String
    abstract val entity: T

    val layoutId: Int = R.layout.fragment_list_of_double_items

    private var currentId: Int = 0
    private var adapter: BaseAdapter<M>? = null
        set(value) {
            field = value
            items_rv.adapter = value
        }

    protected lateinit var dbHelper: DBHelper

    abstract fun initViews()
    abstract fun convertToItemModel(values: List<Pair<String, String>>): M

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            dbHelper = DBHelper(it)
        }
        initViews()
    }

    fun setListAdapter(adapter: BaseAdapter<M>) {
        this.adapter = adapter
    }

    fun loadData() {
        dbHelper.select(entity)
            .also {
                currentId = it.size + 1
            }
            .map { result ->
                result.fields
                    .map { (field, value) ->
                        Pair(field.name, getValue(value, field))
                    }
            }
            .map { pair ->
                convertToItemModel(pair)
            }
            .let {
                adapter?.setItems(it)
            }
    }

    private fun getValue(value: Any, field: Field): String {
        return when (field.type) {
            FieldType.TEXT -> value as String
            FieldType.INTEGER -> (value as Int).toString()
            FieldType.REAL -> (value as Float).toString()
            else -> EMPTY_STRING
        }
    }
}