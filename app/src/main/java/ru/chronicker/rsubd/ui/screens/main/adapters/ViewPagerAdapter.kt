package ru.chronicker.rsubd.ui.screens.main.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.base.ItemModel
import ru.chronicker.rsubd.ui.screens.main.fragments.diseases.DiseaseFragmentView
import ru.chronicker.rsubd.ui.screens.main.fragments.patient.PatientFragmentView

private const val COUNT = 3

class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment<Entity, ItemModel> {
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

    private fun getFragment(position: Int): BaseFragment<Entity, ItemModel> {
        return when (position) {
            0 -> PatientFragmentView() as BaseFragment<Entity, ItemModel>
            1 -> DiseaseFragmentView() as BaseFragment<Entity, ItemModel>
            2 -> DiseaseFragmentView() as BaseFragment<Entity, ItemModel>
            else -> throw IllegalArgumentException("Unknown position: $position")
        }
    }
}