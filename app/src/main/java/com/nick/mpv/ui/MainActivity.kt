package com.nick.mpv.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.nick.mpv.R
import com.nick.mpv.databinding.ActivityMainBinding
import com.nick.mpv.frame.BaseActivity

/**
 * 使用泛型绑定<V, P>，得到绑定后的P层实例[presenter]
 */
class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    /**
     * Demo使用[binding]绑定xml方式，当前这一句这一句已经无用
     */
    override fun getContentLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { _ ->
            showDialog("开始请求数据")
            presenter.requestLogout()
        }
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    override fun onLoadingDialog(show: Boolean) {
        if (show) {
            showDialog()
        } else {
            dismissDialog()
        }
    }

    override fun onSuccess(errorCode: Int, message: String) {
        Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onFailure(errorCode: Int, message: String) {
        Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG).show()
    }
}