package com.whitespace.mydailyvu.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.whitespace.mydailyvu.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView facebook, github, mail, contact, versionCode;
    LinearLayout email, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        close = findViewById(R.id.close_btn);
//        circularImageView = findViewById(R.id.circularImageView2);
        facebook = findViewById(R.id.facebook);
        github = findViewById(R.id.github);
        email = findViewById(R.id.email);
        call = findViewById(R.id.call);
        mail = findViewById(R.id.mail);
        contact = findViewById(R.id.contact);
        versionCode = findViewById(R.id.versionCode);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionCode.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/AhamedLinc0n"));
                startActivity(facebook);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent github = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/MrLincon"));
                startActivity(github);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = mail.getText().toString().trim();

                Intent feedback = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Email));

                try {
                    startActivity(Intent.createChooser(feedback, "Choose an e-mail client"));
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(AboutActivity.this, "There is no e-mail clint installed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Contact = contact.getText().toString().trim();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Contact));

                if (ActivityCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(AboutActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(callIntent);  //call activity and make phone call
                    } catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }
                }
            }
        });
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//    }

}

