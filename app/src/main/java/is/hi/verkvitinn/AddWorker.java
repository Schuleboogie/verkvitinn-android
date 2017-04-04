package is.hi.verkvitinn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import is.hi.verkvitinn.persistence.entities.Group;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.persistence.repositories.WorkerGroupRepository;
import is.hi.verkvitinn.service.ProjectService;
import is.hi.verkvitinn.service.UserService;

public class AddWorker extends AppCompatActivity {
    UserService userService;
    UserRepository userRepository;
    ProjectService projectService;
    ProjectRepository projectRepository;
    MilestoneRepository milestoneRepository;
    private TextView errorText;
    private ListView workerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        final String projectName = getIntent().getStringExtra("projectName");
        final String admin = getIntent().getStringExtra("admin");
        final String projectDescription = getIntent().getStringExtra("projectDescription");
        final String projectLocation = getIntent().getStringExtra("projectLocation");
        final String projectTools = getIntent().getStringExtra("projectTools");
        final String projectEstTime = getIntent().getStringExtra("projectEstTime");
        this.userService= new UserService(userRepository);
        this.projectService= new ProjectService(projectRepository, milestoneRepository);
        errorText = (TextView) findViewById(R.id.errorText);
        workerList = (ListView) findViewById(R.id.workerList);
        List<User> users= userService.findByRole("worker", this);
        ArrayList<User> groupUser = new ArrayList<>();
        ArrayList<String> usernames = (ArrayList<String>) getIntent().getStringArrayListExtra("usersInGroup");
        for(int n =0;n<usernames.size();n++){
            groupUser.add(userService.findByUsername(usernames.get(n), this));
        }
        if(users!=null){
            final AddWorkerList adapter = new AddWorkerList(this, android.R.layout.simple_list_item_1, users, groupUser);
            final Context context = this;
            workerList.setAdapter(adapter);
            Button submitGroupButton = (Button)findViewById(R.id.submitWorkersButton);
            submitGroupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> users= new ArrayList<String>();
                    Boolean [] inProject = adapter.getInProject();
                    for(int n=0;n<inProject.length;n++){
                        if(inProject[n]==true){
                            users.add(((User)adapter.getItem(n)).getUsername());
                        }
                    }
                    String[] userArray = new String[users.size()];
                    for(int n=0;n<users.size();n++){
                        userArray[n]=users.get(n);
                    }

                    String[] workerArray = new String[1];
                    workerArray[0]=users.get(0);

                    Intent intent = new Intent(context, HomeScreen.class);

                    Project newProject=new Project(projectName, admin, projectDescription, projectLocation, projectTools, projectEstTime, null, null, userArray, workerArray, "Not started");
                    ProjectService.create(newProject, context);
                    startActivity(intent);
                }
            });
        }
        else {
            errorText.setText("No groups found");
        }
    }
}