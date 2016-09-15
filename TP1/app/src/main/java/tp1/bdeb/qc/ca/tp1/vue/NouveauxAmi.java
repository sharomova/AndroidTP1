package tp1.bdeb.qc.ca.tp1.vue;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;

/**
 * Created by Olga on 2015-10-22.
 */
public class NouveauxAmi extends MainActivity {
    private final int PICK_CONTACT = 1;
    MainActivity main = new MainActivity();
    DonnesAmis ami;
    DbHelper db;
    Button btnValider;
    Button btnContact;
    EditText nom;
    EditText email;
    EditText tel;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveaux_ami);
        db = DbHelper.getInstance(this);
        initialiser();

        Intent intent = getIntent();
        id = intent.getIntExtra(main.ID_AMI, -1);

        //bouton Add click
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nom.getText().toString().equals("")) {
                    prendreDonnesAmi();
                    db.ajouterAmi(ami);
                }
                //revenir à la page principale
                Intent intent = new Intent(NouveauxAmi.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //bouton Contact click, chercher les donnees dans la liste de contacts
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }

        });
    }

    /**
     * methode qui prende les données d'un ami et les ajout dans vue
     *
     * @param reqCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.
                                Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.
                                Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.
                                            CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
                                            id, null, null);
                            phones.moveToFirst();

                            Cursor courril = getContentResolver().query(ContactsContract.
                                            CommonDataKinds.Email.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
                                            id, null, null);
                            courril.moveToFirst();

                            String tel = phones.getString(phones.getColumnIndex(ContactsContract.
                                    CommonDataKinds.Phone.NUMBER));
                            String nomContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.
                                    Contacts.DISPLAY_NAME));
                            String courriel = courril.getString(courril.getColumnIndex
                                    (ContactsContract.CommonDataKinds.Email.DATA));

                            ami = new DonnesAmis(1, nomContact, tel, courriel, "");
                            ajouterAmiDansView();
                        }
                    }
                }
        }
    }

    /**
     * methode qui initialise les variables
     */

    private void initialiser() {
        btnValider = (Button) findViewById(R.id.nouvaux_amis_btnValider);
        btnContact = (Button) findViewById(R.id.nouveaux_amis_btnContact);
        nom = (EditText) findViewById(R.id.nouvaux_amis_txtNom);
        tel = (EditText) findViewById(R.id.nouveaux_amis_txtTel);
        email = (EditText) findViewById(R.id.nouveaux_amis_txtEmail);
    }

    /**
     * methode qui prende les donnes de vue
     */
    private void prendreDonnesAmi() {
        ami = new DonnesAmis(id, nom.getText().toString(),
                tel.getText().toString(), email.getText().toString(), "");
    }

    /**
     * methode qui ajoute les données dans la vue
     */
    private void ajouterAmiDansView() {
        nom.setText(ami.getNom());
        tel.setText(ami.getTel());
        email.setText(ami.getEmail());
    }
}
