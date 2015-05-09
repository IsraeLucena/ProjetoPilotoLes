
package com.br.les.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.br.les.activities.InfoDialog;
import com.br.les.timeitup.ActivityTI;
import com.parse.ParseObject;

public class ActivityListClickListener implements OnItemClickListener {

    private HashMap<String, List<ParseObject>> atv;
    private Context ctx;

    public ActivityListClickListener(HashMap<String, List<ParseObject>> atv,
            Context ctx) {
        this.atv = atv;
        this.ctx = ctx;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        ActivityTI actSelected = (ActivityTI) parent
                .getItemAtPosition(position);
        List<ParseObject> weekAct = atv.get(actSelected.getName()
                .toLowerCase(Locale.US).trim());
        new InfoDialog(ctx, weekAct).show();
    }
}
