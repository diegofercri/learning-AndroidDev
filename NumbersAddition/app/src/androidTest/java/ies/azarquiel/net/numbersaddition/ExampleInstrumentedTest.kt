<<<<<<<< HEAD:NumbersAddition/app/src/androidTest/java/ies/azarquiel/net/numbersaddition/ExampleInstrumentedTest.kt
package ies.azarquiel.net.numbersaddition
========
package net.azarquiel.carrojpc
>>>>>>>> 22fc1fb733f2370cce4b4ce404efb3827735333a:CarroJPC/app/src/androidTest/java/net/azarquiel/carrojpc/ExampleInstrumentedTest.kt

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
<<<<<<<< HEAD:NumbersAddition/app/src/androidTest/java/ies/azarquiel/net/numbersaddition/ExampleInstrumentedTest.kt
        assertEquals("ies.azarquiel.net.numbersaddition", appContext.packageName)
========
        assertEquals("net.azarquiel.carrojpc", appContext.packageName)
>>>>>>>> 22fc1fb733f2370cce4b4ce404efb3827735333a:CarroJPC/app/src/androidTest/java/net/azarquiel/carrojpc/ExampleInstrumentedTest.kt
    }
}