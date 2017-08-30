package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

/**
 * Created by Puja on 3/15/2017.
 */

public class StoragefilesAdapter extends ArrayAdapter<Storagefiles> {

    private Activity context;
    private int resource;
    private List<Storagefiles> listImage;
    String downloadtype;
    String classnumber;

    private LayoutInflater mInflater;

    public StoragefilesAdapter(@NonNull FragmentActivity context, @LayoutRes int resource, @NonNull List<Storagefiles> objects, String type, String classnum) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
        downloadtype = type;
        classnumber = classnum;
    }

    static class ViewHolder {
        ImageView downloadfile;
        TextView txfilename;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
           convertView = inflater.inflate(resource, null);
            holder.txfilename = (TextView) convertView.findViewById(R.id.tv_downloaditem);
            holder.downloadfile = (ImageView) convertView.findViewById(R.id.iv_downloaditem);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

     //   System.out.println("file name = " + listImage.get(position).getName() + "file url = " +  listImage.get(position).getUrl());

        final Storagefiles filelist = listImage.get(position);
            holder.txfilename.setText(filelist.getName());

        holder.downloadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ref1 = FirebaseStorage.getInstance().getReference();
                StorageReference sref = ref1.child(downloadtype + "/" + classnumber + "/" + filelist.getName() );
                File storagePath = new File(Environment.getExternalStorageDirectory(), "Ekal_study_materials/"+downloadtype);
                // Create direcorty if not exists
                if (!storagePath.exists()) {
                    storagePath.mkdirs();
                }

                final File myFile = new File(storagePath, filelist.getName());
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Downloading..");
                progressDialog.show();


                sref.getFile(myFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Log.e("firebase ", ";local tem file created  created " + myFile.toString());
                        Toast.makeText(context.getApplicationContext(), "File Download Complete!! " , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                        progressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot >() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Downloaded " + ((int) progress) + "%...");
                    }
                });
                holder.downloadfile.setTag(filelist);

            }

    });
        return convertView;
    }

}