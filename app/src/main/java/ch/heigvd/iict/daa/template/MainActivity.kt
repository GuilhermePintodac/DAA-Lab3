package ch.heigvd.iict.daa.template

import android.app.DatePickerDialog
import android.app.Person
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import ch.heigvd.iict.daa.labo3.Student
import ch.heigvd.iict.daa.labo3.Worker
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        // Référence au champ de date
        val dateAnnivImageButton = findViewById<ImageButton>(R.id.image_button_cake)
        val dateAnnivEditText = findViewById<EditText>(R.id.DateAnniv)

        // Configurer le clic pour ouvrir le DatePickerDialog
        dateAnnivImageButton.setOnClickListener {
            showDatePickerDialog(dateAnnivEditText)
        }



        // Spinner
        nationalitySpinner()
        SecteurSpinner()

        // Listener
        buttonListener()

    }

    private fun buttonListener(){
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Bouton clear
        findViewById<Button>(R.id.annuler).setOnClickListener{
            for (i in 0 until mainLayout.childCount) {
                val view = mainLayout.getChildAt(i)

                when (view) {
                    is EditText -> view.text.clear()
                    is Spinner -> view.setSelection(0)
                    is RadioGroup -> view.clearCheck()
                }
            }
            setGroupVisibility(false,false)
        }

        // Bouton Ok
        val oKButton = findViewById<Button>(R.id.ok)
        oKButton.setOnClickListener {
            if ( radioGroup.checkedRadioButtonId == R.id.etudiant) {
                //Student();
                val exampleStudent = Student(
                    "Dreher",
                    "Matthias",
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, 1998)
                        set(Calendar.MONTH, Calendar.APRIL)
                        set(Calendar.DAY_OF_MONTH, 8)
                    },
                    "Allemande",
                    "HEIG-VD",
                    2023,
                    "m.dreher@email.com",
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
                )
            }else if ( radioGroup.checkedRadioButtonId == R.id.employe) {

            }else{

            }
        }


        // Radio bouton pour selection etudiant ou employe
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.etudiant -> {
                    setGroupVisibility(true,false)
                }
                R.id.employe -> {
                    setGroupVisibility(false,true)
                }
            }
        }



    }

    private fun setGroupVisibility(student:Boolean,worker: Boolean){
     findViewById<Group>(R.id.groupEtudiant).visibility = if(student) VISIBLE else GONE
     findViewById<Group>(R.id.groupEntreprise).visibility = if(worker) VISIBLE else GONE

    }

    private fun showDatePickerDialog(editText: EditText) {
        // Obtenir la date actuelle
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Créer et afficher le DatePickerDialog
        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Mettre à jour le champ de date avec la date sélectionnée
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(selectedDate)
            }, year, month, day
        )

        datePickerDialog.show()
    }


    private fun nationalitySpinner(){
        val spinner = findViewById<Spinner>(R.id.element)

        val nationalities = resources.getStringArray(R.array.nationalities).toMutableList()
        nationalities.add(0, "Sélectionner")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nationalities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    // Si l'utilisateur sélectionne "Sélectionner", on ignore
                    (view as TextView).setTextColor(Color.GRAY) // Affiche en gris pour donner un effet de "placeholder"
                } else {
                    // Actions à réaliser lorsque l'utilisateur choisit une option valide
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Rien à faire ici
            }
        }
    }

    private fun SecteurSpinner(){
        val spinner = findViewById<Spinner>(R.id.secteur)

        val nationalities = resources.getStringArray(R.array.sectors).toMutableList()
        nationalities.add(0, "Sélectionner")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nationalities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    // Si l'utilisateur sélectionne "Sélectionner", on ignore
                    (view as TextView).setTextColor(Color.GRAY) // Affiche en gris pour donner un effet de "placeholder"
                } else {
                    // Actions à réaliser lorsque l'utilisateur choisit une option valide
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Rien à faire ici
            }
        }
    }
}