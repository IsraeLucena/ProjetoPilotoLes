package com.br.les.facebook;

import android.app.Application;

import com.parse.Parse;

public class IntegratingFacebookTutorialApplication extends Application {

  static final String TAG = "MyApp";

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.initialize(this, 
        "gK6txX8ALKEZKX4fSU3GvYGQCBhYKJ0fbouIqVXN",
        "agdnZxR0G9Z8xOhfYYOmFmsd9HKN0lwz7MpEwntH"
    );
  }
}
