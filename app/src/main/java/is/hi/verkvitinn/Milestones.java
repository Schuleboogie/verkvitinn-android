package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class Milestones extends AppCompatActivity {
    ProjectService projectService;
    MilestoneRepository milestones;
    ProjectRepository projects;

    private Long projectId;
    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);
        this.projectService = new ProjectService(projects, milestones);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project info
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(ProjectScreen.PROJECT_ID, -1);
        this.projectId = projectId;

        List<Milestone> foundMilestones = projectService.findMilestones(projectId, this);
        if (foundMilestones != null) {
            ListView milestoneList = (ListView) findViewById(R.id.milestoneList);
            MilestoneList adapter = new MilestoneList(this, android.R.layout.simple_list_item_1, foundMilestones);
            milestoneList.setAdapter(adapter);
        }
    }
    public void setMilestone(View view) {
        Intent intent = new Intent(this, SetMilestone.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
