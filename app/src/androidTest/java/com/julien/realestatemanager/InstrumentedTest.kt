package com.julien.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.julien.realestatemanager.models.Utils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {



    @Test
    fun testNetwork(){
        val appContext = InstrumentationRegistry.getTargetContext()

        val connectivityManager=appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo

        assertEquals(Utils.isInternetAvailable(appContext),networkInfo.isConnected)

    }
}
