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
import java.util.HashMap;
import java.util.List;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class Milestones extends AppCompatActivity {
    ProjectService projectService;
    MilestoneRepository milestones;
    ProjectRepository projects;
    SessionManager session;
    private String username;
    private String admin;

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

        String[] headworkers = projectService.findOne(projectId, this).getHeadWorkers();

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        username = user.get(SessionManager.KEY_NAME);

        // admin
        admin = user.get(SessionManager.KEY_ADMIN);

        Boolean canAdd=admin.equals("admin");
        if(!canAdd){
            for(int n=0;n<headworkers.length;n++){
                if(headworkers[n].equals(username))
                    canAdd=true;
            }
        }
        Button milestones = (Button) findViewById(R.id.button7);
        if(canAdd)
            milestones.setVisibility(View.VISIBLE);
        else
            milestones.setVisibility(View.INVISIBLE);

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
