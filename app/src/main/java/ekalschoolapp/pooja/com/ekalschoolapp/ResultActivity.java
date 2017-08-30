package ekalschoolapp.pooja.com.ekalschoolapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_marks;
    TextView tv_marks;
    Button btn_saveresult, btn_classresult, btn_resultvillage;
    ListView lv_result;
    DatabaseReference dref;
    FirebaseUser curuser;
    String villagename;
    List<Studentdetails> studdetail;
    Result_listAdapter adapter;
    String classno;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        et_marks = (EditText) findViewById(R.id.et_result);
        btn_saveresult = (Button) findViewById(R.id.btn_saveresult);
        btn_classresult = (Button) findViewById(R.id.btn_classresult);
        lv_result = (ListView) findViewById(R.id.lv_result);
        tv_marks = (TextView) findViewById(R.id.tv_result);
        btn_resultvillage = (Button) findViewById(R.id.btn_resultvillage);

        curuser = FirebaseAuth.getInstance().getCurrentUser();
        studdetail = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);

     /*   dref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = dref.child("profile/" + curuser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                villagename = user.getVillage();
                // tv_resulttitle.setText(villagename);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ResultActivity.this, "Please Complete your profile!!" , Toast.LENGTH_SHORT).show();
            }
        });  */

        btn_resultvillage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                android.widget.PopupMenu popup = new android.widget.PopupMenu(ResultActivity.this, btn_resultvillage);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_resultvillage);
                                vone.setText("Chand pur");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_resultvillage);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;

                            case R.id.vthree:
                                Button vthree = (Button) findViewById(R.id.btn_resultvillage);
                                vthree.setText("Kishan Pur");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Kishan Pur";
                                break;

                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_resultvillage);
                                vfour.setText("Nagta");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        btn_classresult.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ResultActivity.this, btn_classresult);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_classresult);
                                one.setText("I");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                showlist(classno);
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_classresult);
                                two.setText("II");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                showlist(classno);
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_classresult);
                                three.setText("III");
                                Toast.makeText(ResultActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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

         btn_saveresult.setOnClickListener(this);

    }

    public void showlist(String classno1)
    {

        DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("student/" + villagename + "/" + year + "/" + classno1 );

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studdetail.clear();   // to clear listview content
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Studentdetails studlist = snapshot.getValue(Studentdetails.class);
                    studdetail.add(studlist);
                }

                //Init adapter
                adapter = new Result_listAdapter(ResultActivity.this, R.layout.result_list_item, studdetail);

                //Set adapter for listview
                adapter.notifyDataSetChanged();
                lv_result.setAdapter(null);
                lv_result.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public class Result_listAdapter extends ArrayAdapter<Studentdetails> {

        private ArrayList<Studentdetails> studlist;
        private String[] arrTemp;

        public Result_listAdapter(Context context, int textViewResourceId,
                                  List<Studentdetails> resultlist) {
            super(context, textViewResourceId, resultlist);
            this.studlist = new ArrayList<>();
            this.studlist.addAll(resultlist);
        }

        private class ViewHolder {
            TextView name;
            EditText marks;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ResultActivity.Result_listAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.result_list_item, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tv_result);
                holder.marks = (EditText) convertView.findViewById(R.id.et_result);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            //Fill EditText with the value you have in data source
            //   holder.marks.setText(studlist.get(position).getMarks());
            //  holder.marks.setId(position);
            final Studentdetails stud = studlist.get(position);
            holder.name.setText(stud.getName() + " (" + stud.getRollno() + ")");


            holder.marks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        final EditText et = (EditText) v;
                        final Studentdetails stud1 = (Studentdetails) et.getTag();
                        stud1.setMarks(Integer.parseInt(et.getText().toString()));
                        System.out.println("list size1 = " + studlist.size());
                                   // to remove focus from edittext
                                    /*
                                    et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if(actionId== EditorInfo.IME_ACTION_DONE){
                                            //Clear focus here from edittext
                                            et.clearFocus();
                                        }
                                        return false;
                                    }
                                });
                                     */
                    }


                }
            });


       /*     if (lv_result != null) {
                for (int i = 0; i < lv_result.getChildCount(); i++) {
                    View v = lv_result.getChildAt(i);

                    EditText ed1 = (EditText) v;
                    Studentdetails stud1 = (Studentdetails) ed1.getTag();
                    stud1.setMarks(Integer.parseInt(ed1.getText().toString()));
                    holder.marks.setTag(stud1);
                    Log.d("value", ed1.getText().toString() + "marks: " + stud1.getMarks());
                }
            }
            */

                holder.marks.setTag(stud);

                return convertView;

            }

        }

        public void saveresult() {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("student/" + villagename + "/" + year + "/" + classno);
            System.out.println(villagename);

            ArrayList<Studentdetails> studlist = adapter.studlist;
            System.out.println("list size = " + studlist.size());
            for (int i = 0; i < studlist.size(); i++) {
                Studentdetails stud = studlist.get(i);

                Query pendingTasks = ref.orderByChild("rollno").equalTo(stud.getRollno());
                final int mr = stud.getMarks();
                System.out.println("rollno= " + stud.getRollno() + "marks = " + mr);
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                            snapshot.getRef().child("marks").setValue(mr);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());
                    }

                });

            }
            //displaying a success toast
            Toast.makeText(this, "Marks saved", Toast.LENGTH_LONG).show();

        }


        @Override
        public void onClick(View view) {

            if (view == btn_saveresult) {
                if (classno != null) {


                    saveresult();
                } else
                    Toast.makeText(ResultActivity.this, "Please select class no.", Toast.LENGTH_SHORT).show();

            }

        }



}
