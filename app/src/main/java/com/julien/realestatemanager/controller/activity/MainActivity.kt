package com.julien.realestatemanager.controller.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.fragment.PropertyDetailFragment
import com.julien.realestatemanager.controller.fragment.PropertyListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var propertyList: PropertyListFragment
    private lateinit var propertyDetail: PropertyDetailFragment
    private lateinit var toolbar:Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyList = PropertyListFragment()
        propertyDetail = PropertyDetailFragment()

        this.configureToolBar()

        this.configureDrawerLayout()

        this.configureNavigationView()


       // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_list, propertyList)
            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                    add(R.id.frame_layout_property_detail,propertyDetail)

            }
        }


    }

    inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemid = item?.itemId

        if (itemid == R.id.action_add){
            val intent = Intent(this, CreatePropertyActivity::class.java)
            startActivity(intent)
            //val intent = Intent(this, NewPropertyActivity::class.java)
            //startActivity(intent)
        }
        if (itemid == R.id.action_edit){


        }
        if (itemid == R.id.action_search){
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }

    }
    private fun configureToolBar(){
        toolbar = findViewById(R.id.activity_main_toolbar)
        setSupportActionBar(toolbar)
    }

    private  fun configureDrawerLayout(){
        drawerLayout = findViewById(R.id.activity_main_drawer_layout)
        toolbar = findViewById(R.id.activity_main_toolbar)
        var toggle:ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView(){
        navigationView = findViewById(R.id.activity_main_nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }
}
