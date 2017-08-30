package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadFilesActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_classadmin, btn_uploadfile, btn_choose, btn_uploadtype;
    EditText etfilename;
    String classno;
    String uploadtype;
    private StorageReference sref;
    private DatabaseReference dref;
    String filename;
    private Uri filepath;
    private int PICK_IMAGE_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files);

        sref = FirebaseStorage.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference();

        btn_classadmin = (Button) findViewById(R.id.btn_classadmin);
        btn_uploadtype = (Button) findViewById(R.id.btn_uploadtype);
        btn_uploadfile = (Button) findViewById(R.id.btn_uploadfile);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        etfilename = (EditText) findViewById(R.id.etfilename);

        btn_classadmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(UploadFilesActivity.this, btn_classadmin);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.classoption_menu1, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                Button one = (Button) findViewById(R.id.btn_classadmin);
                                one.setText("I");
                                Toast.makeText(UploadFilesActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-1";
                                break;

                            case R.id.two:
                                Button two = (Button) findViewById(R.id.btn_classadmin);
                                two.setText("II");
                                Toast.makeText(UploadFilesActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-2";
                                break;


                            case R.id.three:
                                Button three = (Button) findViewById(R.id.btn_classadmin);
                                three.setText("III");
                                Toast.makeText(UploadFilesActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                classno = "class-3";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        btn_uploadtype.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(UploadFilesActivity.this, btn_uploadtype);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.uploadtype_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.exam_papers:
                                Button one = (Button) findViewById(R.id.btn_uploadtype);
                                one.setText("Exam-Papers");
                                Toast.makeText(UploadFilesActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                uploadtype = "exam_papers";
                                break;

                            case R.id.study_material:
                                Button two = (Button) findViewById(R.id.btn_uploadtype);
                                two.setText("Study-Material");
                                Toast.makeText(UploadFilesActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                uploadtype = "study_material";
                                break;

                        }

                        return true;


                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        //attaching listener
        btn_uploadfile.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
    }


    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getfileextention(String path)
    {
        String extention = path.substring(path.lastIndexOf(".") );
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return extention;
    }


    public void uploadFile() {

        filename = etfilename.getText().toString()+getfileextention(filepath.toString()) ;
        System.out.println("uploadtype : " + uploadtype + "classno : " + classno + " filename : " + filename);
        //checking if file is available
        if (filepath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = sref.child(uploadtype + "/" + classno + "/" + filename );

            //adding the file to reference
            sRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            Storagefiles upload = new Storagefiles(filename, taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
                            String uploadId = dref.child(uploadtype + "/" + classno).push().getKey();
                            dref.child(uploadtype + "/" + classno + "/" +uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }

    @Override
    public void onClick(View view) {

        //if the clicked button is choose
        if (view == btn_uploadfile) {
            uploadFile();
        } else if (view == btn_choose) {
            showFileChooser();
        }
    }
}
