package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Calendar;

public class UserActivity extends BaseActivity {
    TextView verifyEmail;
    FirebaseAuth fAuth;
    Button Vote;
    SliderView sliderView;
    int[] images = {R.drawable.logo4, R.drawable.image1, R.drawable.image2,};

    private boolean firstTimeUsed = false;
    String firstTimeUsedKey = "FIRST_TIME";

    String sharedPreferencesKey = "MY_PREF";
    String buttonClickedKey = "BUTTON_CLICKED";
    SharedPreferences mPrefs;
    private long savedDate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TSHOGYEN");

        verifyEmail = findViewById(R.id.verifyTextUser);
        fAuth = FirebaseAuth.getInstance();

        Vote = findViewById(R.id.vote);
        //ImageSlider
        sliderView = findViewById(R.id.image_slider);
        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        //ImageSlider
        mPrefs = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
        savedDate = mPrefs.getLong(buttonClickedKey,0);
        firstTimeUsed = mPrefs.getBoolean(firstTimeUsedKey,true);//default is true if no value is saved
        checkPrefs();

//        Calendar today = Calendar.getInstance();
//        int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
//        int month = today.get(Calendar.MONTH);
//        int year = today.get(Calendar.YEAR);
//        if (month == Calendar.JUNE && dayOfMonth == 4 && year == 2021) {
//            // Day of voting
//            Vote.setEnabled(true);
//        }
//        else {
//            Vote.setEnabled(false);
//        }

        Vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CCvote.class));
                finish();
                saveClickedTime();
            }
        });

        if (!fAuth.getCurrentUser().isEmailVerified()){
            verifyEmail.setVisibility(View.VISIBLE);
        }

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        verifyEmail.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void saveClickedTime() {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Calendar cal = Calendar.getInstance();
        long millis = cal.getTimeInMillis();
        mEditor.putLong(buttonClickedKey,millis);
        mEditor.putBoolean(firstTimeUsedKey,false); //the button is clicked first time, so set the boolean to false.
        mEditor.commit();

        //hide the button after clicked
        Vote.setEnabled(false);
    }

    private void checkPrefs(){
        if(firstTimeUsed == false) {
            if(savedDate>0) {
                //create two instances of Calendar and set minute,hour,second and millis
                //to 1, to be sure that the date differs only from day,month and year

                Calendar currentCal = Calendar.getInstance();
                currentCal.set(Calendar.MINUTE,1);
                currentCal.set(Calendar.HOUR,1);
                currentCal.set(Calendar.SECOND,1);
                currentCal.set(Calendar.MILLISECOND,1);

                Calendar savedCal = Calendar.getInstance();
                savedCal.setTimeInMillis(savedDate); //set the time in millis from saved in sharedPrefs
                savedCal.set(Calendar.MINUTE,1);
                savedCal.set(Calendar.HOUR,1);
                savedCal.set(Calendar.SECOND,1);
                savedCal.set(Calendar.MILLISECOND,1);

                if (currentCal.getTime().after(savedCal.getTime())){
                    Vote.setEnabled(true);
                }
                else if (currentCal.getTime().equals(savedCal.getTime())){
                    Vote.setEnabled(false);
                }

            }
        }
        else {
            //just set the button visible if app is used the first time
            Vote.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        if(item.getItemId() == R.id.resetPassword){
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
        }
        if(item.getItemId() == R.id.user_profile){
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
        if(item.getItemId() == R.id.about){
            startActivity(new Intent(getApplicationContext(), AboutPage.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotovote(View view){
        startActivity(new Intent(getApplicationContext(), CCvote.class));
    }

    public void gotocandidate(View view){
        startActivity(new Intent(getApplicationContext(), ViewCandidateActivity.class));
    }

    public void gotoResult(View view) {
        startActivity(new Intent(getApplicationContext(), ccResult.class));
    }
}