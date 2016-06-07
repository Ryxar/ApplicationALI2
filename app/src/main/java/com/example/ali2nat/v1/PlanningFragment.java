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
import com.example.ali2nat.v1.Modele.Salle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanningFragment extends AbstractFragment   {
    private String info="Null";
    private String salle="Null";
    private WeekView mWeekView;
    private Button BtnSemSui,BtnSemPrec;
    private String intensite="Null";
    private   List<WeekViewEvent> events;

        public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.

            int decalage=7;

            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();


            intensite="Cours d'intensité HARDCORE";
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 7-decalage);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth - 1);
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);
            endTime.set(Calendar.MONTH, newMonth - 1);
            WeekViewEvent event = new WeekViewEvent(1, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 30);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 4);
            endTime.set(Calendar.MINUTE, 30);
            endTime.set(Calendar.MONTH, newMonth-1);
            event = new WeekViewEvent(10, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 4);
            startTime.set(Calendar.MINUTE, 20);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 5);
            endTime.set(Calendar.MINUTE, 0);
            event = new WeekViewEvent(10, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_03));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 5);
            startTime.set(Calendar.MINUTE, 30);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 2);
            endTime.set(Calendar.MONTH, newMonth-1);
            event = new WeekViewEvent(2, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 5);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth - 1);
            startTime.set(Calendar.YEAR, newYear);
            startTime.add(Calendar.DATE, 1);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            endTime.set(Calendar.MONTH, newMonth - 1);
            event = new WeekViewEvent(3, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_03));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, 15);
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            event = new WeekViewEvent(4, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_04));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, 1);
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            event = new WeekViewEvent(5, intensite,salle, startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

            // Faire une boucle avec le Json
            for (int i = 0; i < 10; i++) {
                intensite="Cours d'intensité repeat";
                salle="une salle sur Paris";
                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_MONTH, 1+i);
                startTime.set(Calendar.HOUR_OF_DAY, 15-decalage);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.add(Calendar.HOUR_OF_DAY, 3);
                event = new WeekViewEvent(5, intensite,salle, startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_02));
                events.add(event);
            }
            setEvents(events);




            return events;
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
                SimpleDateFormat format = new SimpleDateFormat("  d/M", Locale.getDefault());

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

                        /* WeekViewEvent event = genererCours("Salle de bonhomme ta vue", date);
                        event.setColor(getResources().getColor(R.color.event_color_03));
                        getEvents().add(event);
                        Log.d("yolo", "onClick: ");
                       // mWeekView.getMoreEvents();*/
                        //mCallback.onArticleSelected(event);
                        mWeekView.notifyDatasetChanged();
                        CharSequence text = "taille :" + getEvents().size();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getActivity(), text, duration);
                        toast.show();
                        events=getEvents();
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, 7-7);
                        startTime.set(Calendar.MINUTE, 0);
                        Calendar endTime = (Calendar) startTime.clone();
                        endTime.add(Calendar.HOUR, 1);
                        WeekViewEvent event = new WeekViewEvent(1, "ok",null,"nok", startTime, endTime,false);
                        event.setColor(getResources().getColor(R.color.event_color_02));
                        events.add(event);
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

    public List<WeekViewEvent> getEvents() {
        return this.events;
    }
    public void setEvents(List<WeekViewEvent> events) {
        this.events=events;
    }

    }








