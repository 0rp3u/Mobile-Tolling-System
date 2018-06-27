package pt.isel.ps.g30.tollingsystem

import org.junit.Test

import org.junit.Assert.*
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val date = Date()
        val formated = date.dateTimeParsed()



        assertEquals(Date(date.time).dateTimeParsed(), formated)

        assertEquals(4, 2 + 2)
    }


}
