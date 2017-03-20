package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.UserRepository;
import is.hi.verkvitinn.service.UserService;
import is.hi.verkvitinn.persistence.repositories.UserRepository;


public class LoginScreen extends AppCompatActivity {

    UserService userService;
    UserRepository users;

    // Widgets
    private EditText usernameText;
    private EditText passwordText;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.userService = new UserService(users);

        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.signInMessage);
    }
    /** Called when the user clicks the Send button */
    public void login(View view) {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        boolean auth = true;
        // Check if user has logged in before, if not register user
        if (userService.findByUsername(username, this) != null) {
            // Authenticate user
            if (!userService.auth(username, password, this)) {
                auth = false;
            }
        }
        else {
            userService.register(username, password, "worker", "Skúli Ingvarsson", this);
        }
        // If user has authentication, proceed
        if (auth) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        else errorText.setText(R.string.sign_in_error);
    }
}
