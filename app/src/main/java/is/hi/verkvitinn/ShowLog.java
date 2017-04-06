package is.hi.verkvitinn;

import android.icu.text.SymbolTable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Log;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.service.ProjectService;
import is.hi.verkvitinn.service.UserService;
import android.widget.AdapterView.OnItemSelectedListener;

public class ShowLog extends AppCompatActivity {
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private MilestoneRepository milestoneRepository;
    private UserService userService;
    private UserRepository userRepository;
    private SessionManager session;
    private String username;
    private String searchedUser="";
    private String searchedProject="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.projectService = new ProjectService(projectRepository, milestoneRepository);
        userService = new UserService(userRepository);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);



        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        username = user.get(SessionManager.KEY_NAME);



        Spinner workerspinner = (Spinner)findViewById(R.id.workerspinner);
        List<User> users= userService.findByRole("worker", this);
        ArrayList<String> userNames= new ArrayList<>();
        userNames.add("All workers");
        for(int n=0;n<users.size();n++){
            userNames.add(users.get(n).getUsername());
        }
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userNames);

        workerspinner.setAdapter(spinnerArrayAdapter);

        workerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                if(!adapter.getSelectedItem().toString().equals("All workers")){
                    searchedUser=adapter.getSelectedItem().toString();
                    showList(searchedUser, searchedProject);
                }
                else{
                    searchedUser="";
                    showList(searchedUser, searchedProject);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        List<Project> adminProjets = projectService.findByAdmin(username,this);
        ArrayList<String> projectNames= new ArrayList<>();
        projectNames.add("All projects");
        for(int n=0;n<adminProjets.size();n++){
            projectNames.add(adminProjets.get(n).getName());
        }
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, projectNames);

        Spinner projecSpinner = (Spinner)findViewById(R.id.projecspinner);
        projecSpinner.setAdapter(spinnerArrayAdapter2);

        projecSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                if(!adapter.getSelectedItem().toString().equals("All projects")){
                    searchedProject=adapter.getSelectedItem().toString();
                    showList(searchedUser, searchedProject);
                }
                else{
                    searchedProject="";
                    showList(searchedUser, searchedProject);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



    }

    private void showList(String username, String projectname){
        ArrayList<String[]> info = new ArrayList<>();

        ArrayList<Log> logs = projectService.getForAdmin(username, projectname, this);
        for(int n=0; n<logs.size();n++) {
            String projectName = projectService.findOne(logs.get(n).getProjectId(), this).getName();
            String name = userService.findByUsername(logs.get(n).getUsername(), this).getName();
            Date timeIn = logs.get(n).getTimeIn();
            Date timeOut = logs.get(n).getTimeOut();
            Long diff = timeOut.getTime() - timeIn.getTime();
            Long diffHours = diff / (1000 * 60 * 60);
            Long diffMinutes = diff / (1000 * 60)%60;
            String time = diffHours + ":" + diffMinutes;
            Format formatter = new SimpleDateFormat("yyyy/MM/dd");
            String timestampin = formatter.format(logs.get(n).getTimeIn());
            info.add(new String[]{name, projectName, time, timestampin});
        }


        ListView listviewlogs = (ListView)findViewById(R.id.listviewlogs);
        final loglist adapter = new loglist(this, android.R.layout.simple_list_item_1, info);
        listviewlogs.setAdapter(adapter);
    }
}
