package com.br.les.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.les.timeitup.ActivityTI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseRequests {
    
    public static List<ActivityTI> getMyActivities() throws JSONException {
        final List<ActivityTI> activities = new ArrayList<ActivityTI>();
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONObject userProfile = currentUser.getJSONObject("profile");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Activity");
        query.whereEqualTo("user",userProfile.getString("facebookId"));
        query.findInBackground( new FindCallback<ParseObject>() {
            
            @Override
            public void done(List<ParseObject> arg0, ParseException arg1) {
                for(ParseObject atv : arg0) {
                    activities.add(new ActivityTI(atv.getString("name"), atv.getDate("activityDay"),atv.getString("priority"), atv.getLong("duration")));
                }
            }
        });
        
        return activities;
  
    }

}
