package is.hi.verkvitinn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

import static android.view.View.*;

public class HomeScreen extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projects;

    private TextView errorText;
    private ListView projectList;
    private Button dbBrowser;
    private SessionManager session;
    private String username;
    private String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        username = user.get(SessionManager.KEY_NAME);

        // admin
        admin = user.get(SessionManager.KEY_ADMIN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.projectService = new ProjectService(projects, null);

        errorText = (TextView) findViewById(R.id.errorText);
        projectList = (ListView) findViewById(R.id.projectList);
        dbBrowser = (Button) findViewById(R.id.getActiveWorkers);

        final CheckBox chbongoing = (CheckBox) findViewById(R.id.chbongoing);
        final CheckBox chbnotstarted = (CheckBox) findViewById(R.id.chbnotstarted);
        final CheckBox chbfinished = (CheckBox) findViewById(R.id.chbfinished);
        chbongoing.setChecked(true);
        chbnotstarted.setChecked(true);
        chbfinished.setChecked(true);

        Button createProject = (Button)findViewById(R.id.createProjectButton);
        Button createGroup = (Button)findViewById(R.id.button2);
        Button getActiveWorkers = (Button)findViewById(R.id.getActiveWorkers);

        if(!admin.equals("admin")){
            createProject.setVisibility(GONE);
            createGroup.setVisibility(GONE);
            getActiveWorkers.setVisibility(GONE);
        }
        else{
            createProject.setVisibility(VISIBLE);
            createGroup.setVisibility(VISIBLE);
            getActiveWorkers.setVisibility(VISIBLE);
        }

        // Display projects
        displayProjects(true, true, true);

        // Set up DB browser
        final Activity r = this;
        dbBrowser.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent(r, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        chbfinished.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayProjects(chbongoing.isChecked(), chbnotstarted.isChecked(), chbfinished.isChecked());
            }
        });

        chbnotstarted.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayProjects(chbongoing.isChecked(), chbnotstarted.isChecked(), chbfinished.isChecked());
            }
        });

        chbongoing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayProjects(chbongoing.isChecked(), chbnotstarted.isChecked(), chbfinished.isChecked());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        displayProjects(true, true, true);
    }
    public void createProject(View view) {
        Intent intent = new Intent(this, CreateProject.class);
        startActivity(intent);
    }

    public void createWorkerGroup(View view){
        Intent intent = new Intent(this, WorkerGroup.class);
        startActivity(intent);
    }

    private void displayProjects(boolean onGoing, boolean notStarted, boolean finished) {
        if(!onGoing&&!notStarted&&!finished){
            projectList.setVisibility(INVISIBLE);
            errorText.setText("No projects found");
        }
        else{
            projectList.setVisibility(VISIBLE);
            List<Project> foundProjects;
            if(!admin.equals("admin")) {
                foundProjects = projectService.findByUserAndStatus(username, onGoing, notStarted, finished, this);
            }
            else{
                foundProjects = projectService.findByAdminAndStatus(username, onGoing, notStarted, finished, this);
            }
            if (foundProjects != null) {
                ProjectList adapter = new ProjectList(this, android.R.layout.simple_list_item_1, foundProjects);
                projectList.setAdapter(adapter);
                errorText.setText("");
            } else {
                projectList.setVisibility(INVISIBLE);
                errorText.setText("No projects found");
            }
        }
    }
}
