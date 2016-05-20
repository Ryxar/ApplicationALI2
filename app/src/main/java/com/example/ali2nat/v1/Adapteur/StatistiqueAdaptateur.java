package com.example.ali2nat.v1.Adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ali2nat.v1.Modele.Statistique;
import com.example.ali2nat.v1.R;
import com.example.ali2nat.v1.ViewHolder.StatistiqueViewHolder;

import java.util.List;

/**
 * Created by alexi on 12/05/2016.
 */
public class StatistiqueAdaptateur extends ArrayAdapter<Statistique>{
    public StatistiqueAdaptateur(Context context, List<Statistique> listes){
        super(context, 0 ,listes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // convertView = nul quand Android nous demande de le créer
        // sinon  il vient d'une vue recyclée
        if(convertView == null){
            // on get row_liste via un LayoutInflater,
            // charge un layout xml en objet view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_statistique_detail, parent, false);
        }

        StatistiqueViewHolder viewHolder = (StatistiqueViewHolder) convertView.getTag();
        if(viewHolder == null){
            // Si notre vue n'as pas encore du viewHolder
            viewHolder = new StatistiqueViewHolder();
            //on récupère nos sous vue

            viewHolder.tvNom = (TextView) convertView.findViewById(R.id.tvNom);
            viewHolder.llProgression = (LinearLayout) convertView.findViewById(R.id.llProgression);
            viewHolder.tvResultat = (TextView) convertView.findViewById(R.id.tvResultat);

            // On sauvegarde le mini controlleur dans la vue
            convertView.setTag(viewHolder);

        }
        Statistique stat = getItem(position);
        String resultat = stat.getAvancement() + " / " + stat.getTotal();

        viewHolder.tvNom.setText(stat.getNom());
        viewHolder.tvResultat.setText(resultat);

        ViewGroup.LayoutParams params = viewHolder.llProgression.getLayoutParams();
        params.width = stat.getAvancement()*2; // 200dp = 100% donc coef 2
        viewHolder.llProgression.setLayoutParams(params);


        return convertView;
    }
}
