package com.whyncode.ipgetter.view

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.whyncode.ipgetter.R
import kotlinx.android.synthetic.main.activity_myip.*
import java.net.InetAddress

class MyIpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myip)

        bt_getIp.setOnClickListener {
            getIpAddress()
        }
    }

    private fun getIpAddress(){

        val wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo
        val ipAddress = connectionInfo.ipAddress

        val ipString = String.format(
                "%d.%d.%d.%d",
                ipAddress and 0xff,
                ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff,
                ipAddress shr 24 and 0xff
        )

        tv_showIp.text = ipString
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_LocalIp -> {
                val intent = Intent(this,NetworkIpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_NetworkInfo -> {
                val intent = Intent(this,NetworkInfoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


