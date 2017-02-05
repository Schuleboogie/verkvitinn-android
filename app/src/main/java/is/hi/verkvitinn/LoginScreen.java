package is.hi.verkvitinn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import is.hi.verkvitinn.service.UserService;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }
    /** Called when the user clicks the Send button */
    public void login(View view) {
        if (userService.auth(user)) {
            session.setAttribute("user", userService.findByUsername(user.getUsername()));
            // Run activity
            return "redirect:/home";
        }
        else {
            model.addAttribute("error", "Error authenticating user");
            return "index";
        }
    }
}
