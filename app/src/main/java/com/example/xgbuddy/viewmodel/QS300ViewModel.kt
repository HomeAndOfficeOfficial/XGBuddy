package com.example.xgbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.QS300Preset


class QS300ViewModel() : BaseObservable {
    val preset = MutableLiveData<QS300Preset>(null)

    /**
     * I guess before I do any real work on this, I'm going to have to figure out what the ui is
     * going to look like.
     *
     * But I think similar to how I parse the preset json, I'm going to have to find a solution that
     * utilizes reflection. Otherwise every viewmodel will have a very long and very complex set of
     * methods for setting things.
     *
     * From a high level perspective, I know that this class will likely have a preset and keep track
     * of what voice is displayed, and probably some of the universal things like volume (then again
     * that stuff may be better off in a separate section)
     *
     * The layout will probably use two way data binding. Each control will probably be bound to a
     * function like "setProperty" or something.
     *
     * I think the way to proceed on this would be just have a single control at first, something
     * easy like volume. Get a reusable solution working for that. The view will need to update when
     * receiving data from an external controller/message, it will need to send data when responding
     * to a user interaction, and it will need to change the displayed value depending on that voice
     * that is being displayed on screen, and also change when the current preset is changed.
     *
     * Honestly not sure if all that can be done in a neat two way binding solution but I will try.
     */


}