package com.julien.realestatemanager

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
}
