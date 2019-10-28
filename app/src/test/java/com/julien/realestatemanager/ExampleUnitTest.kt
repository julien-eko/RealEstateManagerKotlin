package com.julien.realestatemanager

import com.julien.realestatemanager.controller.activity.SimulatorActivity
import com.julien.realestatemanager.models.Utils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun covertEuroToDollar() {
        assertEquals(12, Utils.convertEuroToDollar(10))
    }

    @Test
    @Throws(Exception::class)
    fun covertDollarToEuro() {
        assertEquals(8, Utils.convertDollarToEuro(10))
    }

    @Test
    @Throws(Exception::class)
    fun dateFormat() {
        assertEquals("24/06/2019", Utils.getTodayDate())
    }

    @Test
    fun TestMonthPrice(){
        var result:Float
        var price:Float = (100000F + (100000F * (5F / 100F)))
        price -= 10000F
        var time:Float = (1F * 12F)
        result = price / time

        assertEquals(result,SimulatorActivity().monthPrice(100000F,5F,10000F,1F))
    }

    @Test
    fun testTotalPrice(){
        var price:Float = (100000F + (100000F * (5F / 100F)))

        assertEquals(price,SimulatorActivity().totalPrice(100000F,5F))

    }


}
