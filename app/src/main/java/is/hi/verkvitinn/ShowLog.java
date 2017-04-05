package is.hi.verkvitinn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

import is.hi.verkvitinn.persistence.entities.Log;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.service.ProjectService;
import is.hi.verkvitinn.service.UserService;

public class ShowLog extends AppCompatActivity {
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private MilestoneRepository milestoneRepository;
    private UserService userService;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        projectService = new ProjectService(projectRepository, milestoneRepository);
        userService = new UserService(userRepository);
        ArrayList<Object[]> info = new ArrayList<>();
        ArrayList<Log> logs = projectService.getForAdmin(this);
        for(int n=0; n<logs.size();n++){
            String projectName = projectService.findOne(logs.get(n).getProjectId(),this).getName();
            String name = userService.findByUsername(logs.get(n).getUsername(), this).getName();
            Date timeIn = logs.get(n).getTimeIn();
            Date timeOut = logs.get(n).getTimeOut();
            Long diff = timeOut.getTime()-timeIn.getTime();
            System.out.println(timeOut + " ------ " + timeIn);
            long diffHours = diff/(60*60*1000);
            System.out.println(projectName+ " " + name + " " + diffHours);
        }
        System.out.println(projectService.getForAdmin(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
    }
}
