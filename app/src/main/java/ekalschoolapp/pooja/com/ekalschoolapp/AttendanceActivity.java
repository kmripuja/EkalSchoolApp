package ekalschoolapp.pooja.com.ekalschoolapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    List<Studentdetails> attendlist;
    Studentdetails[] checked;
    ListView lv_attendance;
    Attendance_listAdapter adapter;
    String villagename;
    DatabaseReference dref;
    FirebaseUser curuser;
    Button btn_classattend, btn_attendvillage;
    TextView tv_attend, tv_attendtitle;
    CheckBox cb_attendance;
    String classno;
    Button findSelected;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        attendlist = new ArrayList<>();
        lv_attendance = (ListView) findViewById(R.id.lv_attendance);
        tv_attend = (TextView) findViewById(R.id.tv_attend);
        cb_attendance = (CheckBox) findViewById(R.id.cb_attendance);
        findSelected = (Button) findViewById(R.id.findSelected);
        btn_attendvillage = (Button) findViewById(R.id.btn_attendvillage);
        year = Calendar.getInstance().get(Calendar.YEAR);

        curuser = FirebaseAuth.getInstance().getCurrentUser();
        dref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = dref.child("profile/" + curuser.getUid());
     /*   ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                villagename = user.getVillage();
                System.out.println(villagename);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        tv_attendtitle = (TextView) findViewById(R.id.tv_attendtitle);
        btn_classattend = (Button) findViewById(R.id.btn_classattend);
        tv_attendtitle.setText(villagename);

        btn_attendvillage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                android.widget.PopupMenu popup = new android.widget.PopupMenu(AttendanceActivity.this, btn_attendvillage);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_attendvillage);
                                vone.setText("Chand pur");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_attendvillage);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;

                            case R.id.vthree:
                                Button vthree = (Button) findViewById(R.id.btn_attendvillage);
                                vthree.setText("Kishan Pur");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Kishan Pur";
                                break;

                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_attendvillage);
                                vfour.setText("Nagta");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        btn_classattend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(AttendanceActivity.this, btn_classattend);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_classattend);
                                one.setText("I");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                showlist(classno);
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_classattend);
                                two.setText("II");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                showlist(classno);
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_classattend);
                                three.setText("III");
                                Toast.makeText(AttendanceActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-3";
                                showlist(classno);
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        findSelected.setOnClickListener(this);

    }

    public void showlist(String classno1)
    {

        DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("student/" + villagename + "/" + year + "/"   + classno1 );

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attendlist.clear();   // to clear listview content
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Studentdetails studlist = snapshot.getValue(Studentdetails.class);
                    attendlist.add(studlist);
                }

                //Init adapter
                adapter = new Attendance_listAdapter(AttendanceActivity.this, R.layout.student_list_item, attendlist);

                //Set adapter for listview
                adapter.notifyDataSetChanged();
                lv_attendance.setAdapter(null);
                lv_attendance.setAdapter(adapter);

                lv_attendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // When clicked, show a toast with the TextView text
                        Studentdetails stud = (Studentdetails) parent.getItemAtPosition(position);
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Row: " + stud.getName(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class Attendance_listAdapter extends ArrayAdapter<Studentdetails> {

        private ArrayList<Studentdetails> studlist;

       public Attendance_listAdapter(Context context, int textViewResourceId,
                                      List<Studentdetails> attendlist) {
            super(context, textViewResourceId, attendlist);
            this.studlist = new ArrayList<>();
            this.studlist.addAll(attendlist);
        }

        public class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.attendance_list_items, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.tv_attend);
                holder.name = (CheckBox) convertView.findViewById(R.id.cb_attendance);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Studentdetails stud = (Studentdetails) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        stud.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Studentdetails stud = studlist.get(position);
            holder.code.setText(" (" +  stud.getRollno() + ")");
            holder.name.setText(stud.getName());
            holder.name.setChecked(stud.isSelected());
            holder.name.setTag(stud);

            return convertView;

        }

    }

    public void checkButtonClick() {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

           DatabaseReference ref = dref.child("student/" + villagename + "/" + year + "/"  + classno );

                ArrayList<Studentdetails> studlist = adapter.studlist;
                for(int i=0;i<studlist.size();i++){
                    Studentdetails stud = studlist.get(i);
                    if(stud.isSelected()){

                        Query pendingTasks = ref.orderByChild("rollno").equalTo(stud.getRollno());

                        final int at = stud.getAttendance();
                        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                    snapshot.getRef().child("attendance").setValue(at + 1);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getMessage());
                            }

                        });

                        responseText.append("\n" + stud.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View view) {

        if(view == findSelected)
            checkButtonClick();

    }
}
