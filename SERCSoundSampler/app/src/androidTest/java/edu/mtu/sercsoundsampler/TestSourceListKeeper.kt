package edu.mtu.sercsoundsampler

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.mtu.sercsoundsampler.model.SERCPreferencesHelper
import edu.mtu.sercsoundsampler.model.SERCSoundDatabase
import edu.mtu.sercsoundsampler.model.SERCSourceAdapter
import edu.mtu.sercsoundsampler.model.SourceListKeeper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestSourceListKeeper {
    val prefs: SharedPreferences
    val adapter: SERCSourceAdapter
    val helper: SERCPreferencesHelper
    val keeper: SourceListKeeper
    val db: SERCSoundDatabase
    init {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        prefs = appContext.getSharedPreferences(SERCSamplingAllInOneActivity.KEY_PREFS + "_TEST", Context.MODE_PRIVATE)
        helper = SERCPreferencesHelper(prefs)
        keeper = SourceListKeeper(helper)
        db = SERCSoundDatabase(appContext.resources.getString(R.string.bad_item))
        adapter = SERCSourceAdapter(appContext, prefs, helper, keeper, db)
    }
    @Before
    fun setUp() {
        helper.clearAll()
    }
    @Test
    fun ignoreDuplicateAdd() {
        keeper.add("Fan")
        keeper.add("Fan")
        keeper.add("Lawnmower")
        assertEquals("Expect 2", 2, keeper.list().size)
    }
    @Test
    fun preserveOrder() {
        keeper.add("C")
        keeper.add("B")
        keeper.add("A")
        assertEquals("Expect A as the zeroth element", "A", keeper.list()[0])
        assertTrue("a-b-c", "B" == keeper.list()[1] && "C" == keeper.list()[2])
    }
}