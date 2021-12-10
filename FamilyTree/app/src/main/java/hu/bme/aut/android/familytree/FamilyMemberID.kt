package hu.bme.aut.android.familytree



class FamilyMemberID (
    var nev: String? = null,
    var id: String? = null,
    var isBoy: Boolean? = null,
    var mother: String? = null,
    var father: String? = null,
    var wife: String? = null,
    var husband: String? = null,
    var sister: MutableList<String> = mutableListOf(),
    var brother: MutableList<String> = mutableListOf(),
    var daughter: MutableList<String> = mutableListOf(),
    var son: MutableList<String> = mutableListOf(),
    var birth: String? =null,
    var story: String? = null
): Model()

