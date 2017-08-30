package ekalschoolapp.pooja.com.ekalschoolapp;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Puja on 2/19/2017.
 */

public class FirebaseDb  {

    private DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    private StorageReference sref = FirebaseStorage.getInstance().getReference();




    public void getProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    public void updateProfile(String name, String photourlpath)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse(photourlpath))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

    }

    // fetch user info
    public void fetchuser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    // update email address
    public void updateemail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail("user@example.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }

    // update user password
    public void changepassword()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = "SOME-SECURE-PASSWORD";
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });

    }

    // Reset forgotten password
    public void resetpassword()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "user@example.com";

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public void uploadImage()
    {

    }

    public void verifyUser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public void changePassword(String pwd)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = pwd;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    public void deleteUser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
    }

    public void signoutuser()
    {
        FirebaseAuth.getInstance().signOut();
    }
    //**************** User Ends **********************************



    private void getStudent(){

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " students");
                for (DataSnapshot studSnapshot : snapshot.getChildren()) {
                    Studentdetails stud = studSnapshot.getValue(Studentdetails.class);
                    System.out.println(stud.getName() + " - "+ stud.getRollno());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void updateStudentMarks(String childpath, int marks){

        dref.child(childpath).child("marks").setValue(marks);
    }

    private void upadateStudentAttend(String childpath, int attend){

        dref.child(childpath).child("attendance").setValue(attend);
    }

    private void upadateStudentName(String childpath, String name){

        dref.child(childpath).child("name").setValue(name);
    }

    private void upadateStudentRollno(String childpath, int rno){

        dref.child(childpath).child("rollno").setValue(rno);
    }


/*
   public photouploadfull()
   {
       //method to show file chooser

       //this method will upload the file
       private void uploadFile() {

       fbauth = FirebaseAuth.getInstance();
       picname = fbauth.getCurrentUser().getDisplayName();

       //if there is a file to upload
       if (filePath != null) {
           //displaying a progress dialog while upload is going on
           final ProgressDialog progressDialog = new ProgressDialog(this);
           progressDialog.setTitle("Uploading");
           progressDialog.show();

           StorageReference storageRef = mStorageRef.child("profilephoto/"+ picname +".jpg");

           storageRef.putFile(filePath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           //if the upload is successfull
                           //hiding the progress dialog
                           progressDialog.dismiss();

                           //and displaying a success toast
                           Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception exception) {
                           //if the upload is not successfull
                           //hiding the progress dialog
                           progressDialog.dismiss();

                           //and displaying error message
                           Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           //calculating progress percentage
                           double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                           //displaying percentage in progress dialog
                           progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                       }
                   });
       }
       //if there is not any file
       else {
           //you can display an error toast
       }
   }

   }
*/


    //********************** Storage related code***********************8

    public void downloadfile(File filename)
    {
        StorageReference sref = FirebaseStorage.getInstance().getReference();
        File storagePath = new File(Environment.getExternalStorageDirectory(), "Ekal_study_materials");
        // Create direcorty if not exists
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        final File myFile = new File(storagePath, "filename");

        sref.getFile(myFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void getfileurl()
    {
        List<Storagefiles> filename;
        filename = new ArrayList<>();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " files");
                for (DataSnapshot studSnapshot : snapshot.getChildren()) {
                    Storagefiles stud = studSnapshot.getValue(Storagefiles.class);
                    System.out.println(stud.getName() + " - "+ stud.getUrl());
                   // filename.add(stud);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    // Firebase Storage *******************

}
