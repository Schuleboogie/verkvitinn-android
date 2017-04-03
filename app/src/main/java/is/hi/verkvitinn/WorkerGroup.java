package is.hi.verkvitinn;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class WorkerGroup extends AppCompatActivity {
    UserService userService;
    UserRepository userRepository;
    private TextView errorText;
    private ListView workerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.userService= new UserService(userRepository);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_group);


        errorText = (TextView) findViewById(R.id.errorText);
        workerList = (ListView) findViewById(R.id.workerList);


        List<User> workers= userService.findByRole("worker", this);
        if (workers != null) {
            final WorkerList adapter = new WorkerList(this, android.R.layout.simple_list_item_1, workers);
            final Context context = this;
            workerList.setAdapter(adapter);
            Button submitGroupButton = (Button)findViewById(R.id.submitGroupButton);
            submitGroupButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ArrayList<User> workers= new ArrayList<User>();
                    Boolean [] inGroup = adapter.getInGroup();
                    for(int n=0;n<inGroup.length;n++){
                        if(inGroup[n]==true){
                            workers.add((User)adapter.getItem(n));
                        }
                    }
                    EditText groupName = (EditText) findViewById(R.id.tv_groupName);
                    Group newGroup = new Group(groupName.getText().toString(), workers);
                    for(int n = 0; n<newGroup.getWorkers().size();n++){
                        Log.d(newGroup.getWorkers().get(n).getUsername(), "Nafn workers");
                    }
                    WorkerGroupRepository workerGroupRepository=new WorkerGroupRepository();
                    workerGroupRepository.save(newGroup, context);
                }
            });
        }
        else {
            errorText.setText("No workers found");
        }
    }

}
