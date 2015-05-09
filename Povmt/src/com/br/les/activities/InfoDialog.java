
package com.br.les.activities;

import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.br.les.povmt.R;
import com.parse.ParseObject;

public class InfoDialog extends Dialog {

    private Context ctx;
    private List<ParseObject> weekAct;
    private WebView web;

    public InfoDialog(Context context, List<ParseObject> weekAct) {
        super(context);
        this.weekAct = weekAct;
        this.ctx = context;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);
        Calendar c = Calendar.getInstance();
        JSONArray json = new JSONArray();
        web = (WebView) findViewById(R.id.webView1);
        web.getSettings().setJavaScriptEnabled(true);
        int dayOfWeek;
        String data = "[";

        long duration = 0L;
        for (ParseObject atv : weekAct) {
            duration += atv.getLong("duration");
        }
        for (ParseObject act : weekAct) {
            String day = "";
            JSONObject jsonToSend = new JSONObject();

            double percentagem = (act.getLong("duration") * 100)
                    / duration;
            c.setTime(act.getDate("activityDay"));
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case 1:
                    day += "Domingo";
                    break;
                case 2:
                    day += "Segunda";
                    break;
                case 3:
                    day += "Terça";
                    break;
                case 4:
                    day += "Quarta";
                    break;
                case 5:
                    day += "Quinta";
                    break;
                case 6:
                    day += "Sexta";
                    break;
                case 7:
                    day += "Sabado";
                    break;

                default:
                    break;
            }

            try {
                jsonToSend.put("name", day);
                jsonToSend.put("y", percentagem);
                json.put(jsonToSend);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        data += "]";

        WebAppInterface inter = new WebAppInterface();
        inter.setDays(json);
        web.addJavascriptInterface(inter, "Android");
        web.loadUrl("file:///android_res/raw/graphic");
        Log.i("dar", data);

    }

    public class WebAppInterface {
        JSONArray days;

        @JavascriptInterface
        public String getDays() {
            return days.toString();
        }

        public void setDays(JSONArray days) {
            this.days = days;
        }
    }

}
