package ekalschoolapp.pooja.com.ekalschoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CheckDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_checktype, btn_checkclass,btn_show, btn_checkvillage;
    TextView showattendance, showresult, tv_showattend, tv_showresult;
    String checktype;
    private DatabaseReference dref;
    FirebaseUser curuser;
    String villagename;
    List<Studentdetails> studdetail;
    String classno;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_details);

        btn_checkvillage = (Button) findViewById(R.id.btn_checkvillage);
        btn_show = (Button)  findViewById(R.id.btn_showdetails);
        showattendance = (TextView) findViewById(R.id.showattend);
        showresult = (TextView) findViewById(R.id.showresult);
        tv_showattend = (TextView) findViewById(R.id.tv_showattend);
        tv_showresult = (TextView) findViewById(R.id.tv_showresult);


        btn_checkvillage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(CheckDetailsActivity.this, btn_checkvillage);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_checkvillage);
                                vone.setText("Chand pur");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_checkvillage);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;

                            case R.id.vthree:
                                Button vthree = (Button) findViewById(R.id.btn_checkvillage);
                                vthree.setText("Kishan Pur");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Kishan Pur";
                                break;

                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_checkvillage);
                                vfour.setText("Nagta");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        btn_checkclass = (Button)  findViewById(R.id.btn_checkclass);
        btn_checkclass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(CheckDetailsActivity.this, btn_checkclass);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_checkclass);
                                one.setText("I");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_checkclass);
                                two.setText("II");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_checkclass);
                                three.setText("III");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-3";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

   /*     btn_checktype = (Button)  findViewById(R.id.btn_checktype);
        btn_checktype.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(CheckDetailsActivity.this, btn_checktype);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_checktype, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.exam_papers:
                                Button one = (Button) findViewById(R.id.btn_checktype);
                                one.setText("Check Attendance Detail");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                checktype = "checkattendence";
                                break;

                            case R.id.study_material:
                                Button two = (Button) findViewById(R.id.btn_checktype);
                                two.setText("Check Result Detail");
                                Toast.makeText(CheckDetailsActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                checktype = "checkresults";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

*/

        curuser = FirebaseAuth.getInstance().getCurrentUser();
        dref = FirebaseDatabase.getInstance().getReference();
        //list to store artists
        studdetail = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);

/*
        DatabaseReference ref = dref.child("profile/" + curuser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                villagename = user.getVillage();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        btn_show.setOnClickListener(this);
    }

    public void showdetails()
    {
        DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("student/" + villagename + "/"  + year + "/"  + classno );
        final int[] attendsum = {0};
        final int[] resultsum = {0};
        sref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Studentdetails studlist = snapshot.getValue(Studentdetails.class);
                     int attendval = studlist.getAttendance();
                    int resultval = studlist.getMarks();
                      attendsum[0] += attendval;
                    resultsum[0] += resultval;
                   // System.out.println("attendval: " + attendval + "Resultval: " + resultval);
                  //  System.out.println("attend: " + attendsum[0] + "Result: " + resultsum[0]);
                }

                tv_showattend.setText("Average Attendance : ");
                tv_showresult.setText("Average Result : ");
                showattendance.setText(String.valueOf(attendsum[0]));
                showresult.setText(String.valueOf(resultsum[0]));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        //if the clicked button is choose
        if (view == btn_show) {
            showdetails();
        }
    }
}
