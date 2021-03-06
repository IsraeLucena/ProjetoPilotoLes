
package com.br.les.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.br.les.util.ActivityListClickListener;
import com.br.les.util.AdapterTime;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CurrentWeek extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.third_week, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewWeek3);

        getActivities();

        return rootView;
    }

    private void getActivities() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
                                              // hour of day !
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);

            // get start of this week in milliseconds
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            final List<ActivityTI> activities = new ArrayList<ActivityTI>();
            ParseUser currentUser = ParseUser.getCurrentUser();
            JSONObject userProfile = currentUser.getJSONObject("profile");
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Activity");
            query.whereEqualTo("user", userProfile.getString("facebookId"));
            query.whereGreaterThanOrEqualTo("activityDay", cal.getTime());
            Log.i("das", cal.getTime().toString());
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(final List<ParseObject> arg0,
                        ParseException arg1) {
                    threadAddList(activities, arg0);

                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void threadAddList(final List<ActivityTI> activities,
            final List<ParseObject> arg0) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HashMap<String, List<ParseObject>> activitiesList = new HashMap<String, List<ParseObject>>();
                Log.i("das", arg0.size() + "");
                for (ParseObject atv : arg0) {
                    if (activitiesList.containsKey(atv.getString(
                            "name")
                            .toLowerCase(Locale.US).trim())) {
                        activitiesList.get(
                                atv.getString("name")
                                        .toLowerCase(Locale.US)
                                        .trim())
                                .add(atv);
                    } else {
                        List<ParseObject> temp = new ArrayList<ParseObject>();
                        temp.add(atv);
                        activitiesList.put(
                                atv.getString("name")
                                        .toLowerCase(Locale.US)
                                        .trim(),
                                temp);
                    }

                }
                for (String atvKey : activitiesList.keySet()) {
                    long duration = 0L;
                    Date date = null;
                    String priority = null;
                    String name = null;
                    for (ParseObject atv : activitiesList
                            .get(atvKey)) {
                        duration += atv.getLong("duration");
                        date = atv.getDate("activityDay");
                        priority = atv.getString("priority");
                        name = atv.getString("name");
                    }
                    activities.add(new ActivityTI(name,
                            date, priority, duration));
                }
                Log.i("das", activities.size() + "");
                listView.setOnItemClickListener(new ActivityListClickListener(
                        activitiesList, getActivity()));
                AdapterTime adapter = new AdapterTime(activities,
                        getActivity());
                listView.setAdapter(adapter);

            }
        }).run();
    }

}
