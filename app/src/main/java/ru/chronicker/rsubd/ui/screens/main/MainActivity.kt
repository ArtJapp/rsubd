package ru.chronicker.rsubd.ui.screens.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.form.DiseaseRoute
import ru.chronicker.rsubd.ui.screens.main.adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
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
        initNavigation()
    }

    private fun initNavigation() {
        val content = findViewById<ViewPager>(R.id.content)
        content.adapter = viewPagerAdapter
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_patients -> 0
                R.id.action_doctors -> 1
                R.id.action_diseases -> 2
                else -> 0
            }
                .also {
                    content.currentItem = it
                }
                .also {
                    findViewById<Toolbar>(R.id.toolbar).title = viewPagerAdapter.getPageTitle(it)
                }
            true
        }
    }
}
