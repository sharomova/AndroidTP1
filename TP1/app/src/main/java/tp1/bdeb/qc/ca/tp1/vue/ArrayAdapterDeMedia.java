package tp1.bdeb.qc.ca.tp1.vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DbHelper;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;
import tp1.bdeb.qc.ca.tp1.modele.DonnesMedia;

/**
 * Created by Olga on 2015-09-30.
 */
public class ArrayAdapterDeMedia extends ArrayAdapter<DonnesMedia> {

    int mediaCategory;
    DbHelper dbHelper;
    Context context;
    DonnesAmis ami;

    public ArrayAdapterDeMedia(Context context, int resourceId,
                               List<DonnesMedia> items, int mediaCategory) {
        super(context, resourceId, items);
        this.context = context;
        this.mediaCategory = mediaCategory;
        dbHelper = DbHelper.getInstance(context);

    }

    /* Classe holder qui contiendra l'élément de la ligne */
    private class NainHolder {

        TextView txtNomMedia;
        TextView txtNomAmis;
        TextView txtDateRemis;
        TextView txtDatePrevu;
        TextView txtDateEmrunter;
        RatingBar ratingBar;
        ImageView type;
        ImageView resultat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NainHolder holder = null;
        DonnesMedia rowItem = getItem(position);
        int idAmi = dbHelper.getIdAmis(rowItem.getIdAmis());
        if(idAmi != -1) {
            ami = dbHelper.getAmi(idAmi);
        }
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.un_media, null);
            holder = new NainHolder();
            holder.txtNomMedia = (TextView) convertView.findViewById(R.id.un_media_txtNomMedia);
            holder.txtNomAmis = (TextView) convertView.findViewById(R.id.un_media_txtNomAmis);
            holder.txtDateEmrunter = (TextView) convertView.findViewById(R.id.un_media_txtDateEmpr);
            holder.txtDateRemis = (TextView) convertView.findViewById(R.id.un_media_txtDateRemis);
            holder.txtDatePrevu = (TextView) convertView.findViewById(R.id.un_media_txtDatePrevu);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.un_media_toBar);
            holder.type = (ImageView) convertView.findViewById(R.id.un_media_imgType);
            holder.resultat = (ImageView) convertView.findViewById(R.id.un_media_imgRetour);

            convertView.setTag(holder);
        } else
            holder = (NainHolder) convertView.getTag();
            //titre de la média
            holder.txtNomMedia.setText("<< " + rowItem.getNomMedia() + " >>");
            //affiche les médias
            if (!rowItem.getDatePrevuRetour().equals("")) {
                holder.txtNomAmis.setText(context.getResources().getString(R.string.emprunte_chez) + rowItem.getIdAmis());
                holder.txtDatePrevu.setText(context.getResources().getString(R.string.date_prevu) + ":  " + rowItem.getDatePrevuRetour());
                holder.txtDateRemis.setText(context.getResources().getString(R.string.date_reele) +": " + rowItem.getDateReelleRetour());
                holder.txtDateEmrunter.setText(context.getResources().getString(R.string.date_pris) + ":  " + rowItem.getDatePret());
            }
            // cote de la média
            holder.ratingBar.setRating(Float.parseFloat(String.valueOf(rowItem.getCoteMedia())));
            holder.ratingBar.setIsIndicator(true);
             //verification de la date
            boolean expire =  verifierDateRetour(rowItem.getDatePrevuRetour());
            //affiche la resuletate de média avec l'image
            if(expire && !rowItem.getDatePret().equals("")) {
                holder.resultat.setImageResource(R.drawable.fireball);
            } else  if(!expire && !rowItem.getDatePret().equals("")){
                holder.resultat.setImageResource(R.drawable.yes);
            }
           if(rowItem.getDatePret().equals("")){
               holder.resultat.setImageResource(R.drawable.home);
           }
            // affiche le type de la média avec l'image
            if (rowItem.getTypeMedia().equals("Filme")) {
                holder.type.setImageResource(R.drawable.psd);
            } else if (rowItem.getTypeMedia().equals("Jeu")) {
                holder.type.setImageResource(R.drawable.jeu);
            } else {
                holder.type.setImageResource(R.drawable.gol);
            }

        return convertView;
    }

    /**
     * methode qui verifier si la date de retour de média est expirée
     * @param endDate
     * @return boolean expiré ou non
     */
    private boolean verifierDateRetour(String endDate){
        boolean expire = false;
        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
         Locale locale = Locale.getDefault();
         Date actuelle = new Date();
        String dateStart = dfDate.format(actuelle);
        if (dateStart.compareTo(endDate)>0)
        {
            expire = true;
        }
        return expire;
    }

}
