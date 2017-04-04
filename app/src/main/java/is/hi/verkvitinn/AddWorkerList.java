package is.hi.verkvitinn;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.User;


/**
 * Created by skulii on 17.3.2017.
 */

public class AddWorkerList extends ArrayAdapter {
    List<User> workers;
    private static LayoutInflater inflater = null;
    private Boolean[] inGroup;
    List<User> checked;
    public static final String PROJECT_ID = "is.hi.verkvitinn.MESSAGE";

    public AddWorkerList(Context context, int resource, List<User> workers, List<User> checked) {
        super(context, resource, workers);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checked=checked;
        this.workers = workers;
        inGroup = new Boolean[workers.size()];
        for(int n=0;n< workers.size();n++){
            inGroup[n]=false;
        }
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
            vi = inflater.inflate(R.layout.addworkerlistitem, null);
        // Set information
        TextView workerName = (TextView) vi.findViewById(R.id.wName);
        TextView workerUName = (TextView) vi.findViewById(R.id.wUName);
        final User tworkers = workers.get(i);
        workerName.setText(tworkers.getName());
        final int id = i;
        workerUName.setText(tworkers.getUsername());
        CheckBox cb_setWorker = (CheckBox) vi.findViewById(R.id.cb_setWorker);
        cb_setWorker.setChecked(isInGroup(tworkers.getUsername()));
        inGroup[id]=isInGroup(tworkers.getUsername());
        cb_setWorker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inGroup[id]==true){
                    inGroup[id]=false;
                }
                else{
                    inGroup[id]=true;
                }
            }
        });
        return vi;
    }

    public boolean isInGroup(String username){
       for(int n=0;n<checked.size();n++){
           Log.d(checked.get(n).getUsername(), username);
           if(checked.get(n).getUsername().equals(username)){
               return true;
           }
       }
       return false;
    }

    public Boolean [] getInProject(){
        return inGroup;
    }
}