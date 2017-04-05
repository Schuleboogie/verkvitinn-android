package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class CreateProject extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projects;

    private EditText projectNameField;
    private EditText projectDescriptionField;
    private EditText projectLocationField;
    private EditText projectToolsField;
    private EditText projectEstTimeField;
    private String admin;
    private String username;
    private SessionManager session;
    private String editMode;
    private Long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        username = user.get(SessionManager.KEY_NAME);


        setContentView(R.layout.activity_create_project);
        this.projectService = new ProjectService(projects, null);

        projectNameField = (EditText) findViewById(R.id.tv_projectName);
        projectDescriptionField = (EditText) findViewById(R.id.projectDescription);
        projectLocationField = (EditText) findViewById(R.id.projectLocation);
        projectToolsField = (EditText) findViewById(R.id.projectTools);
        projectEstTimeField = (EditText) findViewById(R.id.projectEstTime);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Set project info if edit mode is on
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            editMode = extras.getString(ProjectScreen.PROJECT_EDIT);
            String projectId = extras.getString(ProjectScreen.PROJECT_ID);
            if (editMode.equals("true")) {
                // Find project
                Project foundProject = projectService.findOne(Long.valueOf(projectId), this);
                if (foundProject != null) {
                    this.projectId = Long.valueOf(projectId);
                    projectNameField.setText(foundProject.getName());
                    projectDescriptionField.setText(foundProject.getDescription());
                    projectLocationField.setText(foundProject.getLocation());
                    projectToolsField.setText(foundProject.getTools());
                    projectEstTimeField.setText(foundProject.getEstTime());
                }
            }
        }
        else this.projectId = null;
    }
    // Called when user wants to assign workers
    public void assignWorkers(View view) {
        String projectName = projectNameField.getText().toString();
        String projectDescription = projectDescriptionField.getText().toString();
        String projectLocation = projectLocationField.getText().toString();
        String projectTools = projectToolsField.getText().toString();
        String projectEstTime = projectEstTimeField.getText().toString();
        // Assign admin hér
        String admin = username;
        Intent intent = new Intent(this, AddWorkerGroup.class);
        if(this.projectId!=null){
            intent.putExtra("ProjectId", Long.toString(this.projectId));
        }
        else{
            intent.putExtra("ProjectId", "");
        }
        intent.putExtra("projectName", projectName);
        intent.putExtra("admin", admin);
        intent.putExtra("projectDescription", projectDescription);
        intent.putExtra("projectLocation", projectLocation);
        intent.putExtra("projectTools", projectTools);
        intent.putExtra("projectEstTime", projectEstTime);
        startActivity(intent);
    }

    // Post new project form
    public void createProject(View view) {
        String projectName = projectNameField.getText().toString();
        String projectDescription = projectDescriptionField.getText().toString();
        String projectLocation = projectLocationField.getText().toString();
        String projectTools = projectToolsField.getText().toString();
        String projectEstTime = projectEstTimeField.getText().toString();
        Date projectStartTime = null;
        Date projectFinishTime = null;
        // Ná í workera hér
        String[] projectWorkers = new String[3];
        String[] projectHeadWorkers = new String[3];
        String projectStatus = "Not started";
        // Assign admin hér
        String admin = "Skúli Ingvarsson";
        Project newProject = new Project(projectName, admin, projectDescription, projectLocation, projectTools, projectEstTime, projectStartTime, projectFinishTime, projectWorkers, projectHeadWorkers, projectStatus);
        if (this.projectId != null) { newProject.setId(this.projectId); }
        if (projectService.create(newProject, this) != null)
        {
            // Senda á project screen hér
            if (this.projectId != null) {
                Toast.makeText(this, R.string.project_update_success, Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, R.string.project_create_success, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.project_create_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super.onBackPressed();
                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
