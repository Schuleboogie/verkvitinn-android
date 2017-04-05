package is.hi.verkvitinn;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Log;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.ProjectService;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ProjectScreen extends AppCompatActivity {
    ProjectService projectService;
    ProjectRepository projects;

    private TextView projectName;
    private Button startProjectButton;
    private Button finishProjectButton;
    private Button seeActiveButton;
    private Long projectId;
    private SessionManager session;
    private String adminString;
    private String username;


    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";
    public static final String PROJECT_EDIT = "is.hi.verkvitinn.PROJECT_EDIT";
    public static final String PROJECT_LOCATION = "is.hi.verkvitinn.PROJECT_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        adminString = user.get(SessionManager.KEY_ADMIN);
        username = user.get(SessionManager.KEY_NAME);



        setContentView(R.layout.activity_project_screen);
        this.projectService = new ProjectService(projects, null);


        Button editProjectButton = (Button) findViewById(R.id.editProjectButton);

        if(!adminString.equals("admin")){
            editProjectButton.setVisibility(INVISIBLE);
        }
        else{
            editProjectButton.setVisibility(VISIBLE);
        }

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project info
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(ProjectList.PROJECT_ID, -1);
        this.projectId = projectId;

        projectName = (TextView) findViewById(R.id.tv_projectName);
        startProjectButton = (Button) findViewById(R.id.startProjectButton);
        finishProjectButton = (Button) findViewById(R.id.finishProjectButton);
        seeActiveButton = (Button)findViewById(R.id.seeActive);

        Project p = projectService.findOne(projectId, this);
        if (p != null) {
            TextView name = (TextView) findViewById(R.id.tv_projectName);
            TextView admin = (TextView) findViewById(R.id.tv_admin);
            TextView description = (TextView) findViewById(R.id.tv_description);
            TextView location = (TextView) findViewById(R.id.tv_location);
            TextView tools = (TextView) findViewById(R.id.tv_tools);
            TextView estTime = (TextView) findViewById(R.id.tv_est_time);
            /*TextView estStart = (TextView) findViewById(R.id.tv_est_start_time);
            TextView estFinish = (TextView) findViewById(R.id.tv_est_finish_time);*/
            TextView status = (TextView) findViewById(R.id.tv_status);

            String[] headworkers = p.getHeadWorkers();

            Boolean canAdd=adminString.equals("admin");

            if(!canAdd){
                for(int n=0;n<headworkers.length;n++){
                    if(headworkers[n].equals(username))
                        canAdd=true;
                }
            }

            Button checkin = (Button)findViewById(R.id.checkin);
            Button checkout = (Button)findViewById(R.id.checkout);

            Boolean shouldCheckout = projectService.shouldCheckout(username, projectId, this);
            if(adminString.equals("admin")){
                checkin.setVisibility(GONE);
                checkout.setVisibility(GONE);
            }
            else if(shouldCheckout){
                checkout.setVisibility(VISIBLE);
                checkin.setVisibility(GONE);
            }
            else{
                checkout.setVisibility(GONE);
                checkin.setVisibility(VISIBLE);
            }

            name.setText(p.getName());
            admin.setText(p.getAdmin());
            description.setText(p.getDescription());
            location.setText(p.getLocation());
            tools.setText(p.getTools());
            estTime.setText(p.getEstTime());
            /*estStart.setText(p.getStartTime().toString());
            estFinish.setText(p.getFinishTime().toString());*/
            status.setText(p.getStatus());
            if (p.getStatus().equals("In progress")) {
                startProjectButton.setVisibility(GONE);
                finishProjectButton.setVisibility(VISIBLE);
            }
            else if (p.getStatus().equals("Finished")) {
                startProjectButton.setVisibility(GONE);
            }
            if(!canAdd){
                startProjectButton.setVisibility(GONE);
                finishProjectButton.setVisibility(GONE);
                seeActiveButton.setVisibility(INVISIBLE);
            }


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
    public void editProject(View view) {
        Intent intent = new Intent(this, CreateProject.class);
        Bundle extras = new Bundle();
        extras.putString(PROJECT_ID, "" + this.projectId);
        extras.putString(PROJECT_EDIT, "true");
        intent.putExtras(extras);
        this.startActivity(intent);
    }
    public void openMap(View view) {
        Intent intent = new Intent(this, MapLookup.class);
        TextView tvLocation = (TextView) findViewById(R.id.tv_location);
        String projectLocation = tvLocation.getText().toString();
        intent.putExtra(PROJECT_LOCATION, projectLocation);
        startActivity(intent);
    }
    public void openComments(View view) {
        Intent intent = new Intent(this, Comments.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
    }
    public void seeMilestones(View view) {
        Intent intent = new Intent(this, Milestones.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
    }

    public void startProject(View view) {
        if (projectService.startProject(this.projectId, this)) {
            TextView status = (TextView) findViewById(R.id.tv_status);
            status.setText("In progress");
            // Show finish project button
            finishProjectButton.setVisibility(VISIBLE);
        }
        else Toast.makeText(this, "Project could not be started", Toast.LENGTH_SHORT).show();
    }

    public void checkin(View view){
        Button checkin = (Button)findViewById(R.id.checkin);
        Button checkout = (Button)findViewById(R.id.checkout);
        checkout.setVisibility(VISIBLE);
        checkin.setVisibility(GONE);
        System.out.println(new Date());
        Log newlog = new Log(projectId, new Date(), null, username);
        projectService.addToLog(newlog, this);
    }

    public void checkout(View view){
        Button checkin = (Button)findViewById(R.id.checkin);
        Button checkout = (Button)findViewById(R.id.checkout);
        checkout.setVisibility(GONE);
        checkin.setVisibility(VISIBLE);
        Log updatedLog = new Log(projectId, null, new Date(), username);
        projectService.updateLog(updatedLog, this);
    }

    public void seeActive(View view){
        Intent intent = new Intent(this, SeeActiveWorkers.class);
        intent.putExtra("PROJECT_ID",  "" + this.projectId);
        startActivity(intent);
    }

    public void finishProject(View view) {
        if (projectService.finishProject(this.projectId, this)) {
            Toast.makeText(this, "Project finished", Toast.LENGTH_SHORT).show();
            TextView status = (TextView) findViewById(R.id.tv_status);
            status.setText("Finished");
            // Hide finish project button
            finishProjectButton.setVisibility(GONE);
        }
        else Toast.makeText(this, "Project could not be finished", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(ProjectList.PROJECT_ID, -1);
        this.projectId = projectId;
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
