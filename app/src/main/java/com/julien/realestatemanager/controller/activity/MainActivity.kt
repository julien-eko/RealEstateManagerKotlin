package com.julien.realestatemanager.controller.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.julien.realestatemanager.controller.fragment.PropertyListFragment
import com.julien.realestatemanager.controller.fragment.SelectPropertyFragment
import com.julien.realestatemanager.controller.fragment.SearchFragment


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var propertyList: PropertyListFragment
    private lateinit var selectPropertyFragment: SelectPropertyFragment
    private lateinit var toolbar:Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyList = PropertyListFragment()
        selectPropertyFragment = SelectPropertyFragment()

        this.configureToolBar()

        this.configureDrawerLayout()

        this.configureNavigationView()

       // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_list, propertyList)
            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                   add(R.id.frame_layout_property_detail,selectPropertyFragment)

            }
        }


    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
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
            val intent = Intent(this, PropertyActivity::class.java)
            intent.putExtra("isNewProperty",true)
            startActivity(intent)
            //val intent = Intent(this, NewPropertyActivity::class.java)
            //startActivity(intent)
        }
        if (itemid == R.id.action_edit){

            val id:String = propertyList.idProperty

            if(id == "0"){
                Toast.makeText(this,getString(R.string.choose_property),Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, PropertyActivity::class.java)
                intent.putExtra("isNewProperty",false)
                intent.putExtra("id",id)
                startActivity(intent)
            }


        }
        if (itemid == R.id.action_search){
            val searchFragment = SearchFragment()
            searchFragment.show(supportFragmentManager,"test")

        }

        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var itemid = p0?.itemId

        if (itemid == R.id.activity_main_drawer_map){
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        if (itemid == R.id.activity_main_drawer_settings){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }


        return super.onOptionsItemSelected(p0)
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
        var toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView(){
        navigationView = findViewById(R.id.activity_main_nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun updateList(typeProperty: String,
                   minArea: Int,
                   maxArea: Int,
                   minPrice: Int,
                   maxPrice: Int,
                   minDateOfSale: Long,
                   maxDateOfSale: Long,
                   statut: String,
                   minDateOfCreated: Long,
                   maxDateOfCreated: Long,
                   city:String,
                   minRoom: Int,
                   maxRoom: Int){
        propertyList.searchProperties(typeProperty,
            minArea,
            maxArea,
            minPrice,
            maxPrice,
            minDateOfSale,
            maxDateOfSale,
            statut,
            minDateOfCreated,
            maxDateOfCreated,
            city,
            minRoom,
            maxRoom)
    }
}
