package com.whyncode.ipgetter.view

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.whyncode.ipgetter.R
import java.lang.ref.WeakReference
import java.net.InetAddress
import java.net.NetworkInterface

class NetworkIpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networkip)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar_v2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_myIp -> {
                val intent = Intent(this,MyIpActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_search -> {
                val ipList : MutableList<String> = ArrayList()

                val listViewIp = findViewById<ListView>(R.id.ipListLocalNetwork)
                val task = MyAsyncTask(this,listViewIp,ipList)
                task.execute()
            }
            R.id.nav_NetworkInfo->{
                val intent = Intent(this,NetworkInfoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    class MyAsyncTask internal constructor(context: Context,listViewIp: ListView,ipList: MutableList<String>) : AsyncTask<Void,String,Void>() {

        private val mContextRef:WeakReference<Context> = WeakReference<Context>(context)
        private val adapter = ArrayAdapter(context,R.layout.card_ip_address,R.id.card_ipAddress,ipList)
        private val listView = listViewIp
        private val thisContext = context

        override fun onPreExecute() {
            listView.adapter = adapter
            Toast.makeText(thisContext,"Scanning IP's",Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg p0: Void?): Void? {

            try
            {
                val context = mContextRef.get()
                if (context != null)
                {
                    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val connectionInfo = wifiManager.connectionInfo
                    val ipAddress = connectionInfo.ipAddress

                    val ipString = String.format(
                        "%d.%d.%d.%d",
                        ipAddress and 0xff,
                        ipAddress shr 8 and 0xff,
                        ipAddress shr 16 and 0xff,
                        ipAddress shr 24 and 0xff
                    )

                    val prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1)
                    for (i in 0..254)
                    {
                        val testIp = prefix + (i).toString()
                        val address = InetAddress.getByName(testIp)
                        val reachable = address.isReachable(1000)

                        if (reachable) {
                            publishProgress(testIp)
                        }
                    }
                }
                else
                {
                    Log.i("error:","Context is null")
                }
            }
            catch (t:Throwable) {
                Log.e("Sync", "Well that's not good.", t)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            adapter.add(values[0])
            adapter.notifyDataSetInvalidated()
        }
    }

}
