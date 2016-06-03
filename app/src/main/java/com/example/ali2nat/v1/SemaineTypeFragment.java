package com.example.ali2nat.v1;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Adrien.D on 01/06/2016.
 */
public class SemaineTypeFragment extends AbstractFragment  {
    private WeekView mWeekView;
    private   List<WeekViewEvent> events;

    private OnCoursSelectedListener mCallback;


    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
      //events= new ArrayList<WeekViewEvent>();

        // Populate the week view with some events.
        //events = getEvents();
        Log.d("yolo", "onMonthChange:liste "+events.size());


        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 10);
        WeekViewEvent event = new WeekViewEvent(5, "Yolo","NomYoloSalle", startTime, endTime);

        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);





        return events;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vp = inflater.inflate(R.layout.fragment_semaine_type, container, false);

        // On récupère le bundle
        Bundle bundle = this.getArguments();

        events = new ArrayList<>();

        int taille = bundle.getInt(MainActivity.EVENT_KEY);
        Log.d("yolo", "onCreateView: taille liste"+taille);
        for (int t = 0; t< taille; t++)
            events.add((WeekViewEvent) bundle.getParcelable(MainActivity.EVENT_NUM_TYPE + t));


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) vp.findViewById(R.id.weekView);



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
                SimpleDateFormat format = new SimpleDateFormat("", Locale.getDefault());

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
        showPopUpSuppr();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        showPopUpAjout();
    }
    private void showPopUpSuppr() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext());
        helpBuilder.setTitle("Titre du cour");
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
        helpBuilder.setTitle("Titre du cour");
        helpBuilder.setMessage("info sur le cours " + event.getId());

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
                        Calendar startTime, endTime;
                        String intensite = "Cours d'intensité repeat";
                        String salle = "une salle sur Paris";
                        startTime = Calendar.getInstance();
                        startTime.set(Calendar.DAY_OF_MONTH, 1);
                        startTime.set(Calendar.HOUR_OF_DAY, 15 - 7);
                        startTime.set(Calendar.MINUTE, 0);
                        endTime = (Calendar) startTime.clone();
                        endTime.add(Calendar.HOUR_OF_DAY, 15);
                        WeekViewEvent event = new WeekViewEvent(1, intensite, salle, startTime, endTime);
                        event.setColor(getResources().getColor(R.color.event_color_02));
                        getEvents().add(event);
                        Log.d("yolo", "onClick: ");
                       // mWeekView.getMoreEvents();
                        mCallback.onArticleSelected(event);
                        mWeekView.notifyDatasetChanged();
                        CharSequence text = "taille :" + getEvents().size();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getActivity(), text, duration);
                        toast.show();

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
        this.events = events;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            Log.d("yolo", "onAttach: ");
            mCallback = (OnCoursSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    // Container Activity must implement this interface
    public interface OnCoursSelectedListener {
        public void onArticleSelected(WeekViewEvent event);
        // le type est pour dire si liste de recherche ou liste préf
    }




}
