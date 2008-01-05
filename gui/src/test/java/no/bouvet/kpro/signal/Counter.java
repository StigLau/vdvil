package no.bouvet.kpro.signal;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.QtBlockedSlot;

class Counter extends QSignalEmitter {
    int value;

    public Signal1<Integer> valueChanged = new QSignalEmitter.Signal1<Integer>();

    @QtBlockedSlot
    public int value() {
        return value;
    }

    public void setValue(int val) {
        if (value != val) {
            value = val;
            valueChanged.emit(value);
        }
    }

    public Counter() {
        value = 0;
    }

    public Integer getValue() {
        return value;
    }
}