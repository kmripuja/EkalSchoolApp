package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Puja on 3/17/2017.
 */


public class Student_listAdapter extends ArrayAdapter<Studentdetails> {
    private Activity context;
    private int resource;
    private List<Studentdetails> liststud;

    private LayoutInflater mInflater;

    public Student_listAdapter(@NonNull FragmentActivity context, @LayoutRes int resource, @NonNull List<Studentdetails> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        liststud = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tv_itemname = (TextView) v.findViewById(R.id.tv_itemname);
        TextView tv_itemrollno = (TextView) v.findViewById(R.id.tv_itemrollno);

        tv_itemname.setText(liststud.get(position).getName());
        tv_itemrollno.setText(liststud.get(position).getRollno());

        return v;
    }
}
