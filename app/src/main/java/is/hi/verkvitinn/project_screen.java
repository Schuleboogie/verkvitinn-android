package is.hi.verkvitinn;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;
import android.widget.ArrayAdapter;

public class project_screen extends AppCompatActivity {

    ProjectService projectService;
    ProjectRepository projects;
    MilestoneRepository milestones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Get intent, action and MIME type
        //Intent intent = getIntent();

        //String s = getIntent().getStringExtra("EXTRA_SESSION_ID");

        //Long projectId = Long.parseLong(s);
        this.projectService = new ProjectService(projects, milestones);
        Long l = Long.valueOf(1);
        Project p = projectService.findOne(l, this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_screen);

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
}
