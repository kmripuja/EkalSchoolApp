package ekalschoolapp.pooja.com.ekalschoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SearchTeacherActivity extends AppCompatActivity {

    Button btn_searchteacher, btn_checkvillage1;
    TextView tv_showteacher, showteacher;
    String villagename;
    int year;
    private DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_teacher);

        btn_checkvillage1 = (Button) findViewById(R.id.btn_checkvillage1);
        btn_searchteacher = (Button)  findViewById(R.id.btn_searchteacher);
        tv_showteacher = (TextView) findViewById(R.id.tv_showteacher);
        showteacher = (TextView) findViewById(R.id.showteacher);

        btn_checkvillage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(SearchTeacherActivity.this, btn_checkvillage1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_checkvillage1);
                                vone.setText("Chand pur");
                                Toast.makeText(SearchTeacherActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_checkvillage1);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(SearchTeacherActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;

                            case R.id.vthree:
                                Button vthree = (Button) findViewById(R.id.btn_checkvillage1);
                                vthree.setText("Kishan Pur");
                                Toast.makeText(SearchTeacherActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Kishan Pur";
                                break;

                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_checkvillage1);
                                vfour.setText("Nagta");
                                Toast.makeText(SearchTeacherActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        // to search teacher
        final String[] keyval = new String[1];

        dref = FirebaseDatabase.getInstance().getReference();
        year = Calendar.getInstance().get(Calendar.YEAR);
        DatabaseReference sref = dref.child("profile"  );

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User userinfo = snapshot.getValue(User.class);
                    {
                        if (snapshot.getValue() != null) {
                            if(userinfo.getVillage().equals(villagename) )
                                keyval[0] = snapshot.getKey();
                        } else {
                            Toast.makeText(SearchTeacherActivity.this, "Teacher not Found!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

           System.out.println("key: "+ keyval[0]);
        btn_searchteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] name = new String[1];
                final String[] email = new String[1];
                final String[] state = new String[1];
                final String[] village = new String[1];
                final int[] phone = new int[1];
                DatabaseReference sref = dref.child("profile/" + keyval[0] );
                sref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            email[0] = user.getemail();
                            name[0] = user.getName();
                            state[0] = user.getState();
                            village[0] = user.getVillage();
                            phone[0] = user.getPhone();
                            System.out.println("Name: "+ String.valueOf(name[0]) +"\n Phone: "+ String.valueOf(phone[0])+ "\n Village: "+ String.valueOf(village[0]) + "\n State: " + String.valueOf(state[0]) + "\nEmail-Id: "+ String.valueOf(email[0]));

                        }


                        tv_showteacher.setText("Teacher Info: ");
                        showteacher.setText("Name: "+ name[0] +"\n Phone: "+ String.valueOf(phone[0])+ "\n Village: "+ village[0] + "\n State: " + state[0] + "\nEmail-Id: "+ email[0]);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        // getting teacher info


    }

}
