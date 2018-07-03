package pt.isel.ps.g30.tollingsystem.utils


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build


class NetworkUtils(val context: Context) {


    fun isConnected():Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }
    fun isWifiConnected():Boolean {
        return isConnected(ConnectivityManager.TYPE_WIFI)
    }
    fun isMobileConnected():Boolean {
        return isConnected(ConnectivityManager.TYPE_MOBILE)
    }
    @SuppressLint("ObsoleteSdkInt")
    private fun isConnected(type:Int):Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            val networkInfo = connMgr.getNetworkInfo(type)
            return networkInfo != null && networkInfo.isConnected
        }
        else
        {
            return isConnected(connMgr, type)
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun isConnected(connMgr:ConnectivityManager, type:Int):Boolean {
        val networks = connMgr.allNetworks
        var networkInfo:NetworkInfo
        for (mNetwork in networks)
        {
            networkInfo = connMgr.getNetworkInfo(mNetwork)
            if (!(networkInfo == null || networkInfo.type !== type || !networkInfo.isConnected))
            {
                return true
            }
        }
        return false
    }
}