package com.mousebelly.app.userapp.userprofile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.mousebelly.app.userapp.R;

import static com.mousebelly.app.userapp.userprofile.GetUserProfileData.userprofileUserBean;

public class UserprofileUserPro extends AppCompatActivity {

    EditText Username, Password, Cnfrmpass, Phone;
    TextView Email;
    TextView ph, cnf, pass, errmail, erruser;
    Button Nextmap;
    String Check = "Ok", emailCheck = "Ok", PhnCheck = "Ok";

    String Usernameof, Emailof, Passof, cnfpassof, phoneof;

    ProgressBar pg ;
    RelativeLayout userDetailsLayout;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_userpro);

        context = UserprofileUserPro.this;


        Username = (EditText) findViewById(R.id.username);
        Email = (TextView) findViewById(R.id.email);
        Phone = (EditText) findViewById(R.id.phone);
        ph = (TextView) findViewById(R.id.phn);
        errmail = (TextView) findViewById(R.id.erroremail);
        erruser = (TextView) findViewById(R.id.errusername);
        Nextmap = (Button) findViewById(R.id.nextmap);

        userDetailsLayout = (RelativeLayout)findViewById(R.id.user_details);
        userDetailsLayout.setVisibility(View.INVISIBLE);
        pg = (ProgressBar)findViewById(R.id.pg);

        new LoadUserDetails().execute();


        Nextmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(Username.getText().toString());

                if (TextUtils.isEmpty(Username.getText().toString())) {
                    Username.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Phone.getText().toString())) {
                    Phone.setError("This field is required");
                    return;
                }


                System.out.println("This is username before :" + Username.getText().toString());
                System.out.println("This is username before" + Phone.getText().toString());


                if (Username.getText().toString().equals("") || Username.getText().toString() == null || !Check.equals("Ok") ||
                        Phone.getText().toString().equals("") || Phone.getText().toString() == null || !PhnCheck.equals("Ok")
                        ) {
                    Toast.makeText(UserprofileUserPro.this, "One or more field are not properly filled", Toast.LENGTH_LONG).show();
                } else {
                    UserprofileMainActivity obj = new UserprofileMainActivity();
                    obj.display();

                }

            }
        });


        Username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // Check = "Ok";
                Usernameof = Username.getText().toString();

                System.out.println("This is Username of : " + Usernameof);
                System.out.println("This is Username length : " + Usernameof.length());
                Check = userprofileUserBean.validateUsername(Username.getText().toString());
                erruser.setVisibility(View.VISIBLE);
                erruser.setText(Check);
                if (Check.equals("Ok")) {
                    erruser.setVisibility(View.GONE);
                }
            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                emailCheck = userprofileUserBean.validateEmail(Email.getText().toString());
                errmail.setVisibility(View.VISIBLE);
                errmail.setText(emailCheck);
                if (emailCheck.equals("Ok")) {
                    errmail.setVisibility(View.GONE);
                }

            }
        });




        Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PhnCheck = userprofileUserBean.validatePhone(Phone.getText().toString());
                ph.setVisibility(View.VISIBLE);
                ph.setText(PhnCheck);
                if (PhnCheck.equals("Ok")) {
                    ph.setVisibility(View.GONE);
                }
            }
        });


    }


    class LoadUserDetails extends AsyncTask<Void, Void, Void> {
        GetUserProfileData getProfileData = new GetUserProfileData(UserprofileUserPro.this);

        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);

          }
        @Override
        protected Void doInBackground(Void... voids) {

            getProfileData.loadData();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            userDetailsLayout.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);


            Username.setText(userprofileUserBean.getUserName());
            Usernameof = Username.getText().toString();

            Email.setText(userprofileUserBean.getEmail());

            Phone.setText(userprofileUserBean.getPhone());
        }
    }

}
