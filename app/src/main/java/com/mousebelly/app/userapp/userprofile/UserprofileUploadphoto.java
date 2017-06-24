package com.mousebelly.app.userapp.userprofile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cropper.CropImage;
import com.example.cropper.CropImageView;
import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.CustomProgressDialog;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;

import org.json.JSONException;

import static com.mousebelly.app.userapp.userprofile.GetUserProfileData.userprofileUserBean;

public class UserprofileUploadphoto extends AppCompatActivity {
    Button register;
    ProgressDialog pg;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_photo);


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        pg = CustomProgressDialog.getDialog(UserprofileUploadphoto.this,"Saving");
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Usersend().execute();
            }
        });
    }

    public void onSelectImageClick(View view) {
        startCropImageActivity(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            System.out.println("This is Result ::::::::::::::::" + result);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());

                System.out.println("This is result get URI::::::::::::::::::::" + result.getUri());
                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);

    }

    public class Usersend extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("This is do in background");

            try {
                Server.s.post(APIs.sign_sign, userprofileUserBean.tojson());

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            if (status==200){
                                Toast.makeText(UserprofileUploadphoto.this, "Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserprofileUploadphoto.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(UserprofileUploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPreExecute();
            pg.dismiss();

        }
    }
}
