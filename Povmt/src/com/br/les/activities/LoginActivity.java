package com.br.les.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.br.les.facebook.IntegratingFacebookApplication;
import com.br.les.povmt.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

  private Dialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Parse.enableLocalDatastore(this);
    
    Parse.initialize(this, "yrka0c4ucoQwsUPDttY4qrlpx8vooxPQxQr9AZrS", "hX8LIhU0gulZb4BycpdcqIzJKW7OXnxrP2IeWBat");

    setContentView(R.layout.main);

    // Check if there is a currently logged in user
    // and it's linked to a Facebook account.
    ParseUser currentUser = ParseUser.getCurrentUser();
    if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
      // Go to the user info activity
      showUserDetailsActivity();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
  }

  public void onLoginClick(View v) {
    progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);
    
    List<String> permissions = Arrays.asList("public_profile", "email");
    // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
    // (https://developers.facebook.com/docs/facebook-login/permissions/)
    
    ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        progressDialog.dismiss();
        if (user == null) {
          Log.d(IntegratingFacebookApplication.TAG, "Uh oh. The user cancelled the Facebook login.");
        } else if (user.isNew()) {
          Log.d(IntegratingFacebookApplication.TAG, "User signed up and logged in through Facebook!");
          showUserDetailsActivity();
        } else {
          Log.d(IntegratingFacebookApplication.TAG, "User logged in through Facebook!");
          showUserDetailsActivity();
        }
      }
    });
  }

  private void showUserDetailsActivity() {
    Intent intent = new Intent(this, UserProfileActivity.class);
    startActivity(intent);
  }
}
