package ru.chronicker.rsubd.ui.screens.main.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.base.ItemModel
import ru.chronicker.rsubd.ui.screens.main.fragments.diseases.DiseaseFragmentView
import ru.chronicker.rsubd.ui.screens.main.fragments.patient.PatientFragmentView

class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): BaseFragment<Entity, ItemModel>? {
        return getFragment(position)
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getFragment(position)
            ?.screenName
            ?: EMPTY_STRING
    }

    private fun getFragment(position: Int): BaseFragment<Entity, ItemModel>? {
        return when (position) {
            0 -> DiseaseFragmentView() as BaseFragment<Entity, ItemModel>
            1 -> DiseaseFragmentView() as BaseFragment<Entity, ItemModel>
            else -> null
        }
    }
}