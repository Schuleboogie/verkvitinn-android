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
import android.widget.Toast;

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
        final String projectId = getIntent().getStringExtra("ProjectId");
        Log.d("in addworker", projectId);
        this.userService= new UserService(userRepository);
        this.projectService= new ProjectService(projectRepository, milestoneRepository);
        ArrayList<User> groupUser = new ArrayList<>();
        ArrayList<User> groupHeadworkers = new ArrayList<>();
        if(!projectId.equals("")){
            Project project = projectService.findOne(Long.parseLong(projectId), this);
            String[] headworkers = project.getHeadWorkers();
            String [] workers = project.getWorkers();

            for(int n=0;n<workers.length;n++){
                groupUser.add(userService.findByUsername(workers[n], this));
            }
            for(int n=0;n<headworkers.length;n++){
                groupHeadworkers.add(userService.findByUsername(headworkers[n],this));
            }
        }
        errorText = (TextView) findViewById(R.id.errorText);
        workerList = (ListView) findViewById(R.id.workerList);
        List<User> users= userService.findByRole("worker", this);
        ArrayList<String> usernames =  getIntent().getStringArrayListExtra("usersInGroup");
        for(int n =0;n<usernames.size();n++){
            groupUser.add(userService.findByUsername(usernames.get(n), this));
        }

        if(users!=null){
            final AddWorkerList adapter = new AddWorkerList(this, android.R.layout.simple_list_item_1, users, groupUser, groupHeadworkers);
            final Context context = this;
            workerList.setAdapter(adapter);
            Button submitGroupButton = (Button)findViewById(R.id.submitWorkersButton);
            submitGroupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> users= new ArrayList<String>();
                    Boolean [] inProject = adapter.getInProject();
                    ArrayList<String> hworkers= new ArrayList<String>();
                    for(int n=0;n<inProject.length;n++){
                        if(inProject[n]==true){
                            users.add(((User)adapter.getItem(n)).getUsername());
                        }
                    }
                    String[] userArray = new String[users.size()];
                    for(int n=0;n<users.size();n++){
                        userArray[n]=users.get(n);
                    }
                    Boolean [] asHeadworker = adapter.getAsHeadWorker();
                    for(int n=0;n<inProject.length;n++){
                        if(asHeadworker[n]==true){
                            hworkers.add(((User)adapter.getItem(n)).getUsername());
                        }
                    }

                    String[] hworkerarray = new String[hworkers.size()];
                    for(int n=0;n<hworkers.size();n++){
                        hworkerarray[n]=hworkers.get(n);
                    }
                    String[] workerArray = new String[1];
                    workerArray[0]=users.get(0);

                    Intent intent = new Intent(context, HomeScreen.class);
                    Toast.makeText(context, "Project created", Toast.LENGTH_SHORT).show();

                    //should open the project screen
                    Project newProject=new Project(projectName, admin, projectDescription, projectLocation, projectTools, projectEstTime, null, null, userArray, hworkerarray, "Not started");
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
