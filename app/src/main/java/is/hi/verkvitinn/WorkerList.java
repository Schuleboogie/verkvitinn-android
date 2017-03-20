package is.hi.verkvitinn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.entities.User;


/**
 * Created by skulii on 17.3.2017.
 */

public class WorkerList extends ArrayAdapter {
    List<User> workers;
    private static LayoutInflater inflater = null;
    public static final String PROJECT_ID = "is.hi.verkvitinn.MESSAGE";

    public WorkerList(Context context, int resource, List<User> workers) {
        super(context, resource, workers);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.workers = workers;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return workers.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return workers.get(position);
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
            vi = inflater.inflate(R.layout.workerlistitem, null);
        // Set information
        TextView workerName = (TextView) vi.findViewById(R.id.wName);
        TextView workerUName = (TextView) vi.findViewById(R.id.wUName);
        final User tworkers = workers.get(i);
        workerName.setText(tworkers.getName());
        workerUName.setText(tworkers.getUsername());
        return vi;
    }
}
