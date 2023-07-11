package com.yamahaw.xgbuddy.data

import kotlin.reflect.KMutableProperty

open class MidiData {

    fun setProperty(property: KMutableProperty<Byte>?, value: Byte) {
        property?.setter!!.call(this, value)
    }

    fun setProperty(property: KMutableProperty<Int>?, value: Int) {
        property?.setter!!.call(this, value)
    }

    fun getPropertyValue(property: KMutableProperty<Byte>?): Byte =
        property?.getter!!.call(this)

    fun getPropertyValue(property: KMutableProperty<Int>?): Int =
        property?.getter!!.call(this)


}