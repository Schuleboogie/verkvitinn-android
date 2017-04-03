package is.hi.verkvitinn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hi.verkvitinn.persistence.entities.Group;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.WorkerGroupRepository;

public class AddWorkerGroup extends AppCompatActivity {
    WorkerGroupRepository workerGroupRepository;
    private TextView errorText;
    private ListView groupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.workerGroupRepository=new WorkerGroupRepository();
        final String projectName = getIntent().getStringExtra("projectName");
        final String admin = getIntent().getStringExtra("admin");
        final String projectDescription = getIntent().getStringExtra("projectDescription");
        final String projectLocation = getIntent().getStringExtra("projectLocation");
        final String projectTools = getIntent().getStringExtra("projectTools");
        final String projectEstTime = getIntent().getStringExtra("projectEstTime");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker_group);
        errorText = (TextView) findViewById(R.id.errorText);
        groupList = (ListView) findViewById(R.id.groupList);
        List<Group> groups= workerGroupRepository.findAll(this);
        if(groups!=null){
            final AddWorkerGroupList adapter = new AddWorkerGroupList(this, android.R.layout.simple_list_item_1, groups);
            final Context context = this;
            groupList.setAdapter(adapter);
            Button submitGroupButton = (Button)findViewById(R.id.submitGroupsButton);
            submitGroupButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ArrayList<String> users= new ArrayList<String>();
                    Boolean [] inProject = adapter.getInProject();
                    for(int n=0;n<inProject.length;n++){
                        if(inProject[n]==true){
                            ArrayList<User> userInGroup= ((Group)adapter.getItem(n)).getWorkers();
                            for(int m=0;m<userInGroup.size();m++){
                                users.add(userInGroup.get(m).getUsername());
                                Log.d(userInGroup.get(m).getUsername(), "nafn");
                            }
                        }
                    }
                    Intent intent = new Intent(context, AddWorker.class);
                    intent.putStringArrayListExtra("usersInGroup", users);
                    intent.putExtra("projectName", projectName);
                    intent.putExtra("admin", admin);
                    intent.putExtra("projectDescription", projectDescription);
                    intent.putExtra("projectLocation", projectLocation);
                    intent.putExtra("projectTools", projectTools);
                    intent.putExtra("projectEstTime", projectEstTime);
                    startActivity(intent);
                }
            });
        }
        else {
            errorText.setText("No groups found");
        }
    }
}








  /*


}*/
