package is.hi.verkvitinn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.service.UserService;

public class WorkerGroup extends AppCompatActivity {
    UserService userService;
    UserRepository userRepository;
    private TextView errorText;
    private ListView workerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.userService= new UserService(userRepository);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_group);


        errorText = (TextView) findViewById(R.id.errorText);
        workerList = (ListView) findViewById(R.id.workerList);


        List<User> workers= userService.findByRole("Worker");
        if (workers != null) {
            WorkerList adapter = new WorkerList(this, android.R.layout.simple_list_item_1, workers);
            workerList.setAdapter(adapter);
        }
        else {
            errorText.setText("No workers found");
        }
    }

}
