package com.example.xgbuddy.data

import kotlin.reflect.KMutableProperty

open class MidiData {
    fun setProperty(property: KMutableProperty<Byte>?, value: Byte) {
        property?.setter!!.call(this, value)
    }

    fun getPropertyValue(property: KMutableProperty<Byte>?): Byte =
        property?.getter!!.call(this)
}