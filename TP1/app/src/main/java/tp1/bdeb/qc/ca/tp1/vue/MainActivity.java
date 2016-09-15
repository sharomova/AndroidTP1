package tp1.bdeb.qc.ca.tp1.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;

public class MainActivity extends AppCompatActivity {

    ArrayAdapterDeAmis adapter;
    ArrayList<DonnesAmis> amis = new ArrayList<DonnesAmis>();
    public static final String ID_AMI = "ID_AMI";
    public static final int TOUT_MEDIA = 1;
    public static final int EMPRUNTE_MEDIA = 0;
    ListView latAmis;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DbHelper.getInstance(this);
        amis = db.getTousAmis();
        //Sorting amis avec nom
        Collections.sort(amis, new Comparator<DonnesAmis>() {
            @Override
            public int compare(DonnesAmis lhs, DonnesAmis rhs) {
                // TODO Auto-generated method stub
                return lhs.getNom().compareToIgnoreCase(rhs.getNom());
            }
        });

        latAmis = (ListView) findViewById(R.id.main_activ_lstAmis);
        adapter = new ArrayAdapterDeAmis(this, R.layout.un_amis, amis);
        latAmis.setAdapter(adapter);
        //click sur un média
        latAmis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.modifierAmi),
                       Toast.LENGTH_SHORT).show();
                int idAmi = amis.get(position).getId();
                Intent intent = new Intent(MainActivity.this, ModifierAjouterUnAmis.class);
                intent.putExtra(ID_AMI, idAmi);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Création d'un MenuInflater qui va permettre d'instancier un Menu XML en un objet Menu
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.ajouter_amis:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.agoutAmi), Toast.LENGTH_SHORT).show();
                Intent intentAjouter = new Intent(MainActivity.this, NouveauxAmi.class);
                startActivity(intentAjouter);
                return true;
            case R.id.amis:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.ami), Toast.LENGTH_SHORT).show();
                Intent amis = new Intent(MainActivity.this, MainActivity.class);
                startActivity(amis);
                return true;
            case R.id.media:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.media), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tout_media:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.tout_media), Toast.LENGTH_SHORT).show();
                Intent intentTout = new Intent(MainActivity.this, MediaActivity.class);
                intentTout.putExtra(ID_AMI, TOUT_MEDIA);
                startActivity(intentTout);
                return true;
            case R.id.empruntes_media:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.emprunter_media), Toast.LENGTH_SHORT).show();
                Intent intentEmprunte = new Intent(MainActivity.this, MediaActivity.class);
                intentEmprunte.putExtra(ID_AMI, EMPRUNTE_MEDIA);
                startActivity(intentEmprunte);
                return true;
            case R.id.ajouter_media:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.add_media), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ModifierAjouterUnMedia.class);
                startActivity(intent);
                return true;
        }
        return false;}
}
