/*

Auteurs : Alexandre Shyshmarov, Guilherme Pinto
Ce code permet la gestion du formulaire en activity_main.xml
 */

package ch.heigvd.iict.daa.template

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import ch.heigvd.iict.daa.labo3.Person
import ch.heigvd.iict.daa.labo3.Student
import ch.heigvd.iict.daa.labo3.Worker
import java.text.DateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // Variable pour stocker la date sélectionnée par l'utilisateur
    private var selectedDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Test pour le remplissage automatique des champs Student et Worker ou null
//        val examplePerson: Person? = Person.exampleWorker
//        val examplePerson: Person? = Person.exampleStudent
        val examplePerson: Person? = null;


        // Initialiser la vue avec les données en fonction du type d'exemple sélectionné
        if (examplePerson != null) {
            when (examplePerson) {
                is Worker -> initializeViewWithWorker(examplePerson)
                is Student -> initializeViewWithStudent(examplePerson)
            }
        }



        // Référence au champ de date
        val dateAnnivImageButton = findViewById<ImageButton>(R.id.image_button_cake)
        val dateAnnivEditText = findViewById<EditText>(R.id.DateAnniv)

        // Configurer le clic pour ouvrir le DatePickerDialog
        dateAnnivImageButton.setOnClickListener {
            showDatePickerDialog(dateAnnivEditText)
        }


        // Spinner
        // Configurer les spinners
        setupSpinner(R.id.element, R.array.nationalities, "Sélectionner")
        setupSpinner(R.id.secteur, R.array.sectors, "Sélectionner")

        // Listener
        buttonListener()

    }

    private fun initializeViewWithWorker(worker: Worker) {
        findViewById<EditText>(R.id.nom).setText(worker.name)
        findViewById<EditText>(R.id.prenom).setText(worker.firstName)
        findViewById<EditText>(R.id.email).setText(worker.email)
        findViewById<EditText>(R.id.commentaires).setText(worker.remark)
        findViewById<EditText>(R.id.entreprise).setText(worker.company)
        findViewById<EditText>(R.id.experiance).setText(worker.experienceYear.toString())

        // Affiche la date de naissance formatée
        val dateAnnivEditText = findViewById<EditText>(R.id.DateAnniv)
        worker.birthDay?.let { birthDay ->
            selectedDate = birthDay  // Initialiser selectedDate avec la date de naissance du Worker
            val format = DateFormat.getDateInstance()
            dateAnnivEditText.setText(format.format(birthDay.time))  // Mettre à jour le champ de texte avec la date formatée
        }

        // Configurer les spinners pour Nationalité et Secteur
        setSpinnerSelection(R.id.element, worker.nationality)
        setSpinnerSelection(R.id.secteur, worker.sector)

        // Sélectionner le bon radio button pour "Employé"
        findViewById<RadioGroup>(R.id.radioGroup).check(R.id.employe)
        setGroupVisibility(student = false, worker = true)
    }

    private fun initializeViewWithStudent(student: Student) {
        findViewById<EditText>(R.id.nom).setText(student.name)
        findViewById<EditText>(R.id.prenom).setText(student.firstName)
        findViewById<EditText>(R.id.email).setText(student.email)
        findViewById<EditText>(R.id.commentaires).setText(student.remark)
        findViewById<EditText>(R.id.ecoleUniversite).setText(student.university)
        findViewById<EditText>(R.id.anneeDiplome).setText(student.graduationYear.toString())

        // Affiche la date de naissance formatée
        val dateAnnivEditText = findViewById<EditText>(R.id.DateAnniv)
        student.birthDay?.let { birthDay ->
            selectedDate = birthDay  // Initialiser selectedDate avec la date de naissance du Student
            val format = DateFormat.getDateInstance()
            dateAnnivEditText.setText(format.format(birthDay.time))  // Mettre à jour le champ de texte avec la date formatée
        }

        // Configurer le spinner pour Nationalité
        setSpinnerSelection(R.id.element, student.nationality)

        // Sélectionner le bon radio button pour "Étudiant"
        findViewById<RadioGroup>(R.id.radioGroup).check(R.id.etudiant)
        setGroupVisibility(student = true, worker = false)
    }

    private fun setSpinnerSelection(spinnerId: Int, value: String) {
        val spinner = findViewById<Spinner>(spinnerId)
        val adapter = spinner.adapter as ArrayAdapter<String>
        val position = adapter.getPosition(value)
        spinner.setSelection(position)
    }


    private fun buttonListener() {
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Bouton clear
        findViewById<Button>(R.id.annuler).setOnClickListener {
            for (i in 0 until mainLayout.childCount) {
                val view = mainLayout.getChildAt(i)

                when (view) {
                    is EditText -> view.text.clear()
                    is Spinner -> view.setSelection(0)
                    is RadioGroup -> view.clearCheck()
                }
            }
            setGroupVisibility(false, false)
            selectedDate = null
        }

        // Bouton Ok
        findViewById<Button>(R.id.ok).setOnClickListener {
            // Récupérer les valeurs du formulaire
            val name = findViewById<EditText>(R.id.nom).text.toString()
            val firstName = findViewById<EditText>(R.id.prenom).text.toString()
            val nationality = findViewById<Spinner>(R.id.element).selectedItem.toString()
            val email = findViewById<EditText>(R.id.email).text.toString()
            val remark = findViewById<EditText>(R.id.commentaires).text.toString()

            // Utiliser la date sélectionnée ou la date actuelle
            val birthDay = selectedDate ?: Calendar.getInstance()

            if (radioGroup.checkedRadioButtonId == R.id.etudiant) {
                val university = findViewById<EditText>(R.id.ecoleUniversite).text.toString()
                val graduationYear =
                    findViewById<EditText>(R.id.anneeDiplome).text.toString().toIntOrNull() ?: Calendar.getInstance().get(Calendar.YEAR)

                // Créer une instance de Student
                val student = Student(name, firstName, birthDay, nationality, university, graduationYear, email, remark)

                // Écrire les informations dans le log
                Log.d("StudentInfo", "Student instance: $student")


            } else if (radioGroup.checkedRadioButtonId == R.id.employe) {
                val company = findViewById<EditText>(R.id.entreprise).text.toString()
                val sector = findViewById<Spinner>(R.id.secteur).selectedItem.toString()
                val experienceYear =
                    findViewById<EditText>(R.id.experiance).text.toString().toIntOrNull() ?: 0

                // Créer une instance de Worker
                val worker = Worker(name, firstName, birthDay, nationality, company, sector, experienceYear, email, remark)

                // Écrire les informations dans le log
                Log.d("WorkerInfo", "Worker instance: $worker")

            }

        }


            // Radio bouton pour selection etudiant ou employe
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.etudiant -> {
                        setGroupVisibility(true, false)
                    }

                    R.id.employe -> {
                        setGroupVisibility(false, true)
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
        val calendar = selectedDate ?: Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Créer et afficher le DatePickerDialog
        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Mettre à jour la date sélectionnée
                selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }

                // Utiliser DateFormat pour formater la date localisée
                val format = DateFormat.getDateInstance();
                val formattedDate = selectedDate?.time?.let { format.format(it) }

                // Mettre à jour le champ de date avec la date sélectionnée
                editText.setText(formattedDate)

            }, year, month, day
        )

        datePickerDialog.show()
    }

    private fun setupSpinner(spinnerId: Int, arrayId: Int, defaultText: String) {
        val spinner = findViewById<Spinner>(spinnerId)

        // Récupérer les valeurs de la ressource et ajouter l'élément par défaut
        val items = resources.getStringArray(arrayId).toMutableList()
        items.add(0, defaultText)

        // Créer l'adaptateur
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Définir l'écouteur de sélection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    // Si l'utilisateur sélectionne "Sélectionner", on ignore
                    (view as TextView).setTextColor(Color.GRAY) // Affiche en gris pour donner un effet de "placeholder"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Rien à faire ici
            }
        }
    }
}