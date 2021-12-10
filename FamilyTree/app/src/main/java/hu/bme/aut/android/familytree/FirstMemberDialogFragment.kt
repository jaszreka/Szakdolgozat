package hu.bme.aut.android.familytree

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.familytree.databinding.DialogFirstMemberBinding
import java.util.*

class FirstMemberDialogFragment : DialogFragment() {
    private  lateinit var binding: DialogFirstMemberBinding
    private lateinit var rg: RadioGroup
    private var isSelected: Boolean = false
    private var gender: Boolean? = null

    interface FirstMemberDialogListener {
        fun onFamilyMemberCreated(newItem: FamilyMember)
    }

    private lateinit var listener: FirstMemberDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FirstMemberDialogListener
            ?: throw RuntimeException("Activity must implement the NewFamilyMemberDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFirstMemberBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.first_family_member)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid() && isSelected()) {
                    listener.onFamilyMemberCreated(getFamilyMember())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    companion object {
        const val TAG = "FirstMemberDialogFragment"
    }

    private fun getContentView(): View {
        val contentView = binding.root
        onclick(contentView)

        return contentView

    }
    private fun onclick(v: View){
        rg = binding.radiogroup
        rg.setOnCheckedChangeListener { rg, i ->
            var rb: RadioButton = v.findViewById<RadioButton>(i)
            if (rb != null)
                isSelected = true
                if (rb.text == "male") {
                    gender = true
                }
                if (rb.text == "female")
                    gender = false
        }
    }

    private fun isValid() = binding.FamilyMemberNameEditText.text.isNotEmpty()
    private fun isSelected() = isSelected

    private fun getFamilyMember(): FamilyMember {
        var newMember = FamilyMember(binding.FamilyMemberNameEditText.text.toString())
        newMember.sId = UUID.randomUUID().toString()
        newMember.isBoy = gender
        return newMember
    }



}