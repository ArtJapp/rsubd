package ru.chronicker.rsubd.ui.screens.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.patient.PatientFragmentView

class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): BaseFragment? {
        return getFragment(position)
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getFragment(position)
            ?.getName()
            ?: EMPTY_STRING
    }

    private fun getFragment(position: Int): BaseFragment? {
        return when (position) {
            0 -> PatientFragmentView()
            1 -> PatientFragmentView()
            else -> null
        }
    }
}