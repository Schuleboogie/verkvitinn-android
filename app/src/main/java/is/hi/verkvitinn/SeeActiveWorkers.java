package is.hi.verkvitinn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

public class SeeActiveWorkers extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projectRepository;
    MilestoneRepository milestoneRepository;

    private Long projectId;
    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_active_workers);
        Long projectid = Long.parseLong(getIntent().getStringExtra("PROJECT_ID"));
        this.projectId = projectid;
        this.projectService = new ProjectService(projectRepository, milestoneRepository);
        ListView activeWorkerList = (ListView) findViewById(R.id.activeWorkerList);
        ArrayList<String> active = projectService.getOnCall(projectid, this);
        TextView texti = (TextView)findViewById(R.id.texti);
        if(active.size()>0){
            texti.setText("Theese workers are active right now");
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, active);
            activeWorkerList.setAdapter(itemsAdapter);
        }
        else{
            texti.setText("There are no workers active at the moment");
        }
    }
    public void backToProject(View view) {
        Intent intent = new Intent(this, ProjectScreen.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
    }

}
