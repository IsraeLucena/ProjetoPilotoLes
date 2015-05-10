
package com.br.les.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.br.les.povmt.R;

/**
 * Class with a list of the rooms.
 */
public class DialogLoadImage extends Dialog {

    /**
     * The class constructor.
     */
    public static final int MEDIA_TYPE_IMAGE = 1;

    /** The Constant MEDIA_TYPE_VIDEO. */
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** The frag. */
    private Context mContext;

    /**
     * Instantiates a new dialog load image.
     * 
     * @param context the context
     */
    public DialogLoadImage(final Context context) {
        super(context);
        mContext = context;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Dialog#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading_image);

        startButtonGetCamera();
        startButtonGetGalery();
    }

    /**
     * Start button get camera.
     */
    private void startButtonGetCamera() {
        final Button buttonCam = (Button) findViewById(R.id.button2Cam);
        buttonCam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(mContext
                        .getPackageManager()) != null) {

                    ((Activity) mContext).startActivityForResult(
                            takePictureIntent,
                            ((CreateTI) mContext).REQUEST_IMAGE_CAPTURE);
                }

                dismiss();
            }
        });
    }

    /**
     * Start button get galery.
     */
    private void startButtonGetGalery() {
        final Button buttonGallery = (Button) findViewById(R.id.button1Galery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub

                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                ((Activity) mContext).startActivityForResult(intent,
                        ((CreateTI) mContext).REQUEST_LOAD_IMAGE);
                dismiss();
            }
        });

    }

    /**
     * Gets the output media file uri.
     * 
     * @param type the type
     * @return the output media file uri
     */
    private static Uri getOutputMediaFileUri(final int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video.
     * 
     * @param type the type
     * @return the output media file
     */
    private static File getOutputMediaFile(final int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        final File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "FindMovies");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.i("fas", "failed to create directory");
            return null;

        }

        // Create a media file name
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
