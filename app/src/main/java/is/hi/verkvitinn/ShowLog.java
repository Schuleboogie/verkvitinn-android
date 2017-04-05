package is.hi.verkvitinn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.Format;
import java.text.SimpleDateFormat;
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
        ArrayList<String[]> info = new ArrayList<>();
        ArrayList<Log> logs = projectService.getForAdmin(this);
        for(int n=0; n<logs.size();n++) {
            String projectName = projectService.findOne(logs.get(n).getProjectId(), this).getName();
            String name = userService.findByUsername(logs.get(n).getUsername(), this).getName();
            Date timeIn = logs.get(n).getTimeIn();
            Date timeOut = logs.get(n).getTimeOut();
            Long diff = timeOut.getTime() - timeIn.getTime();
            Long diffHours = diff / (1000 * 60 * 60);
            Long diffMinutes = diff / (1000 * 60);
            String time = diffHours + ":" + diffMinutes;
            Format formatter = new SimpleDateFormat("yyyy/MM/dd");
            String timestampin = formatter.format(logs.get(n).getTimeIn());
            info.add(new String[]{name, projectName, time, timestampin});
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);

        ListView listviewlogs = (ListView)findViewById(R.id.listviewlogs);
        final loglist adapter = new loglist(this, android.R.layout.simple_list_item_1, info);
        listviewlogs.setAdapter(adapter);
    }
}
