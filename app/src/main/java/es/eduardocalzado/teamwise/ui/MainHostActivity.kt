package es.eduardocalzado.teamwise.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R

@AndroidEntryPoint
class MainHostActivity : AppCompatActivity() {

    private lateinit var mainBottomNavigationView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_Teamwise_Main)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav_host)

        toolbar = findViewById(R.id.toolbar)
        mainBottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(mainBottomNavigationView.menu)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        mainBottomNavigationView.setupWithNavController(navController)

        // code needed to show/hide bottom bars, show/hide toolbars, any UI component.
        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.main_dest -> {
                    handleBottomNavVisibility(View.VISIBLE)
                    handleToolbarVisibility(View.VISIBLE)
                }
                R.id.info_dest -> {
                    handleBottomNavVisibility(View.VISIBLE)
                    handleToolbarVisibility(View.VISIBLE)
                }
                R.id.me_dest -> {
                    handleBottomNavVisibility(View.VISIBLE)
                    handleToolbarVisibility(View.VISIBLE)
                }
                R.id.players_dest -> {
                    handleBottomNavVisibility(View.GONE)
                    handleToolbarVisibility(View.VISIBLE)
                }
                else -> {
                    handleBottomNavVisibility(View.GONE)
                    handleToolbarVisibility(View.GONE)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun handleBottomNavVisibility(visible: Int) {
        mainBottomNavigationView.visibility = visible
    }

    private fun handleToolbarVisibility(visible: Int) {
        toolbar.visibility = visible
    }
}