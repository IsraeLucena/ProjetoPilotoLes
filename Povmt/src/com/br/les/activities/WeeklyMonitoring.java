
package com.br.les.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.br.les.povmt.R;
import com.br.les.report.TabsPagerAdapter;

public class WeeklyMonitoring extends FragmentActivity implements TabListener {

    private static final String JSONUSER = "JsonUser";
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    // Tab titles
    private static final String[] TABS = {
            "Current", "Last", "Before last"
    };
    private String json;

    @SuppressLint("NewApi")
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_monitoring);

        if (!isConnected()) {
            dialogError(R.string.connection_error,
                    R.string.try_again_connection);
        } else {

            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                json = bundle.getString(JSONUSER);
                //Gson gson = new Gson();
            } else {
                String possibleEmail = "";
                try {
                    Account[] accounts = AccountManager.get(this)
                            .getAccountsByType("com.google");

                    if (accounts.length > 0) {
                        possibleEmail += accounts[0].name;
                    }
                } catch (Exception e) {
                    Log.i("Exception", "Exception:" + e);
                }

                
                // No Google account logged
                if ("".equals(possibleEmail)) {
                    dialogError(R.string.user_login_error,
                            R.string.try_again_user_login);
                } else {
                    
                }
            }
            initiatingView();
        }

    }

    @SuppressLint("NewApi")
    private void initiatingView() {
        // System.out.println("####CURRENTUSER: " + currentUser);
        viewPager = (ViewPager) findViewById(R.id.weekly_monitoring);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tabName : TABS) {
            actionBar.addTab(actionBar.newTab().setText(tabName)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(final int arg0, final float arg1,
                    final int arg2)
                    throws UnsupportedOperationException {
            }

            @Override
            public void onPageScrollStateChanged(final int arg0)
                    throws UnsupportedOperationException {
            }

        });
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ti_main, menu);
        return true;
    }

    @Override
    public void onTabSelected(final Tab tab, final FragmentTransaction ft)
            throws UnsupportedOperationException {
    }

    @Override
    public void onTabUnselected(final Tab tab, final FragmentTransaction ft)
            throws UnsupportedOperationException {
    }

    @Override
    public void onTabReselected(final Tab tab, final FragmentTransaction ft)
            throws UnsupportedOperationException {
    }

    /**
     * If back button pressed, finalize Activity.
     */
    @Override
    public final void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.quit_search)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int which) {
                                finish();
                            }
                        }).setNegativeButton(R.string.no, null).show();
    }

    /**
     * If some error, creates dialog for the user.
     */
    public final void dialogError(final int title, final int message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.exit,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int which) {
                                finish();
                            }
                        }).show().setCancelable(false);

    }

    /**
     * Check internet connection.
     * 
     * @return the connection.
     */
    public final boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public final String getJson() {
        return json;
    }

    /**
     * Event Handling for Individual menu item selected Identify single menu.
     * item by it's id.
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_createTI) {
            Toast.makeText(WeeklyMonitoring.this, "Create a new TI",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(WeeklyMonitoring.this, CreateTI.class);
            i.putExtra(JSONUSER, json);
            finish();
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
