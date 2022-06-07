package com.example.mycomposecookbook.data.model

import com.example.mycomposecookbook.MainApplication
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class Test {


    val name: String? = null
    val label: StringRes? = null

}

class StringRes {
    public var value: String? = null
}


class typedadp : TypeAdapter<StringRes>() {
    override fun write(out: JsonWriter?, value: StringRes?) {


    }

    override fun read(`in`: JsonReader?): StringRes {

        val stringres = StringRes()
        stringres.value = "abcd"+`in`!!.nextString()
        return stringres
    }

}