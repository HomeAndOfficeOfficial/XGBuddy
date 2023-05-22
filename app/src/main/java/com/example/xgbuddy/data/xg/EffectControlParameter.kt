package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class EffectControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Byte,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    /**
     * Just writing this here because I don't know where else to put it.
     *
     * This is going to be a slightly more complicated solution than the previous parameters since
     * the different reverbs/choruses/effects can have different parameters in different locations
     * and whatnot.
     *
     * 1. Change Reverb, Chorus, and Variation to ReverbType, ChorusType, VariationType
     *
     * 2. Extend Effect to three subclasses - ReverbEffect, ChorusEffect, VariationEffect
     *
     * 3. Each of those classes have fields that relate to the parameters used in param change
     *  messages.
     *
     * 4. Use these classes in MidiViewModel for reverb, chorus, variation.
     *
     * I'm still not sure how I'll handle updating the control views. First, just get the data
     * classes sorted out.
     */
}