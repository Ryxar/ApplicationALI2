package com.example.ali2nat.v1.Adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ali2nat.v1.Modele.Periode;
import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.R;
import com.example.ali2nat.v1.ViewHolder.PeriodeViewHolder;
import com.example.ali2nat.v1.ViewHolder.SalleViewHolder;

import java.util.List;

/**
 * Created by alexi on 12/05/2016.
 */
public class PeriodeAdapteur extends ArrayAdapter{
    public PeriodeAdapteur(Context context, List<Periode> periodes){
        super(context, 0 ,periodes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // convertView = nul quand Android nous demande de le créer
        // sinon  il vient d'une vue recyclée
        if(convertView == null){
            // on get row_liste via un LayoutInflater,
            // charge un layout xml en objet view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_periode_detail, parent, false);
        }

        PeriodeViewHolder viewHolder = (PeriodeViewHolder) convertView.getTag();
        if(viewHolder == null){
            // Si notre vue n'as pas encore du viewHolder
            viewHolder = new PeriodeViewHolder();
            //on récupère nos sous vue

            viewHolder.tvNomPeriode = (TextView) convertView.findViewById(R.id.tvNomPeriode);
            viewHolder.tvInt1 = (TextView) convertView.findViewById(R.id.tvInt1);
            viewHolder.tvInt2 = (TextView) convertView.findViewById(R.id.tvInt2);

            // On sauvegarde le mini controlleur dans la vue
            convertView.setTag(viewHolder);

        }
        Periode periodes = (Periode) getItem(position);
        viewHolder.tvNomPeriode.setText(periodes.getNomPeriode());
        viewHolder.tvInt1.setText(periodes.getInt1().getText());
        viewHolder.tvInt2.setText(periodes.getInt2().getText());

        return convertView;
    }
}
