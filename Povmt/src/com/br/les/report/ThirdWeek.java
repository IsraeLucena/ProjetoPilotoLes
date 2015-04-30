
package com.br.les.report;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.br.les.util.AdapterTime;
import com.br.les.util.ParseRequests;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ThirdWeek extends Fragment {

    private ListView listView;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.third_week, container, false);

        // json = ((WeeklyMonitoring) getActivity()).getJson();
        // Gson gson = new Gson();
        // currentUser = gson.fromJson(json, User.class);
        //
        // // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.listViewWeek3);
        try {
            final List<ActivityTI> activities = new ArrayList<ActivityTI>();
            ParseUser currentUser = ParseUser.getCurrentUser();
            JSONObject userProfile = currentUser.getJSONObject("profile");
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Activity");
            query.whereEqualTo("user", userProfile.getString("facebookId"));
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> arg0, ParseException arg1) {
                    for (ParseObject atv : arg0) {
                        activities.add(new ActivityTI(atv.getString("name"),
                                atv.getDate("activityDay"), atv
                                        .getString("priority"), atv
                                        .getLong("duration")));
                    }
                    AdapterTime adapter = new AdapterTime(activities,
                            getActivity());
                    listView.setAdapter(adapter);
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //
        // String[] values = currentUser.getThirdWeek().tiRank();
        //
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        // rootView.getContext(), android.R.layout.simple_list_item_1,
        // android.R.id.text1, values);
        //
        // // Assign adapter to ListView
        // listView.setAdapter(adapter);

        return rootView;
    }

}
