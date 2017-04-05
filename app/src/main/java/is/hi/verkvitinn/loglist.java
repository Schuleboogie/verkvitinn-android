package is.hi.verkvitinn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.R;
import is.hi.verkvitinn.persistence.entities.User;


/**
 * Created by skulii on 17.3.2017.
 */

public class loglist extends ArrayAdapter {
    List<User> workers;
    private static LayoutInflater inflater = null;
    private Boolean[] inGroup;
    List<String[]> logs;
    public static final String PROJECT_ID = "is.hi.verkvitinn.MESSAGE";

    public loglist(Context context, int resource, List<String[]> logs) {
        super(context, resource, logs);
        this.logs=logs;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int i, View view, ViewGroup vg) {
        // TODO Auto-generated method stub
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.loglist, null);
        // Set information
        TextView username = (TextView) vi.findViewById(R.id.username);
        TextView projectname = (TextView) vi.findViewById(R.id.projectname);
        TextView date = (TextView) vi.findViewById(R.id.date);
        TextView hours = (TextView) vi.findViewById(R.id.hours);
        username.setText(logs.get(i)[0]);
        projectname.setText(logs.get(i)[1]);
        hours.setText(logs.get(i)[2]);
        date.setText(logs.get(i)[3]);
        return vi;
    }

    public Boolean [] getInGroup(){
        return inGroup;
    }

}
