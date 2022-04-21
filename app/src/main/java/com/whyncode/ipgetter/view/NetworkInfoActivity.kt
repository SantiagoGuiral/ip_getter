package com.whyncode.ipgetter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.whyncode.ipgetter.R

class NetworkInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_info)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar_v3, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_myIp -> {
                val intent = Intent(this,MyIpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_ping -> {
                //TODO
            }
            R.id.nav_LocalIp->{
                val intent = Intent(this,NetworkIpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
