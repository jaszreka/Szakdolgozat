package hu.bme.aut.android.familytree

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.familytree.databinding.DialogNewFamilyMemberBinding
import java.util.*

class NewFamilyMemberDialogFragment : DialogFragment() {
    private  lateinit var binding: DialogNewFamilyMemberBinding
    private lateinit var categorySpinner: Spinner


    interface NewFamilyMemberDialogListener {
        fun onFamilyMemberCreated(newItem: FamilyMember)
    }

    private lateinit var listener: NewFamilyMemberDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewFamilyMemberDialogListener
            ?: throw RuntimeException("Activity must implement the NewFamilyMemberDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewFamilyMemberBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_family_member)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid()) {
                    listener.onFamilyMemberCreated(getFamilyMember())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewFamilyMemberDialogFragment"
    }

    private fun getContentView(): View {
        val contentView = binding.root

        spinner(contentView)

        return contentView
    }

    private fun spinner(view: View){
        categorySpinner = binding.FamilyMemberCategorySpinner
        categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )
    }

    private fun isValid() = binding.FamilyMemberNameEditText.text.isNotEmpty()


    private fun getFamilyMember(): FamilyMember {
        var newMember = FamilyMember(binding.FamilyMemberNameEditText.text.toString())
        newMember.sId = UUID.randomUUID().toString()
        var category = categorySpinner.selectedItemPosition
        when(category){
            0 -> {
                if(actualMember.mother == null){

                actualMember.father?.wife = newMember
                newMember.husband = actualMember.father

                for(sister in actualMember.sister){
                    sister.mother = newMember
                }
                for(sister in actualMember.sister) {
                    newMember.daughter.add(sister)
                }

                for(brother in actualMember.brother){
                    brother.mother = newMember
                }
                for(brother in actualMember.brother) {
                    newMember.son.add(brother)
                }

                actualMember.mother = newMember

                if(actualMember.isBoy == true){
                    newMember.son.add(actualMember)
                }
                else
                    newMember.daughter.add(actualMember)

                newMember.isBoy = false
            }
            }
            1 -> {
                if(actualMember.father == null){

                actualMember.mother?.husband = newMember
                newMember.wife = actualMember.mother

                for(sister in actualMember.sister){
                    sister.father = newMember
                }
                for(sister in actualMember.sister) {
                    newMember.daughter.add(sister)
                }

                for(brother in actualMember.brother){
                    brother.father = newMember
                }
                for(brother in actualMember.brother) {
                    newMember.son.add(brother)
                }

                actualMember.father = newMember

                if(actualMember.isBoy == true){
                    newMember.son.add(actualMember)
                }
                else  {
                    newMember.daughter.add(actualMember)
                }

                newMember.isBoy = true
            }}
            2 -> {

                newMember.mother = actualMember.mother           //új anyukája legyen am anyukája
                actualMember.mother?.daughter?.add(newMember)    //am anyukájának a lánya legyen új

                newMember.father = actualMember.father           //új apukája legyen am apukája
                actualMember.father?.daughter?.add(newMember)    //am apukájának a lánya legyen új

                for(sis in actualMember.sister){              //am nővéreinek új is legyen nővére
                    sis.sister.add(newMember)
                }
                for(bro in actualMember.brother){            //am bátyjainak új is legyen bátyja
                    bro.sister.add(newMember)
                }

                for(bro in actualMember.brother) {           //am bátyjai, újnak is legyenek bátyjai
                    newMember.brother.add(bro)
                }

                for(sis in actualMember.sister) {             //am nővérei, újnak is legyenek nővérei
                    newMember.sister.add(sis)
                }

                actualMember.sister.add(newMember)            //am tetsvére legyen új hátra kell rakni különben kétszer lesz

                if (actualMember.isBoy == true){
                    newMember.brother.add(actualMember)          //ha am fiú, új bátyja legyen
                }
                else
                    newMember.sister.add(actualMember)          //ha am lány, új nővére legyen

                newMember.isBoy = false                         //új legyen lány

            }
            3 -> {

                newMember.mother = actualMember.mother
                actualMember.mother?.son?.add(newMember)

                newMember.father = actualMember.father
                actualMember.father?.son?.add(newMember)

                for(sis in actualMember.sister){
                    sis.brother.add(newMember)
                }

                for(bro in actualMember.brother){
                    bro.brother.add(newMember)
                }


                for(bro in actualMember.brother) {
                    newMember.brother.add(bro)
                }

                for(sis in actualMember.sister) {
                    newMember.sister.add(sis)
                }

                actualMember.brother.add(newMember)

                if (actualMember.isBoy == true){
                    newMember.brother.add(actualMember)
                }
                else
                    newMember.sister.add(actualMember)

                newMember.isBoy = true
            }
            4 -> { if(actualMember.wife == null){

                actualMember.wife = newMember

                newMember.husband = actualMember

                for(dau in actualMember.daughter){
                    dau.mother = newMember
                }

                for(so in actualMember.son){
                    so.mother = newMember
                }

                for(dau in actualMember.daughter) {
                    newMember.daughter.add(dau)
                }

                for(so in actualMember.son) {
                    newMember.son.add(so)
                }

                newMember.isBoy = false
            }}
            5 ->{ if(actualMember.husband == null){

                actualMember.husband = newMember
                newMember.wife = actualMember

                for(dau in actualMember.daughter){
                    dau.father = newMember
                }

                for(so in actualMember.son){
                    so.father = newMember
                }

                for(dau in actualMember.daughter) {
                    newMember.daughter.add(dau)
                }

                for(so in actualMember.son) {
                    newMember.son.add(so)
                }

                newMember.isBoy = true
            }}
            6 -> {

                for(dau in actualMember.daughter){
                    dau.sister.add(newMember)
                }

                for(so in actualMember.son){
                    so.sister.add(newMember)
                }

                for(dau in actualMember.daughter){
                    newMember.sister.add(dau)
                }

                for(so in actualMember.son){
                    newMember.brother.add(so)
                }

                actualMember.daughter.add(newMember)

                if (actualMember.isBoy == true){
                    actualMember.wife?.daughter?.add(newMember)

                    newMember.father = actualMember
                    newMember.mother = actualMember.wife
                }
                else {
                    actualMember.husband?.daughter?.add(newMember)

                    newMember.mother = actualMember
                    newMember.father = actualMember.husband
                }

                newMember.isBoy = false
            }
            7 -> {

                for(dau in actualMember.daughter){
                    dau.brother.add(newMember)
                }

                for(so in actualMember.son){
                    so.brother.add(newMember)
                }

                for(dau in actualMember.daughter){
                    newMember.sister.add(dau)
                }

                for(so in actualMember.son){
                    newMember.brother.add(so)
                }

                actualMember.son.add(newMember)

                if (actualMember.isBoy == true){
                    actualMember.wife?.son?.add(newMember)

                    newMember.father = actualMember
                    newMember.mother = actualMember.wife
                }
                else {
                    actualMember.husband?.son?.add(newMember)

                    newMember.mother = actualMember
                    newMember.father = actualMember.husband
                }

                newMember.isBoy = true
            }
        }

        return newMember
    }




}