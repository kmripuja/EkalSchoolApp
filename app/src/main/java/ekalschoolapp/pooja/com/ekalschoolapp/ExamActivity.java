package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    DatabaseReference dref;
    private ListView lv_exam;
    private StoragefilesAdapter adapter;
    List<Storagefiles> paperlist;
    Button btn_examclass;
    String classno;
    String downloadtype = "exam_papers";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        progressDialog = new ProgressDialog(this);

        lv_exam = (ListView) findViewById(R.id.lv_exam);

        //  progressDialog.setMessage("Please wait loading file-list...");
        //   progressDialog.show();

        paperlist = new ArrayList<>();


        btn_examclass = (Button) findViewById(R.id.btn_examclass);
        btn_examclass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ExamActivity.this, btn_examclass);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_examclass);
                                one.setText("I");
                                Toast.makeText(ExamActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                showlist(classno);
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_examclass);
                                two.setText("II");
                                Toast.makeText(ExamActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                showlist(classno);
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_examclass);
                                three.setText("III");
                                Toast.makeText(ExamActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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




    }

    public void showlist(final String classno1)
    {
        // to clear listview content
        paperlist.clear();

        dref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = dref.getRef().child("exam_papers/" + classno1 );
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    Storagefiles img = snapshot.getValue(Storagefiles.class);
                    paperlist.add(img);
                }

                //Init adapter
                adapter = new StoragefilesAdapter(ExamActivity.this, R.layout.storagefiles_list_item, paperlist, "exam_papers", classno1);

                //Set adapter for listview
                adapter.notifyDataSetChanged();
                lv_exam.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });


    }

}
