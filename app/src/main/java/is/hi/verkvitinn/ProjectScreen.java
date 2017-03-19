package is.hi.verkvitinn;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class ProjectScreen extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projects;

    private TextView projectName;

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
        int projectId = intent.getIntExtra(ProjectList.PROJECT_ID, -1);

        projectName = (TextView) findViewById(R.id.tv_projectName);
        Project p = projectService.findOne(Long.valueOf(projectId), this);
        if (p != null) {
            TextView name = (TextView) findViewById(R.id.tv_projectName);
            TextView admin = (TextView) findViewById(R.id.tv_admin);
            TextView description = (TextView) findViewById(R.id.tv_description);
            TextView location = (TextView) findViewById(R.id.tv_location);
            TextView tools = (TextView) findViewById(R.id.tv_tools);
            TextView estTime = (TextView) findViewById(R.id.tv_est_time);
            TextView estStart = (TextView) findViewById(R.id.tv_est_start_time);
            TextView estFinish = (TextView) findViewById(R.id.tv_est_fisish_time);
            TextView status = (TextView) findViewById(R.id.tv_status);


            name.setText(p.getName());
            admin.setText(p.getAdmin());
            description.setText(p.getDescription());
            location.setText(p.getLocation());
            tools.setText(p.getTools());
            estTime.setText(p.getEstTime());
            estStart.setText(p.getStartTime().toString());
            estFinish.setText(p.getFinishTime().toString());
            status.setText(p.getStatus());

            ListView workers = (ListView) findViewById(R.id.workerList);
            final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < p.getWorkers().length; ++i) {
                list.add(p.getWorkers()[i]);
            }
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            workers.setAdapter(adapter);

        }
        else projectName.setText("Project not found");
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
