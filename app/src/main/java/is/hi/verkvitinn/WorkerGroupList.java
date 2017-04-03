package is.hi.verkvitinn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Group;
import is.hi.verkvitinn.persistence.entities.User;


/**
 * Created by skulii on 17.3.2017.
 */

public class WorkerGroupList extends ArrayAdapter {
    List<Group> groups;
    private static LayoutInflater inflater = null;
    private Boolean[] inProject;
    public static final String PROJECT_ID = "is.hi.verkvitinn.MESSAGE";

    public WorkerGroupList(Context context, int resource, List<Group> groups) {
        super(context, resource, groups);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groups = groups;
        inProject = new Boolean[groups.size()];
        for(int n=0;n< groups.size();n++){
            inProject[n]=false;
        }
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return groups.get(position);
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
            vi = inflater.inflate(R.layout.workergrouplistitem, null);
        // Set information
        TextView workerName = (TextView) vi.findViewById(R.id.wName);
        final Group tgroup = groups.get(i);
        workerName.setText(tgroup.getName());
        final int id = i;
        CheckBox cb_setWorkerGroup = (CheckBox) vi.findViewById(R.id.cb_setWorker);
        cb_setWorkerGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inProject[id]==true){
                    inProject[id]=false;
                }
                else{
                    inProject[id]=true;
                }
            }
        });
        return vi;
    }

    public Boolean [] getInProject(){
        return inProject;
    }
}
