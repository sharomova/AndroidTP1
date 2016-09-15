package tp1.bdeb.qc.ca.tp1.vue;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;

/**
 * Created by Olga on 2015-09-26.
 */

public class ModifierAjouterUnAmis extends MainActivity{
    MainActivity main =new MainActivity();
    DonnesAmis ami;
    DbHelper db;
    Button btnValider;
    ImageButton btnEmail;
    ImageButton phone;
    EditText nom;
    EditText email;
    EditText tel;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_un_amis);
        db = DbHelper.getInstance(this);
        initialiser();

        Intent intent = getIntent();
        id = intent.getIntExtra(main.ID_AMI, -1);
        if (intent != null && id != -1){
            ami = db.getAmi(id);
            ajouterAmiDansView();
        }

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    prendreDonnesAmi();
                    boolean update = db.updateAmi(ami);
                //revenir à la page principale
                Intent intent = new Intent(ModifierAjouterUnAmis.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //pour envoyer le courriel
        btnEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, ami.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.ubject_of_email));
                intent.putExtra(Intent.EXTRA_STREAM, "h");
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {

                }

            }
        });
        //pour faire un appel téléphonique
        phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + ami.getTel()));
                try {

                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });
    }

    /**
     * methode qui initialise les variables
     */
    private void initialiser () {
        btnValider = (Button) findViewById(R.id.modifier_amis_btnValider);
        nom = (EditText) findViewById(R.id.modifier_amis_txtNom);
        tel = (EditText) findViewById(R.id.modifier_amis_txtTel);
        email = (EditText) findViewById(R.id.modifier_amis_txtEmail);
        phone = (ImageButton) findViewById(R.id.modif_amis_btnphone);
        btnEmail = (ImageButton) findViewById(R.id.modif_amis_btnEmail);
    }

    /**
     * methode qui prende les donnes de vue
     */
    private  void prendreDonnesAmi (){
        ami = new DonnesAmis(id, nom.getText().toString(),
                tel.getText().toString() , email.getText().toString(), "");
    }
    /**
     * methode qui ajoute les donnes dans la View
     */
    private void ajouterAmiDansView() {
        nom.setText(ami.getNom());
        tel.setText(ami.getTel());
        email.setText(ami.getEmail());
    }
}
