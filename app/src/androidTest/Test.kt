package me.deedee.it4785_currencyconvertor

import android.content.Context

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@org.junit.runner.RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @org.junit.Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext: android.content.Context =
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
                .getTargetContext()
        org.junit.Assert.assertEquals("me.hanhngo.mycurrencyconverter", appContext.getPackageName())
    }
}