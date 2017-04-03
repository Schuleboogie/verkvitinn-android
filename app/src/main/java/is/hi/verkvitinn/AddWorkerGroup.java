package is.hi.verkvitinn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hi.verkvitinn.persistence.entities.Group;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.persistence.repositories.WorkerGroupRepository;
import is.hi.verkvitinn.service.UserService;

public class AddWorkerGroup extends AppCompatActivity {
    WorkerGroupRepository workerGroupRepository;
    private TextView errorText;
    private ListView groupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.workerGroupRepository=new WorkerGroupRepository();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker_group);
        errorText = (TextView) findViewById(R.id.errorText);
        groupList = (ListView) findViewById(R.id.groupList);
        List<Group> groups= workerGroupRepository.findAll(this);
        System.out.println(groups.size()+ " fjöldi hópa");
        if(groups!=null){
            final WorkerGroupList adapter = new WorkerGroupList(this, android.R.layout.simple_list_item_1, groups);
            final Context context = this;
            groupList.setAdapter(adapter);
            Button submitGroupButton = (Button)findViewById(R.id.submitGroupsButton);
            submitGroupButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ArrayList<Group> groups= new ArrayList<Group>();
                    Boolean [] inProject = adapter.getInProject();
                    for(int n=0;n<inProject.length;n++){
                        if(inProject[n]==true){
                            groups.add((Group)adapter.getItem(n));
                            Log.d(((Group) adapter.getItem(n)).getName(), "nafnið á hópnum sem var valinn");
                        }
                    }

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
