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

public class StudyMaterialActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    DatabaseReference dref;
    private ListView listview;
    private StoragefilesAdapter adapter;
    List<Storagefiles> imgList;
    Button btn_smatclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);

            progressDialog = new ProgressDialog(this);

            listview = (ListView) findViewById(R.id.studymat_listView1);

          //  progressDialog.setMessage("Please wait loading file-list...");
         //   progressDialog.show();

            imgList = new ArrayList<>();


        btn_smatclass = (Button) findViewById(R.id.btn_smatclass);
        btn_smatclass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(StudyMaterialActivity.this, btn_smatclass);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String classno;
                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_smatclass);
                                one.setText("I");
                                Toast.makeText(StudyMaterialActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                showlist(classno);
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_smatclass);
                                two.setText("II");
                                Toast.makeText(StudyMaterialActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                showlist(classno);
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_smatclass);
                                three.setText("III");
                                Toast.makeText(StudyMaterialActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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

    public void showlist(final String classno)
    {
        // to clear listview content
        imgList.clear();

        dref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = dref.getRef().child("study_material/" + classno );
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    Storagefiles img = snapshot.getValue(Storagefiles.class);
                    imgList.add(img);
                }

                //Init adapter
                adapter = new StoragefilesAdapter(StudyMaterialActivity.this, R.layout.storagefiles_list_item, imgList, "study_material", classno);

                //Set adapter for listview
                adapter.notifyDataSetChanged();
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });


    }

}
