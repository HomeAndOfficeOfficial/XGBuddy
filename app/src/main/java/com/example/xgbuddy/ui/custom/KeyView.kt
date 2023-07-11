package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.xgbuddy.R

class KeyView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attributeSet) {

    private val note: String // TODO: Expand this to a Note data class probably

    private var isKeyOn = false
        set(value) {
            field = value
            setKeyBackground()
        }

    var listener: OnKeyPressListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyView, 0, 0)
        note = typedArray.getString(R.styleable.KeyView_note) ?: "c"
        setKeyBackground()
        typedArray.recycle()
    }

    private fun setKeyBackground() {
        if (note.length == 1) {
            if (isKeyOn) {
                setImageResource(R.drawable.kdb_white_bg_press)
            } else {
                setImageResource(R.drawable.kbd_white_bg)
            }
        } else {
            if (isKeyOn) {
                setImageResource(R.drawable.kbd_black_bg_press)
            } else {
                setImageResource(R.drawable.kbd_black_bg)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isKeyOn) {
                    isKeyOn = true
                    listener?.onKeyDown(note)
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                isKeyOn = false
                listener?.onKeyUp(note)
                return false
            }
        }
        return super.onTouchEvent(event)
    }

    interface OnKeyPressListener {
        fun onKeyDown(note: String)
        fun onKeyUp(note: String)
    }
}