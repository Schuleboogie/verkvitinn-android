package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class ProjectScreen extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projects;

    private TextView projectName;
    public static final String PROJECT_EDIT = "is.hi.verkvitinn.PROJECT_EDIT";
    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";
    private Long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_screen);
        this.projectService = new ProjectService(projects, null);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project info
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(ProjectList.PROJECT_ID, -1);
        this.projectId = projectId;

        projectName = (TextView) findViewById(R.id.projectName);
        Project foundProject = projectService.findOne(projectId, this);
        if (foundProject != null) {
            projectName.setText(foundProject.getName());

        }
        else projectName.setText("Project not found");
    }
    public void editProject(View view) {
        Intent intent = new Intent(this, CreateProject.class);
        Bundle extras = new Bundle();
        extras.putString(PROJECT_EDIT, "true");
        extras.putString(PROJECT_ID, String.valueOf(projectId));
        intent.putExtras(extras);
        startActivity(intent);
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
