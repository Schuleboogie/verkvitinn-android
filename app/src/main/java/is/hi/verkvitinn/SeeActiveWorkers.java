package is.hi.verkvitinn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class SeeActiveWorkers extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projectRepository;
    MilestoneRepository milestoneRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_active_workers);
        Long projectid = Long.parseLong(getIntent().getStringExtra("PROJECT_ID"));
        this.projectService = new ProjectService(projectRepository, milestoneRepository);
        ListView activeWorkerList = (ListView) findViewById(R.id.activeWorkerList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectService.getOnCall(projectid, this));
        activeWorkerList.setAdapter(itemsAdapter);
    }

}
