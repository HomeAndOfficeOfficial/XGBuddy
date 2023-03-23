package com.example.xgbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.qs300.QS300Preset
import kotlin.reflect.full.memberProperties


class QS300ViewModel : ViewModel() {

    val preset = MutableLiveData<QS300Preset>(null)
    var currentParamIndex = 0
    var voice: Int = 0
    var element: Int = 0

    fun nextParameter() {
        val params = preset.value!!.voices[0].elements[0]::class.memberProperties
        if (currentParamIndex + 1 < params.size) {

        }
    }


    /**
     * What might be required to set up the layouts programatically?
     *
     * Each element needs to fulfill three functions:
     * 1. On user interaction, send midi data
     * 2. On Midi data received, update to display new value
     * 3. On preset data changed, change displayed value
     *
     *
     * I can imagine a solution where we have a viewgroup that has several configurations - vertical,
     * horizontal, grid(rows, columns), and also accepts as a parameter the instrument mode it's in,
     * a start address and end address of the elements the controls will be mapped to.
     *
     * The viewgroup could automatically populate its views based on these parameters, and set
     * interaction listeners on each view which would map directly to the address of the element
     * that they represent
     * That should take care of the first item. For example (something like this)
     *
     * fun addViews(minAddr: Int, maxAddr: Int) {
     *      for (i in minAddr until maxAddr) {
     *          val element = QS300Element::getBaseAddress findBy i
     *          addView(SeekBar().apply {
     *              id = create some unique id based on the address that can be recreated later
     *              min = element.min
     *              max = element.max
     *              progress = element.default
     *              onChangedListener = { value, fromUser ->
     *                  if (fromUser) {
     *                      parameterChangeListener.onParameterChanged(element, value)
     *                  }
     *              }
     *          }
     *      }
     * }
     *
     * // This would live in a fragment
     * ParameterChangeListener.onParameterChanged(element: QS300Element, value: Int) {
     *      val current = preset.voice[currentVoice].element[currentElement].getPropertValue(element.reflectedField)
     *      if (current != value) {
     *      preset.voice[currentVoice].element[currentElement].setProperty(element.reflectedField, value.toByte())
     * }
     *
     * THere would likely be more logic around the type of view that would be populated, and dealing
     * with the different callbacks.
     *
     * On to number 2. How do update that particular control if we get data from an external
     * controller?
     *
     * In our midiReceived callback, check if the message is a control change or parameter change or
     * whatever. It probably has an address in the message, use that to access the element in the
     * viewgroup -> viewGroup.updateParameterView(elementAddr, value)
     *
     * Viewgroup can maintain a map of its views with unique keys based on the elementAddr, so they
     * can easily be referenced.
     *
     * That's a vague solution for number 2, but it's feasible at least.
     *
     * When changing presets, probably just need to iterate through all the elements similar to
     * how they are read in from json.
     *
     * Furthermore, we can keep this viewmodel around to keep track of the current voice, element, etc.
     *
     * Observe changes in those to update the views that are on the screen.
     *
     * THe viewgroup could have maybe have a method that takes in a preset/voice/element
     * and all the views are updated appropriately.
     *
     * I think some elements just might not fit into this pattern so I guess there will have to be
     * some level manual programming invovled. I guess as that is developed, I'll figure out what
     * needs to be done.
     */


}