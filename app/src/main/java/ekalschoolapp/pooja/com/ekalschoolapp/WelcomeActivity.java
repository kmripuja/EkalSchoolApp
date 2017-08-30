package ekalschoolapp.pooja.com.ekalschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView  tv1;

    Button btn_student;
    Button btn_profile;
    Button btn_studymat;
    Button btn_exam;
    Button btn_attendance;
    Button btn_result;


    DatabaseReference ref;
    FirebaseUser curuser;
    String villagename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv1 = (TextView) findViewById(R.id.tv1);
        ref = FirebaseDatabase.getInstance().getReference();
        curuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dref = ref.child("profile/" + curuser.getUid());

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           /*     User user = dataSnapshot.getValue(User.class);
                villagename = user.getVillage();
                if(villagename != null) {
                    System.out.println(villagename);
                    tv1.setText(villagename + " School");
                }   */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        btn_student = (Button) findViewById(R.id.btn_student);

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, StudentActivity.class);
                startActivity(intent);
            }

        });

        btn_profile = (Button) findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

        });

        btn_studymat = (Button) findViewById(R.id.btn_study);

        btn_studymat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, StudyMaterialActivity.class);
                startActivity(intent);
            }

        });




        btn_exam = (Button) findViewById(R.id.btn_exam);

        btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ExamActivity.class);
                startActivity(intent);
            }

        });

        btn_attendance = (Button) findViewById(R.id.btn_attendance);

        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }

        });

        btn_result = (Button) findViewById(R.id.btn_result);

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ResultActivity.class);
                startActivity(intent);
            }

        });


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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.queries: {
                Intent intent = new Intent(WelcomeActivity.this, QueriesActivity.class);
                startActivity(intent);
                return true;
             }
            case R.id.feedback: {
                Intent intent = new Intent(WelcomeActivity.this, FeedbackActivity.class);
                startActivity(intent);
                return true;
             }
            case R.id.adminpage:{
                Intent intent = new Intent(WelcomeActivity.this, AdminpageActivity.class);
                startActivity(intent);
                return true;
             }
            case R.id.logout: {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, LoginActivity.class));
                return true;
             }
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    @Override
    public void onClick(View view) {
        //if logout is pressed
   /*     if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }  */
    }
}
