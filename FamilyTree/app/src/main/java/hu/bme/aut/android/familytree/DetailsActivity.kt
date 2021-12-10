package hu.bme.aut.android.familytree

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import java.text.SimpleDateFormat
import hu.bme.aut.android.familytree.databinding.ActivityDetailsBinding
import java.lang.reflect.Array.set
import java.util.*


class DetailsActivity : AppCompatActivity() {

    val formatDate = SimpleDateFormat("dd-MM-yyyy")
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val actionBar = supportActionBar

        actionBar!!.title = "Details"

        actionBar.setDisplayHomeAsUpEnabled(true)


        binding.textName.setText(actualMember.nev)
        binding.story.setText(actualMember.story)
        binding.textDate.setText(actualMember.birth)

        binding.button.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datepicker = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                    val selectDate = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR, i)
                    selectDate.set(Calendar.MONTH, i2)
                    selectDate.set(Calendar.DAY_OF_MONTH, i3)
                    val date = formatDate.format(selectDate.time)
                    Toast.makeText(this, "Date : " + date, Toast.LENGTH_SHORT).show()
                    binding.textDate.setText(date)
                    actualMember.birth = date

                },
                getDate.get(Calendar.YEAR),
                getDate.get(Calendar.MONTH),
                getDate.get(Calendar.DAY_OF_MONTH)
            )
            datepicker.show()
        }

        binding.buttonsave.setOnClickListener {
            var newname = binding.textName.text.toString()
            var story = binding.story.text.toString()

            actualMember.nev = newname
            actualMember.story = story

            binding.textName.setText(actualMember.nev)
            binding.story.setText(actualMember.story)

        }

    }
}
