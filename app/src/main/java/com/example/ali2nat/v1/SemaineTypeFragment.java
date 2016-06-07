package com.example.ali2nat.v1;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Adrien.D on 01/06/2016.
 */
public class SemaineTypeFragment extends AbstractFragment  {
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents;

    private OnCoursSelectedListener mCallback;


    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);
        return events;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vp = inflater.inflate(R.layout.fragment_semaine_type, container, false);

        // On récupère le bundle
        Bundle bundle = this.getArguments();

        mNewEvents = new ArrayList<WeekViewEvent>();

        /*int taille = bundle.getInt(MainActivity.EVENT_KEY);
        Log.d("yolo", "onCreateView: taille liste"+taille);
        for (int t = 0; t< taille; t++)
            events.add((WeekViewEvent) bundle.getParcelable(MainActivity.EVENT_NUM_TYPE + t));*/


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
    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
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
        showPopUpSuppr(event);
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        showPopUpAjout(time);
    }
    private void showPopUpSuppr(WeekViewEvent event) {

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
        helpBuilder.setMessage("info sur le cours " + event.getStartTime() );

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
    private void showPopUpAjout(Calendar time) {

        final Dialog dialog = new Dialog(getContext());
        final Date date = time.getTime();
        dialog.setContentView(R.layout.dialog_ajout);
        dialog.setTitle("Formulaire d'ajout");

        // there are a lot of settings, for dialog, check them all out!
        // set up radiobutton

        Button btnAjout=(Button)dialog.findViewById(R.id.btnAjout);
        Button btnRetour=(Button)dialog.findViewById(R.id.btnRetour);





        btnAjout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePicker timePicker=(TimePicker)dialog.findViewById(R.id.timePicker);
                RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.rd_1);
                RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.rd_2);
                timePicker.setIs24HourView(true);
                int h=timePicker.getCurrentHour();
                int gh=h-7;
                // Set the new event with duration one hour.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, gh);
                startTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                Calendar endTime = (Calendar)startTime.clone();
                endTime.add(Calendar.HOUR, 1);

                // Create a new event.
                if(rd1.isChecked()){
                WeekViewEvent event = new WeekViewEvent(20, "Paris 9 Bergère","salle", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_03));
                mNewEvents.add(event);}
                if(rd2.isChecked()){
                    WeekViewEvent event = new WeekViewEvent(21, "Paris 4 Saint Merri","salle", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_02));
                    mNewEvents.add(event);}

                // Refresh the week view. onMonthChange will be called again.
                mWeekView.notifyDatasetChanged();
                dialog.dismiss();
            }
        });


        btnRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Set the new event with duration one hour.
                dialog.dismiss();

            }
        });

        // Remember, create doesn't show the dialog

        dialog.show();
    }



    public WeekView getWeekView() {
        return mWeekView;
    }



    //public void setEvents(List<WeekViewEvent> events) {        this.events = events;    }

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
