package hu.bme.aut.android.familytree

class FamilyMember(var nev: String) {
    var sId: String? = null
    var isBoy: Boolean? = null
    var mother: FamilyMember? = null
    var father: FamilyMember? = null
    var wife: FamilyMember? = null
    var husband: FamilyMember? = null
    var sister: MutableList<FamilyMember> = mutableListOf()
    var brother: MutableList<FamilyMember> = mutableListOf()
    var daughter: MutableList<FamilyMember> = mutableListOf()
    var son: MutableList<FamilyMember> = mutableListOf()
    var birth: String? = null
    var story: String? = null

        set(value) {
            field = value
        }

}