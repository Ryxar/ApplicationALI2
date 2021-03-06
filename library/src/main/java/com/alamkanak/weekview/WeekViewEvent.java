package com.alamkanak.weekview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.alamkanak.weekview.WeekViewUtil.*;

/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014.
 * Website: http://april-shower.com
 */
public class WeekViewEvent implements Parcelable {
    private long mId;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mName;
    private String mLocation;
    private int mColor;
    private boolean mAllDay;
    private String mSalle;

    public WeekViewEvent() {

    }

    /**
     * Initializes the event for week view.
     *
     * @param id          The id of the event.
     * @param name        Name of the event.
     * @param startYear   Year when the event starts.
     * @param startMonth  Month when the event starts.
     * @param startDay    Day when the event starts.
     * @param startHour   Hour (in 24-hour format) when the event starts.
     * @param startMinute Minute when the event starts.
     * @param endYear     Year when the event ends.
     * @param endMonth    Month when the event ends.
     * @param endDay      Day when the event ends.
     * @param endHour     Hour (in 24-hour format) when the event ends.
     * @param endMinute   Minute when the event ends.
     */
    public WeekViewEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        this.mId = id;

        this.mStartTime = Calendar.getInstance();
        this.mStartTime.set(Calendar.YEAR, startYear);
        this.mStartTime.set(Calendar.MONTH, startMonth - 1);
        this.mStartTime.set(Calendar.DAY_OF_MONTH, startDay);
        this.mStartTime.set(Calendar.HOUR_OF_DAY, startHour);
        this.mStartTime.set(Calendar.MINUTE, startMinute);

        this.mEndTime = Calendar.getInstance();
        this.mEndTime.set(Calendar.YEAR, endYear);
        this.mEndTime.set(Calendar.MONTH, endMonth - 1);
        this.mEndTime.set(Calendar.DAY_OF_MONTH, endDay);
        this.mEndTime.set(Calendar.HOUR_OF_DAY, endHour);
        this.mEndTime.set(Calendar.MINUTE, endMinute);

        this.mName = name;
    }

    /**
     * Initializes the event for week view.
     *
     * @param id        The id of the event.
     * @param name      Name of the event.
     * @param location  The location of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     * @param allDay    Is the event an all day event.
     */
    public WeekViewEvent(long id, String name, String location, String salle, Calendar startTime, Calendar endTime, boolean allDay) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mSalle = salle;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAllDay = allDay;
    }

    /**
     * Initializes the event for week view.
     *
     * @param id        The id of the event.
     * @param name      Name of the event.
     * @param location  The location of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     */
    public WeekViewEvent(long id, String name, String location, String salle, Calendar startTime, Calendar endTime) {
        this(id, name, location, salle, startTime, endTime, false);
    }

    /**
     * Initializes the event for week view.
     *
     * @param id        The id of the event.
     * @param name      Name of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     */
    public WeekViewEvent(long id, String name, String salle, Calendar startTime, Calendar endTime) {
        this(id, name, null, salle, startTime, endTime);
    }


    protected WeekViewEvent(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mLocation = in.readString();
        mColor = in.readInt();
        mAllDay = in.readByte() != 0;
    }

    public static final Creator<WeekViewEvent> CREATOR = new Creator<WeekViewEvent>() {
        @Override
        public WeekViewEvent createFromParcel(Parcel in) {
            return new WeekViewEvent(in);
        }

        @Override
        public WeekViewEvent[] newArray(int size) {
            return new WeekViewEvent[size];
        }
    };

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public String getmSalle() {
        return mSalle;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setAllDay(boolean allDay) {
        this.mAllDay = allDay;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekViewEvent that = (WeekViewEvent) o;

        return mId == that.mId;

    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    public List<WeekViewEvent> splitWeekViewEvents() {
        //This function splits the WeekViewEvent in WeekViewEvents by day
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        // The first millisecond of the next day is still the same day. (no need to split events for this).
        Calendar endTime = (Calendar) this.getEndTime().clone();
        endTime.add(Calendar.MILLISECOND, -1);
        if (!isSameDay(this.getStartTime(), endTime)) {
            endTime = (Calendar) this.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent event1 = new WeekViewEvent(this.getId(), this.getName(), this.getLocation(), this.getmSalle(), this.getStartTime(), endTime, this.isAllDay());
            event1.setColor(this.getColor());
            events.add(event1);

            // Add other days.
            Calendar otherDay = (Calendar) this.getStartTime().clone();
            otherDay.add(Calendar.DATE, 1);
            while (!isSameDay(otherDay, this.getEndTime())) {
                Calendar overDay = (Calendar) otherDay.clone();
                overDay.set(Calendar.HOUR_OF_DAY, 0);
                overDay.set(Calendar.MINUTE, 0);
                Calendar endOfOverDay = (Calendar) overDay.clone();
                endOfOverDay.set(Calendar.HOUR_OF_DAY, 23);
                endOfOverDay.set(Calendar.MINUTE, 59);
                WeekViewEvent eventMore = new WeekViewEvent(this.getId(), this.getName(), null, this.getmSalle(), overDay, endOfOverDay, this.isAllDay());
                eventMore.setColor(this.getColor());
                events.add(eventMore);

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) this.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent event2 = new WeekViewEvent(this.getId(), this.getName(), this.getLocation(), this.getmSalle(), startTime, this.getEndTime(), this.isAllDay());
            event2.setColor(this.getColor());
            events.add(event2);
        } else {
            events.add(this);
        }

        return events;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mLocation);
        dest.writeInt(mColor);
        dest.writeByte((byte) (mAllDay ? 1 : 0));
    }
   /* public void calendarSet(){
        int hour, min, year, month, day, duration, min_end, hour_end;

        Calendar startTime = Calendar.getInstance();

        class_Date=this.

                getClass_date()

                .

                        split("-");

        class_Starttime=this.

                getClass_starttime()

                .

                        split(":");

        year=Integer.valueOf(class_Date[0]);
        month=Integer.valueOf(class_Date[1]);


        day=Integer.valueOf(class_Date[2]);
        duration=this.

                getClass_duration();

        min_end=duration%60;
        hour=Integer.valueOf(class_Starttime[0]);
        min=Integer.valueOf(class_Starttime[1]);
        hour_end=hour+((duration-min_end)/60);
        startTime.set(year,month-1,day,hour,min);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year,month-1,day,hour_end,min);
        if(min!=0)

        {
            endTime.add(Calendar.MINUTE, min_end);
        }

        super.

                setEndTime(endTime);

        super.

                setId(this.class_id);

        super.

                setStartTime(startTime);

        super.

                setName("Cours :"+this.class_id);

        super.

                setLocation("");
    }
*/
}
