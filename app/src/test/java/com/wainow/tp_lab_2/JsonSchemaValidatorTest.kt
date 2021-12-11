package com.wainow.tp_lab_2

import com.google.gson.Gson
import com.wainow.tp_lab_2.data.utils.UserObjectSchema
import com.wainow.tp_lab_2.domain.Game
import com.wainow.tp_lab_2.domain.User
import com.wainow.tp_lab_2.domain.UserInteractor
import net.pwall.json.schema.JSONSchema
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JsonSchemaValidatorTest {
    @Test
    fun jsonValidator_correctJson() {
        val game = Game(
            UserInteractor().createUser(),
            UserInteractor().createUser()
        )
        val schema = JSONSchema.parse(UserObjectSchema.get())
        val json = Gson().toJson(game)
        val output = schema.validateBasic(json)
        output.errors?.forEach {
            println("${it.error} - ${it.instanceLocation}")
        }
        assertEquals(true, output.errors.isNullOrEmpty())
    }

    @Test
    fun jsonValidator_incorrectJson() {
        val schema = JSONSchema.parse(UserObjectSchema.get())
        val json = Gson().toJson(UserInteractor().createUser())
        val output = schema.validateBasic(json)
        output.errors?.forEach {
            println("${it.error} - ${it.instanceLocation}")
        }
        assertEquals(false, output.errors.isNullOrEmpty())
    }
}