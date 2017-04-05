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
    private Boolean[] asHeadWorker;
    List<User> checked;
    List<User> checkedHeadworkers;
    public static final String PROJECT_ID = "is.hi.verkvitinn.MESSAGE";

    public AddWorkerList(Context context, int resource, List<User> workers, List<User> checked, List<User> checkedHeadworkers) {
        super(context, resource, workers);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checked=checked;
        this.workers = workers;
        this.checkedHeadworkers = checkedHeadworkers;
        inGroup = new Boolean[workers.size()];
        asHeadWorker = new Boolean[workers.size()];
        for(int n=0;n< workers.size();n++){
            inGroup[n]=false;
            asHeadWorker[n]=false;
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
        final CheckBox cb_setWorker = (CheckBox) vi.findViewById(R.id.cb_setWorker);
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
        CheckBox cb_setHeadWorker = (CheckBox) vi.findViewById(R.id.cb_setHeadWorker);
        cb_setHeadWorker.setChecked(isHeadworker(tworkers.getUsername()));
        asHeadWorker[id]=isHeadworker(tworkers.getUsername());
        cb_setWorker.setEnabled(!isHeadworker(tworkers.getUsername()));
        cb_setHeadWorker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(asHeadWorker[id]==true){
                    cb_setWorker.setEnabled(true);
                    inGroup[id]=true;
                    cb_setWorker.setChecked(true);
                    asHeadWorker[id]=false;
                }
                else{
                    cb_setWorker.setChecked(false);
                    inGroup[id]=true;
                    cb_setWorker.setEnabled(false);
                    asHeadWorker[id]=true;
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

    public boolean isHeadworker(String username){
        for(int n=0;n<checkedHeadworkers.size();n++){
            Log.d(checkedHeadworkers.get(n).getUsername(), username);
            if(checkedHeadworkers.get(n).getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }


    public Boolean [] getInProject(){
        return inGroup;
    }

    public Boolean [] getAsHeadWorker(){
        return asHeadWorker;
    }

}
