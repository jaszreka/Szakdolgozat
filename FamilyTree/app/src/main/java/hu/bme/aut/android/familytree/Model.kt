package hu.bme.aut.android.familytree

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
open class Model {
    @Exclude
    var docId: String? = null
    fun <T : Model?> withId(docId: String): T {
        this.docId = docId
        return this as T
    }
}