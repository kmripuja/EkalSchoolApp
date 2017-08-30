package ekalschoolapp.pooja.com.ekalschoolapp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_studname,et_studrollno;
    TextView tv_villagestud, tv_studtitle;
    Button btn_addstud, btn_classstud, btn_studvillage;
    ListView lv_stud;
    DatabaseReference dref;
    FirebaseUser curuser;
    String villagename;
    List<Studentdetails> studdetail;
    String classno;
    int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        et_studname = (EditText) findViewById(R.id.et_studname);
        et_studrollno = (EditText) findViewById(R.id.et_studrollno);
        btn_addstud = (Button) findViewById(R.id.btn_addstud);
        btn_classstud = (Button) findViewById(R.id.btn_classstud);
        lv_stud = (ListView) findViewById(R.id.lv_stud);
        tv_studtitle = (TextView) findViewById(R.id.tv_studtitle);
        btn_studvillage = (Button) findViewById(R.id.btn_studvillage);

        curuser = FirebaseAuth.getInstance().getCurrentUser();

        btn_studvillage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(StudentActivity.this, btn_studvillage);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_studvillage);
                                vone.setText("Chand pur");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_studvillage);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;

                            case R.id.vthree:
                                Button vthree = (Button) findViewById(R.id.btn_studvillage);
                                vthree.setText("Kishan Pur");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Kishan Pur";
                                break;

                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_studvillage);
                                vfour.setText("Nagta");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method



        btn_classstud.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(StudentActivity.this, btn_classstud);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_classstud);
                                one.setText("I");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                tv_studtitle.setText("Village - " + villagename + " \n" +classno + " Student List");
                                showlist(classno);
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_classstud);
                                two.setText("II");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                tv_studtitle.setText("Village - " + villagename + " \n" +classno + " Student List");
                                showlist(classno);
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_classstud);
                                three.setText("III");
                                Toast.makeText(StudentActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-3";
                                tv_studtitle.setText("Village - " + villagename + " \n" + classno + " Student List");
                                showlist(classno);
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

       dref = FirebaseDatabase.getInstance().getReference();
        //list to store artists
        studdetail = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);


      /*  DatabaseReference ref = dref.child("profile/" + curuser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                villagename = user.getVillage();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });  */


        lv_stud.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Studentdetails stud = studdetail.get(i);
                showUpdateDeleteDialog(stud.getName(), stud.getRollno());
                return true;
            }
        });

        btn_addstud.setOnClickListener(this);


    }

    private void addStudent() {
        //getting the values to save
        String name = et_studname.getText().toString().trim();
        String rollno = et_studrollno.getText().toString().trim();

        DatabaseReference sref = FirebaseDatabase.getInstance().getReference("student");
//        System.out.println(villagename);
            //checking if the value is provided
            if(!TextUtils.isEmpty(name))
            {
                Studentdetails stud = new Studentdetails(name, rollno);
                String key = sref.child( villagename + "/" + year + "/" + classno).push().getKey();
                sref.child( villagename + "/"  + year + "/" + classno + "/" + key).setValue(stud);

                //setting edittext to blank again
                et_studname.setText("");
                et_studrollno.setText("");

                //displaying a success toast
                Toast.makeText(this, "Student added", Toast.LENGTH_LONG).show();
            }

            else {
                //if the value is not given displaying a toast
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }
        }

    public void showlist(String classno1)
    {

        DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("student/" + villagename + "/"  + year + "/"  + classno1 );

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studdetail.clear();   // to clear listview content
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Studentdetails studlist = snapshot.getValue(Studentdetails.class);
                    studdetail.add(studlist);
                }

                //Init adapter
                Student_listAdapter adapter = new Student_listAdapter(StudentActivity.this, R.layout.student_list_item, studdetail);

                //Set adapter for listview
                adapter.notifyDataSetChanged();
                lv_stud.setAdapter(null);
                lv_stud.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateStudent(String keyval, String name, String rollno, int marks, int attendance) {
        DatabaseReference sref = dref.child("student/" + villagename + "/" + year + "/" + classno + "/" + keyval );
        Studentdetails stud = new Studentdetails(name, rollno, marks, attendance);
        sref.setValue(stud);
        Toast.makeText(getApplicationContext(), "Student Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteStudent(String keyval) {
        DatabaseReference sref = dref.child("student/" + villagename + "/" + year + "/" + classno + "/" + keyval );
        sref.removeValue();
        Toast.makeText(getApplicationContext(), "Student Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String name, final String rollno) {

        final String[] keyval = new String[1];

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatestudent, null);
        dialogBuilder.setView(dialogView);

        final EditText et_updatestudname = (EditText) dialogView.findViewById(R.id.et_updatestudname);
        final EditText et_updatestudrollno = (EditText) dialogView.findViewById(R.id.et_updatestudrollno);
        final EditText et_updatestudmarks = (EditText) dialogView.findViewById(R.id.et_updatestudmarks);
        final EditText et_updatestudattendance = (EditText) dialogView.findViewById(R.id.et_updatestudattendance);
        final Button btn_updatestud = (Button) dialogView.findViewById(R.id.btn_updatestud);
        final Button btn_deletestud = (Button) dialogView.findViewById(R.id.btn_deletestud);

        dialogBuilder.setTitle(name +"(" + rollno + ")");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        DatabaseReference sref = dref.child("student/" + villagename + "/" + year + "/" + classno );
        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Studentdetails studlist = snapshot.getValue(Studentdetails.class);
                    if(studlist.getName().equals(name)  && studlist.getRollno().equals(rollno) )
                        keyval[0] = snapshot.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn_updatestud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatestudname = et_updatestudname.getText().toString().trim();
                String updatestudrollno = et_updatestudrollno.getText().toString().trim();
                int marks = Integer.parseInt(et_updatestudmarks.getText().toString().trim());
                int attendance = Integer.parseInt(et_updatestudattendance.getText().toString().trim());
                if (!TextUtils.isEmpty(updatestudname)) {
                    updateStudent(keyval[0], updatestudname, updatestudrollno, marks, attendance);
                    b.dismiss();
                }
            }
        });


        btn_deletestud.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deleteStudent(keyval[0]);
                b.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view == btn_addstud)
            addStudent();

    }

}
