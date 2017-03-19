package is.hi.verkvitinn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.projectService = new ProjectService(projects, null);

        errorText = (TextView) findViewById(R.id.errorText);
        projectList = (ListView) findViewById(R.id.projectList);
        dbBrowser = (Button) findViewById(R.id.getActiveWorkers);

        // Display projects
        displayProjects();

        // Set up DB browser
        final Activity r = this;
        dbBrowser.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent(r, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        displayProjects();
    }
    public void createProject(View view) {
        Intent intent = new Intent(this, CreateProject.class);
        startActivity(intent);
    }
    private void displayProjects() {
        List<Project> foundProjects = projectService.findByAdmin("Skúli Ingvarsson", this);
        if (foundProjects != null) {
            ProjectList adapter = new ProjectList(this, android.R.layout.simple_list_item_1, foundProjects);
            projectList.setAdapter(adapter);
        }
        else {
            errorText.setText("No projects found");
        }
    }
}
