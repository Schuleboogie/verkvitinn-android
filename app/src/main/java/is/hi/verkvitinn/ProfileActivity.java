package is.hi.verkvitinn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.service.UserService;

public class ProfileActivity extends AppCompatActivity {
    SessionManager session;
    private String username;
    private UserService userService;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.userService=new UserService(userRepository);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> userDetails = session.getUserDetails();

        // name
        username = userDetails.get(SessionManager.KEY_NAME);

        User user = userService.findByUsername(username, this);

        TextView username = (TextView)findViewById(R.id.username);
        TextView name = (TextView)findViewById(R.id.name);
        TextView role = (TextView)findViewById(R.id.role);

        username.setText(user.getUsername());
        name.setText(user.getName());
        role.setText(user.getRole());
    }
}
