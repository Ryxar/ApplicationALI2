package com.example.ali2nat.v1;

import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Adrien.D on 24/05/2016.
 */
public abstract class AbstractFragment extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private WeekView mWeekView;
    private Button BtnSemSui,BtnSemPrec;

    public AbstractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vp = inflater.inflate(R.layout.fragment_planning, container, false);


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) vp.findViewById(R.id.weekView);
        BtnSemSui=(Button)vp.findViewById(R.id.SemaineSuivante);
        BtnSemPrec=(Button)vp.findViewById(R.id.SemainePrecedente);
        BtnSemSui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SemaineSuiv();
            }
        });
        BtnSemPrec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SemainePrec();
            }
        });



        mWeekView.setNumberOfVisibleDays(7);
        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);



        // Inflate the layout for this fragment
        return vp;
    }
    public void SemaineSuiv(){

        mWeekView.setcurrX(mWeekView.getWidth());

}
    public void SemainePrec(){

        mWeekView.setcurrX(-mWeekView.getWidth());
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat("d/M", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 24 ? (hour) + "H" : (hour == 0 ? "0H" : hour + "H");
            }
        });
    }

    protected String getEventTitle(Calendar time,String info) {
        return String.format(info, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
       showPopUpInfo(event);
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        showPopUpSuppr(event);
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        showPopUpAjout();
    }
    private void showPopUpSuppr(WeekViewEvent event) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext());
        helpBuilder.setTitle(event.getName());
        helpBuilder.setMessage("Voulez-vous supprimer ce cour ?");
        helpBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing but close the dialog
                    }
                });

        helpBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
    private void showPopUpInfo(WeekViewEvent event) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext());
        helpBuilder.setTitle(event.getName());
        helpBuilder.setMessage("Salle:"+event.getmSalle()+"\n De :"+String.format("%d "+"H"+" %d",event.getStartTime().get(Calendar.HOUR_OF_DAY)+7,event.getStartTime().get(Calendar.MINUTE))+"\n A :"+String.format("%d "+"H"+" %d",event.getEndTime().get(Calendar.HOUR_OF_DAY)+7,event.getEndTime().get(Calendar.MINUTE)));
        helpBuilder.setNeutralButton("Retour", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
    private void showPopUpAjout() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext());
        helpBuilder.setTitle("Formulaire d'ajout");
        helpBuilder.setMessage("etc");
        helpBuilder.setPositiveButton("Ajouter",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        helpBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }
}
