package tp1.bdeb.qc.ca.tp1.vue;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tp1.bdeb.qc.ca.tp1.R;
import tp1.bdeb.qc.ca.tp1.modele.DonnesAmis;

/**
 * Created by Olga on 2015-09-25.
 */
public class ArrayAdapterDeAmis extends ArrayAdapter<DonnesAmis> {
    Context context;
    public ArrayAdapterDeAmis(Context context, int resourceId,
                               List<DonnesAmis> items) {
        super(context, resourceId, items);
        this.context = context;
    }
    /* Classe holder qui contiendra l'élément de la ligne */
    private class NainHolder {

        TextView txtNom;
        TextView txtTel;
        TextView txtEmeil;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NainHolder holder = null;
        DonnesAmis rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
           convertView = mInflater.inflate(R.layout.un_amis, null);
            holder = new NainHolder();
            holder.txtNom = (TextView) convertView.findViewById(R.id.un_amis_txtNom);
            holder.txtTel = (TextView) convertView.findViewById(R.id.un_amis_txtTel);
            holder.txtEmeil = (TextView) convertView.findViewById(R.id.un_amis_txtEmail);

            convertView.setTag(holder);
        } else
            holder = (NainHolder) convertView.getTag();

        holder.txtNom.setText(rowItem.getNom());
        holder.txtEmeil.setText("E-mail : " + rowItem.getEmail());
        holder.txtTel.setText("Tel :" + String.valueOf(rowItem.getTel()));

        return convertView;
    }
}
