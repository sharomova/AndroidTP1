package tp1.bdeb.qc.ca.tp1.vue;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;
import tp1.bdeb.qc.ca.tp1.modele.DonnesMedia;

/**
 * Created by Olga on 2015-09-30.
 */
public class MediaActivity extends MainActivity {
    ArrayAdapterDeMedia adapter;
    ArrayList<DonnesMedia>
            medias = new ArrayList<DonnesMedia>();
    ArrayList<DonnesAmis> amis = new ArrayList<DonnesAmis>();
    MainActivity main = new MainActivity();
    public static final String ID_MEDIA = "ID_MEDIA";
    int mediaCategory;
    ListView lstMedia;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        db = DbHelper.getInstance(this);
        Intent intent = getIntent();
        mediaCategory = intent.getIntExtra(main.ID_AMI, -1);
        //afficher tout les média
        if(mediaCategory == 1){
            medias = db.getTousMedia();
           trierMedia();
        }
        //afficher les médias empruntées
        if(mediaCategory == 0){
            medias = db.getEmprunteMedia();
            trierMedia();
        }

        lstMedia = (ListView) findViewById(R.id.activ_media_lstMedia);
        adapter = new ArrayAdapterDeMedia(this, R.layout.un_media, medias, mediaCategory);
        lstMedia.setAdapter(adapter);

        lstMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MediaActivity.this, getResources().getString(R.string.modifierMedia),
                        Toast.LENGTH_SHORT).show();
                int idMedia = medias.get(position).getIdMedia();
                Intent intent = new Intent(MediaActivity.this, ModifierAjouterUnMedia.class);
                intent.putExtra(ID_MEDIA, idMedia);
                startActivity(intent);
            }
        });


    }

    /**
     * methode qui trier les média par le nomd'emprunteur après par titre de la média
     */
    private void trierMedia(){
        //Sorting media avec nom de l'emprunteur
        Collections.sort(medias, new Comparator<DonnesMedia>(){
            @Override
            public int compare(DonnesMedia lhs, DonnesMedia rhs) {
                 //TODO Auto-generated method stub
                return lhs.getIdAmis().compareToIgnoreCase(rhs.getIdAmis());
            }
       });

        //Sorting media avec titre de media
        Collections.sort(medias, new Comparator<DonnesMedia>(){
            @Override
            public int compare(DonnesMedia lhs, DonnesMedia rhs) {
                // TODO Auto-generated method stub
                return lhs.getNomMedia().compareToIgnoreCase(rhs.getNomMedia());
            }
        });
    }
}
