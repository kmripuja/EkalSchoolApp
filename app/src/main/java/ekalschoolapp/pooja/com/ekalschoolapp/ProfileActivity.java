package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity  {

    //a Uri object to store file path
    private Uri filePath;
    private ImageView ivprofile;
    private StorageReference mStorageRef;
    FirebaseAuth fbauth;
    private TextView showname;
    private TextView editVillageName;
    private TextView editState;
    private TextView editphone;
    private TextView showemail;
    FirebaseUser curuser;
    String uvillage, ustate;
    int usphone;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        showname = (TextView) findViewById(R.id.tv_showname);
     //   editVillageName = (TextView) findViewById(R.id.et_village1);
 //       editState = (TextView) findViewById(R.id.et_state1);
  //      editphone = (TextView) findViewById(R.id.etphone1) ;
        showemail = (TextView) findViewById(R.id.tv_showemail);

        ivprofile = (ImageView) findViewById(R.id.ivprofile);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        dref = FirebaseDatabase.getInstance().getReference();

        curuser  = FirebaseAuth.getInstance().getCurrentUser();
        ivprofile.setImageURI(curuser.getPhotoUrl());
        showname.setText(curuser.getDisplayName());
        showemail.setText(curuser.getEmail());


        dref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = dref.child("profile/" + curuser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
//                uvillage = user.getVillage();
//                ustate = user.getState();
//                usphone =user.getPhone();
              //  editVillageName.setText(uvillage);
//                editState.setText(ustate);
 //              editphone.setText(String.valueOf(usphone));
             //   System.out.println(uvillage + " " + ustate + " " + usphone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    /*    editVillageName.setOnLongClickListener(
                new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        dialogedit(editVillageName,  "village");
                        return false;
                    }
                });

        editState.setOnLongClickListener(
                new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        dialogedit(editState, "state");
                        return false;
                    }
                });

        editphone.setOnLongClickListener(
                new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        dialogeditphone(editphone, "phone");
                        return false;
                    }
                });
*/

    }


    public void dialogedit(final TextView editval, final String userinfo)
    {
        final String[] val = new String[1];
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(ProfileActivity.this);
        View promptsView = li.inflate(R.layout.update_profile, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ProfileActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.et_value);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                // get user input and set it to
                                // result
                                // edit text
                                val[0] = userInput.getText().toString();
                                editval.setText(val[0]);
                                DatabaseReference ref = dref.child("student/" + userinfo ).getRef();
                                dref.child("profile/" + curuser.getUid() + "/" + userinfo ).setValue(val[0]);
                                final DatabaseReference newref = dref.child("student/" + val[0]).getRef();
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        newref.setValue(dataSnapshot.getValue());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                ref.removeValue();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }


    public void dialogeditphone(final TextView editval, final String userinfo)
    {
        final String[] val = new String[1];
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(ProfileActivity.this);
        View promptsView = li.inflate(R.layout.update_profile, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ProfileActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.et_value);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                // get user input and set it to
                                // result
                                // edit text
                                val[0] =userInput.getText().toString();
                                editval.setText(val[0]);
                                DatabaseReference ref = dref.child("student/" + userinfo ).getRef();
                                dref.child("profile/" + curuser.getUid() + "/" + userinfo ).setValue(Integer.parseInt(val[0]));
                                final DatabaseReference newref = dref.child("student/" + Integer.parseInt(val[0])).getRef();
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        newref.setValue(dataSnapshot.getValue());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                ref.removeValue();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }


   /* public void saveprofile() {

       // uvillage = editVillageName.getText().toString();
      //  ustate = editState.getText().toString();
       // uphone = Integer.parseInt(editPhone.getText().toString());



        User user = new User(curuser.getDisplayName(),curuser.getEmail(),curuser.getPhotoUrl().toString(), uvillage, ustate,usphone);
        dref.child("profile/" + curuser.getUid()).setValue(user);
    }  */


}
