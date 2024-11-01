package ch.heigvd.iict.daa.template

import android.app.DatePickerDialog
import android.app.Person
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ch.heigvd.iict.daa.labo3.Student
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Références aux éléments
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val groupEtudiant = findViewById<Group>(R.id.groupEtudiant)
        val groupEntreprise = findViewById<Group>(R.id.groupEntreprise)

        // Initialiser les groupes comme invisibles

        groupEtudiant.visibility = View.GONE
        groupEntreprise.visibility = View.GONE


        // Ajouter le listener pour détecter le changement de sélection
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.etudiant -> {
                    groupEtudiant.visibility = View.VISIBLE
                    groupEntreprise.visibility = View.GONE
                }
                R.id.employe -> {
                    groupEtudiant.visibility = View.GONE
                    groupEntreprise.visibility = View.VISIBLE
                }
            }
        }

        // Référence au champ de date
        val dateAnnivImageButton = findViewById<ImageButton>(R.id.image_button_cake)
        val dateAnnivEditText = findViewById<EditText>(R.id.DateAnniv)

        // Configurer le clic pour ouvrir le DatePickerDialog
        dateAnnivImageButton.setOnClickListener {
            showDatePickerDialog(dateAnnivEditText)
        }

        val oKButton = findViewById<Button>(R.id.ok)
        //Champ sélectioné
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        oKButton.setOnClickListener {
            if (selectedRadioButtonId == R.id.etudiant) {
                Student();
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
            }else if (selectedRadioButtonId == R.id.employe) {

            }else{

            }
        }



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
}