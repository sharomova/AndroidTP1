package tp1.bdeb.qc.ca.tp1.vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesMedia;

/**
 * Created by Olga on 2015-10-01.
 */
public class ModifierAjouterUnMedia extends MainActivity {
    MediaActivity medAct = new MediaActivity();
    DbHelper db;
    DonnesMedia media;
    Button btnValider;
    EditText txtnomAmi;
    EditText txtTitre;
    EditText datePris;
    EditText datePrevu;
    EditText dateReelle;
    RatingBar ratingBar;
    RadioButton rdbFilme;
    RadioButton rdbJeu;
    RadioButton rdbAutre;
    RadioGroup radioGroup;
    String typeMedia;
    int id;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_un_media);
        db = DbHelper.getInstance(this);
        initialiser();

        Intent intent = getIntent();
        id = intent.getIntExtra(medAct.ID_MEDIA, -1);
        if (intent != null && id != -1){
           media = db.getMedia(id);
            ajouterMediaDansView();
        }
        //pour prendre le type de la média
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.modifier_media_rdbFilme) {
                    typeMedia = "Filme";
                } else if (checkedId == R.id.modifier_media_rdbJeu) {
                    typeMedia = "Jeu";
                } else if (checkedId == R.id.modifier_media_rdbAutre) {
                    typeMedia = "Autre";
                }
            }
        });
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeMedia != null) {
                    if (id == -1) { // si le nouveaux media
                        prendreDonnesMedia();
                        int idAmi = db.ajouterMedia(media);
                        if (idAmi == -1) {
                            Toast.makeText(ModifierAjouterUnMedia.this, getResources().getString(R.string.friend_isnt_existe), Toast.LENGTH_SHORT).show();
                        }
                    } else { //si modifier les donnes
                        prendreDonnesMedia();
                        boolean update = db.updateMedia(media);
                        if (!update) {
                            Toast.makeText(ModifierAjouterUnMedia.this, getResources().getString(R.string.up_date), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //revenir à la page de media
                    Intent intent = new Intent(ModifierAjouterUnMedia.this, MediaActivity.class);
                    intent.putExtra(ID_AMI, TOUT_MEDIA);
                    startActivity(intent);
                } else {
                    Toast.makeText(ModifierAjouterUnMedia.this, getResources().getString(R.string.non_type), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //prendre la date d'emprunter de média
        final DatePickerDialog.OnDateSetListener datepris = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datePris.setText(sdf.format(myCalendar.getTime()));
            }

        };

        datePris.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ModifierAjouterUnMedia.this, datepris, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //prendre la date de retour prevu de média
        final DatePickerDialog.OnDateSetListener dateprevu = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datePrevu.setText(sdf.format(myCalendar.getTime()));
            }

        };
        datePrevu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ModifierAjouterUnMedia.this, dateprevu, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //prendre la date de retour reele de média
        final DatePickerDialog.OnDateSetListener dateretour = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateReelle.setText(sdf.format(myCalendar.getTime()));
            }

        };
        dateReelle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ModifierAjouterUnMedia.this, dateretour, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    /**
     * methode qui initialise les variables
     */
    private void initialiser () {
        btnValider = (Button) findViewById(R.id.modifier_media_btnValider);
        txtnomAmi = (EditText) findViewById(R.id.modifier_media_txtNom);
        txtTitre = (EditText) findViewById(R.id.modifier_media_txtTitre);
        datePrevu = (EditText) findViewById(R.id.modifier_media_txtDatePrevu);
        datePris = (EditText) findViewById(R.id.modifier_media_txtDatePris);
        dateReelle = (EditText) findViewById(R.id.modifier_media_txtDateReelle);
        ratingBar = (RatingBar) findViewById(R.id.modifier_media_rating);
        rdbFilme =(RadioButton)findViewById(R.id.modifier_media_rdbFilme);
        rdbJeu = (RadioButton) findViewById(R.id.modifier_media_rdbJeu);
        rdbAutre = (RadioButton) findViewById(R.id.modifier_media_rdbAutre);
        radioGroup = (RadioGroup) findViewById(R.id.modifier_media_rgrpType);

    }
    /**
     * methode qui ajoute les donnes dans la View
     */
    private void ajouterMediaDansView() {
        txtnomAmi.setText(String.valueOf(media.getIdAmis()));
        txtTitre.setText(media.getNomMedia());
        datePrevu.setText(media.getDatePrevuRetour());
        datePris.setText(media.getDatePret());
        dateReelle.setText(media.getDateReelleRetour());
        ratingBar.setRating(Float.parseFloat(String.valueOf(media.getCoteMedia())));
        if(media.getTypeMedia().equals("Filme")){
            rdbFilme.setChecked(true);
        } else if (media.getTypeMedia().equals("Jeu")){
            rdbJeu.setChecked(true);
        } else {
            rdbAutre.setChecked(true);
        }
    }
    /**
     * methode qui prende les donnes de vue
     */
    private  void prendreDonnesMedia (){
        media = new DonnesMedia(id, txtnomAmi.getText().toString(),
                txtTitre.getText().toString(),
                (int)Math.round(ratingBar.getRating()), datePris.getText().toString(),
                datePrevu.getText().toString(), dateReelle.getText().toString(), typeMedia);
    }
}
