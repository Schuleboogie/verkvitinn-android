package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class SetMilestone extends AppCompatActivity {
    ProjectService projectService;
    MilestoneRepository milestones;
    ProjectRepository projects;


    private Long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_milestone);
        this.projectService = new ProjectService(projects, milestones);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project id
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(Milestones.PROJECT_ID, -1);
        this.projectId = projectId;
    }
    public void createMilestone(View view) {
        EditText milestoneTitleElement = (EditText) findViewById(R.id.milestoneTitle);
        String milestoneTitle = milestoneTitleElement.getText().toString();
        Milestone newMilestone = new Milestone(this.projectId, new Date(), milestoneTitle);
        projectService.setMilestone(newMilestone, this);
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
