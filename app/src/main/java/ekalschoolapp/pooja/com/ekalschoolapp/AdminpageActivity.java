package ekalschoolapp.pooja.com.ekalschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminpageActivity extends AppCompatActivity  {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    Button btn_checkdetails, btn_uploadfiles, btn_steacherdetail, btn_sendnotification, btn_slogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);


        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));


        }

        btn_checkdetails = (Button) findViewById(R.id.btn_checkdetails);

        btn_checkdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminpageActivity.this, CheckDetailsActivity.class);
                startActivity(intent);
            }

        });


        btn_uploadfiles = (Button) findViewById(R.id.btn_uploadfile);

        btn_uploadfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminpageActivity.this, UploadFilesActivity.class);
                startActivity(intent);
            }

        });

        btn_steacherdetail = (Button) findViewById(R.id.btn_steacherdetail);

        btn_steacherdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminpageActivity.this, SearchTeacherActivity.class);
                startActivity(intent);
            }

        });

        btn_sendnotification = (Button) findViewById(R.id.btn_sendnotification);

        btn_sendnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminpageActivity.this, SendNotificationActivity.class);
                startActivity(intent);
            }

        });

        btn_slogout = (Button) findViewById(R.id.btn_slogout);

        btn_slogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(AdminpageActivity.this, HomeActivity.class));
            }

        });
    }


}
