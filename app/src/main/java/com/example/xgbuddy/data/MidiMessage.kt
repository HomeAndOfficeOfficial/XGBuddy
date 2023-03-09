package com.example.xgbuddy.data

data class MidiMessage(val msg: ByteArray?, val offset: Int, val count: Int, val timestamp: Long) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MidiMessage

        if (msg != null) {
            if (other.msg == null) return false
            if (!msg.contentEquals(other.msg)) return false
        } else if (other.msg != null) return false
        if (offset != other.offset) return false
        if (count != other.count) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = msg?.contentHashCode() ?: 0
        result = 31 * result + offset
        result = 31 * result + count
        result = 31 * result + timestamp.hashCode()
        return result
    }
}
