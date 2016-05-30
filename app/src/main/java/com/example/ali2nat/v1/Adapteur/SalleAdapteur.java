package com.example.ali2nat.v1.Adapteur;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali2nat.v1.Modele.Salle;
import com.example.ali2nat.v1.R;
import com.example.ali2nat.v1.ViewHolder.SalleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis Pentori on 09/05/2016.
 */
public class SalleAdapteur extends ArrayAdapter<Salle>{
    private List<Salle> liste;

    public SalleAdapteur(Context context, List<Salle> listes){
        super(context, 0 ,listes);
        liste = new ArrayList<>();
        liste = listes;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // convertView = nul quand Android nous demande de le créer
        // sinon  il vient d'une vue recyclée
        if(convertView == null){
            // on get row_liste via un LayoutInflater,
            // charge un layout xml en objet view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_salle_detail, parent, false);
        }


        SalleViewHolder viewHolder = (SalleViewHolder) convertView.getTag();
        if(viewHolder == null){
            // Si notre vue n'as pas encore du viewHolder
            viewHolder = new SalleViewHolder();
            //on récupère nos sous vue

            viewHolder.tvNomSalle = (TextView) convertView.findViewById(R.id.tvNom);
            viewHolder.tvAdresse = (TextView) convertView.findViewById(R.id.tvAdresse);
            viewHolder.bAjout = (Button)convertView.findViewById(R.id.bAjout);

            // On sauvegarde le mini controlleur dans la vue
            convertView.setTag(viewHolder);

        }
        Salle salle = getItem(position);
        viewHolder.tvNomSalle.setText(salle.getNom());
        viewHolder.tvAdresse.setText(salle.getAdresse());
        // si la salle est a ajouter
        viewHolder.bAjout.setText("v");

        viewHolder.bAjout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });
        return convertView;
    }

    public Salle getItem(int position) {
        return liste.get(position);
    }


}
