package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class RegisterActivity extends Activity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 12 ;
    //defining view objects
    private EditText editTextEmail ;
    private EditText editTextPassword;
    private EditText editTextUsername;
    private Button btn_Register ;
    private ImageView ivprofile;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dref;
    private Uri mUri;
    String villagename;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();

        //initializing views
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        ivprofile = (ImageView) findViewById(R.id.ivprofile);


        btn_Register = (Button) findViewById(R.id.btn_Register);

        progressDialog = new ProgressDialog(this);

/*
        btn_uservillage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(RegisterActivity.this, btn_uservillage);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.villagename_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.vone:
                                Button vone = (Button) findViewById(R.id.btn_uservillage);
                                vone.setText("Chand pur");
                                Toast.makeText(RegisterActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Chand pur";
                                break;

                            case R.id.vtwo:
                                Button vtwo = (Button) findViewById(R.id.btn_uservillage);
                                vtwo.setText("Gandhi Nagar");
                                Toast.makeText(RegisterActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Gandhi Nagar";
                                break;


                            case R.id.vfour:
                                Button vfour = (Button) findViewById(R.id.btn_uservillage);
                                vfour.setText("Nagta");
                                Toast.makeText(RegisterActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                villagename = "Nagta";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
*/


        //attaching listener to button
        btn_Register.setOnClickListener(this);
        ivprofile.setOnClickListener(this);

    }

    private void registerUser(){



        //getting email and password from edit texts
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();
     //   final String state = editTextState.getText().toString().trim();
     //   final String phone = editTextPhone.getText().toString().trim();
        //checking if email and passwords are empty

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        else if(password.length() < 6){
            Toast.makeText(RegisterActivity.this,"Password should be more than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }


     /*   if(TextUtils.isEmpty(villagename)){
            Toast.makeText(this,"Please enter village name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(state)){
            Toast.makeText(this,"Please enter state", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please enter phone no.", Toast.LENGTH_LONG).show();
            return;
        }
*/
        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            final FirebaseUser firebaseUser = task.getResult().getUser();
                            Task<Void> updateTask = firebaseUser.updateProfile(
                                    new UserProfileChangeRequest
                                            .Builder()
                                            .setDisplayName(username)
                                            .setPhotoUri(mUri).build());
                            updateTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                       // startSaveCredentials(firebaseUser, password);
                                    }
                                }
                            });


                            //display some message here
                            Toast.makeText(RegisterActivity.this,"Successfully registered", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        }
                        else{
                                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                    // Password too weak
                                    Toast.makeText(RegisterActivity.this, "Password should be more than 6 characters", Toast.LENGTH_SHORT).show();
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Email address is malformed
                                    Toast.makeText(RegisterActivity.this, "Invalid Email Address", Toast.LENGTH_LONG).show();
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    // Collision with existing user email, it should be very hard for
                                    // the user to even get to this error due to CheckEmailFragment.
                                    Toast.makeText(RegisterActivity.this, "User Already Exist", Toast.LENGTH_LONG).show();
                                } else {
                                    // General error message, this branch should not be invoked but
                                    // covers future API changes
                                    Toast.makeText(RegisterActivity.this, "Registration Error" , Toast.LENGTH_LONG).show();

                                }
                                progressDialog.dismiss();
                            }

                     }
                });

    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                ivprofile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view == ivprofile) {
            showFileChooser();
        }
        //calling register method on click
        registerUser();


    }
}
