package hu.bme.aut.android.familytree

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.familytree.databinding.ActivityFamilyTreeBinding
import java.util.*
lateinit var actualMember : FamilyMember
class FamilyTreeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, NewFamilyMemberDialogFragment.NewFamilyMemberDialogListener, FirstMemberDialogFragment.FirstMemberDialogListener {
    private var family = FamilyTree()
    private var familyID = FamilyTreeID()

    private lateinit var binding: ActivityFamilyTreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamilyTreeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.appBarFamilyTree.fab.setOnClickListener { view ->
            if (family.members.isEmpty() == false) {

                    NewFamilyMemberDialogFragment().show(
                        supportFragmentManager,
                        NewFamilyMemberDialogFragment.TAG
                    )
                }
            else {
                    FirstMemberDialogFragment().show(
                        supportFragmentManager,
                        FirstMemberDialogFragment.TAG
                    )
                }
        }

        binding.navView.setNavigationItemSelectedListener(this)


        binding.appBarFamilyTree.contentFamilyTree.buttonMother.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.mother == null) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.mother!!
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonFather.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.father == null) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.father!!
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonSister.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.sister.isEmpty()) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.sister.random()
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonBrother.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.brother.isEmpty()) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.brother.random()
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonDaughter.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.daughter.isEmpty()) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.daughter.random()
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonSon.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    if (actualMember.son.isEmpty()) {
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    } else {
                        actualMember = actualMember.son.random()
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonWife.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
            if(actualMember.wife==null) {
                binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
            }
            else{
                actualMember = actualMember.wife!!
                binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
            }
            }}
        }

        binding.appBarFamilyTree.contentFamilyTree.buttonHusband.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
            if(actualMember.husband==null) {
                binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
            }
            else{
                actualMember = actualMember.husband!!
                binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
            }
                }}
        }


        binding.appBarFamilyTree.contentFamilyTree.buttonDetails.setOnClickListener {
           when {
               (family.members.isEmpty() == false) -> {
                   val details = Intent(this, DetailsActivity::class.java)
                   startActivity(details)
           }}
            if(family.members.isEmpty() == false){
                binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                for(member in familyID.members){
                    if(member.id == actualMember.sId) {
                        member.nev = actualMember.nev
                        member.story = actualMember.story
                        member.birth = actualMember.birth
                    }
                }
            }
        }

        binding.appBarFamilyTree.contentFamilyTree.delete.setOnClickListener {
            when {
                (family.members.isEmpty() == false) -> {
                    var i = family.members.indexOf(actualMember)

                    for (memberid in familyID.members) {
                        if (memberid.mother == family.members[i].sId) {
                            memberid.mother = null
                        }
                        if (memberid.father == family.members[i].sId) {
                            memberid.father = null
                        }
                        if (memberid.husband == family.members[i].sId) {
                            memberid.husband = null
                        }
                        if (memberid.wife == family.members[i].sId) {
                            memberid.wife = null
                        }
                        for (sister in memberid.sister) {
                            if (sister == family.members[i].sId) {
                                memberid.sister.remove(sister)
                            }
                        }
                        for (brother in memberid.brother) {
                            if (brother == family.members[i].sId) {
                                memberid.brother.remove(brother)
                            }
                        }
                        for (daughter in memberid.daughter) {
                            if (daughter == family.members[i].sId) {
                                memberid.daughter.remove(daughter)
                            }
                        }
                        for (son in memberid.son) {
                            if (son == family.members[i].sId) {
                                memberid.son.remove(son)
                            }
                        }
                    }

                    var idx: Int = 0
                    for(memberid in  familyID.members) {
                        if (memberid.id == family.members[i].sId) {
                            deleteMember(memberid)
                            idx = familyID.members.indexOf(memberid)
                        }
                    }

                    deleteNull()

                    if(familyID.members.size > 2){
                        familyID.members.removeAt(idx)
                        var duplidx: Int = 0
                        for(m in familyID.members){
                            if(m.id == family.members[i].sId)
                                duplidx = familyID.members.indexOf(m)
                        }
                        familyID.members.removeAt(duplidx)
                    }
                    else{
                        familyID.members.clear()
                    }

                    for (member in family.members) {
                        if (member.mother == family.members[i]) {
                            member.mother = null
                        }
                        if (member.father == family.members[i]) {
                            member.father = null
                        }
                        if (member.husband == family.members[i]) {
                            member.husband = null
                        }
                        if (member.wife == family.members[i]) {
                            member.wife = null
                        }
                        for (sister in member.sister) {
                            if (sister == family.members[i]) {
                                member.sister.remove(sister)
                            }
                        }
                        for (brother in member.brother) {
                            if (brother == family.members[i]) {
                                member.brother.remove(brother)
                            }
                        }
                        for (daughter in member.daughter) {
                            if (daughter == family.members[i]) {
                                member.daughter.remove(daughter)
                            }
                        }
                        for (son in member.son) {
                            if (son == family.members[i]) {
                                member.son.remove(son)
                            }
                        }
                    }

                    if (family.members.size > 1) {
                        family.members.removeAt(i)
                        actualMember = family.members[0]
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)

                    } else {
                        var üres = FamilyMember("üres")
                        actualMember = üres
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(getString(R.string.create_the_first_family_member))
                        family.members.clear()
                    }
                }
            }
        }


        initFamilyListener()

    }

    fun initFamilyListener() {
        val db = Firebase.firestore
        val userid: String = uid.toString()
        db.collection("users").document(userid)
            .collection("members")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> newFamilyMemberID(dc.document.toObject<FamilyMemberID>().withId(dc.document.getId()))
                    }
                }

                if (family.members.isEmpty()) {
                    createAppFamily()
                    initMembers()
                    if (family.members.isEmpty() == false) {
                        actualMember = family.members[0]
                        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)
                    }
                }
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                for(member in familyID.members){
                    updateMember(member)
                }
                deleteNull()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createAppFamily(){
        for(fbmember in familyID.members){
            var nev = fbmember.nev
            var appmember = FamilyMember(nev.toString())
            appmember.sId = fbmember.id
            appmember.isBoy = fbmember.isBoy
            appmember.story = fbmember.story
            family.members.add(appmember)
        }
    }

    private fun initMembers(){
        for(i in family.members.indices){
            for(appmember in family.members){
                if(appmember.sId == familyID.members[i].husband){
                    family.members[i].husband = appmember
                }
                if(appmember.sId == familyID.members[i].wife){
                    family.members[i].wife = appmember
                }
                if(appmember.sId == familyID.members[i].mother){
                    family.members[i].mother = appmember
                }
                if(appmember.sId == familyID.members[i].father){
                    family.members[i].father = appmember
                }
                for(son in familyID.members[i].son){
                    if(appmember.sId == son){
                        family.members[i].son.add(appmember)
                    }
                }
                for(daughter in familyID.members[i].daughter){
                    if(appmember.sId == daughter){
                        family.members[i].daughter.add(appmember)
                    }
                }
                for(brother in familyID.members[i].brother){
                    if(appmember.sId == brother){
                        family.members[i].brother.add(appmember)
                    }
                }
                for(sister in familyID.members[i].sister){
                    if(appmember.sId == sister){
                        family.members[i].sister.add(appmember)
                    }
                }
            }
        }
    }

    override fun onFamilyMemberCreated(newItem: FamilyMember) {
        if(newItem.isBoy != null){
        family.members.add(newItem)
        actualMember = newItem
        binding.appBarFamilyTree.contentFamilyTree.tvData.setText(actualMember.nev)

        convertAppMemberToFb(newItem)
        }
        else{
            toast("Already exists")
        }
    }

    private fun convertAppMemberToFb(newAppMember: FamilyMember){
        var convertedMember = FamilyMemberID(newAppMember.nev)
        convertedMember.id = newAppMember.sId
        convertedMember.isBoy = newAppMember.isBoy
        convertedMember.story = newAppMember.story

        convertedMember.mother = newAppMember.mother?.sId
        for (member in familyID.members){
            if(member.id == newAppMember.mother?.sId)
                if(convertedMember.isBoy == true) {
                    member.son.add(convertedMember.id.toString())
                }
                else {
                    member.daughter.add(convertedMember.id.toString())
                }
        }

        convertedMember.father = newAppMember.father?.sId
        for (member in familyID.members){
            if(member.id == newAppMember.father?.sId)
                if(convertedMember.isBoy == true) {
                    member.son.add(convertedMember.id.toString())
                }
                else {
                    member.daughter.add(convertedMember.id.toString())
                }
        }

        convertedMember.wife = newAppMember.wife?.sId
        for (member in familyID.members){
            if(member.id == newAppMember.wife?.sId)
                member.husband = convertedMember.id

        }

        convertedMember.husband = newAppMember.husband?.sId
        for (member in familyID.members){
            if(member.id == newAppMember.husband?.sId)
                member.wife = convertedMember.id

        }

        for(sister in newAppMember.sister) {
            convertedMember.sister.add(sister.sId.toString())

            for (member in familyID.members){
                if(member.id == sister.sId)
                    if(convertedMember.isBoy == true) {
                        member.brother.add(convertedMember.id.toString())
                    }
                    else {
                        member.sister.add(convertedMember.id.toString())
                    }
            }
        }

        for(brother in newAppMember.brother) {
            convertedMember.brother.add(brother.sId.toString())

            for (member in familyID.members){
                if(member.id == brother.sId)
                    if(convertedMember.isBoy == true) {
                        member.brother.add(convertedMember.id.toString())
                    }
                    else {
                        member.sister.add(convertedMember.id.toString())
                    }
            }
        }

        for(daughter in newAppMember.daughter) {
            convertedMember.daughter.add(daughter.sId.toString())

            for (member in familyID.members){
                if(member.id == daughter.sId)
                    if(convertedMember.isBoy == true) {
                        member.father = convertedMember.id
                    }
                    else {
                        member.mother = convertedMember.id
                    }
            }
        }

        for(son in newAppMember.son) {
            convertedMember.son.add(son.sId.toString())

            for (member in familyID.members){
                if(member.id == son.sId)
                    if(convertedMember.isBoy == true) {
                        member.father = convertedMember.id
                    }
                    else {
                        member.mother = convertedMember.id
                    }
            }
        }

        newFamilyMemberID(convertedMember)
        saveMember(convertedMember)

    }

    private fun newFamilyMemberID(nmember: FamilyMemberID?) {
        nmember ?: return
        familyID.members += nmember
    }

    private fun saveMember(person: FamilyMemberID) {

        val db = Firebase.firestore
        val userid: String = uid.toString()

        db.collection("users").document(userid)
            .collection("members")
            .add(person)
            .addOnSuccessListener {
                toast("Member saved")
                 }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun updateMember(person: FamilyMemberID) {
        val db = Firebase.firestore
        val userid: String = uid.toString()

        db.collection("users").document(userid)
            .collection("members").document(person.docId.toString())
            .set(person)
            .addOnSuccessListener {
                toast("Member updated")
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun deleteMember(person: FamilyMemberID) {
        val db = Firebase.firestore
        val userid: String = uid.toString()

        db.collection("users").document(userid)
            .collection("members").document(person.docId.toString())
            .delete()
            .addOnSuccessListener {
                toast("Member deleted")
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun deleteNull() {
        val db = Firebase.firestore
        val userid: String = uid.toString()

        db.collection("users").document(userid)
            .collection("members").document("null")
            .delete()
    }



}