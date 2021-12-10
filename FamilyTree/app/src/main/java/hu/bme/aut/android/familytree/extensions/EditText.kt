package hu.bme.aut.android.familytree.extensions

import android.widget.EditText

fun EditText.validateNonEmpty(): Boolean {
    if (text.isEmpty()) {
        error = "Required"
        return false
    }
    return true
}
