package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

import SessionManagement.SessionManager;
import is.hi.verkvitinn.persistence.entities.Message;
import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.repositories.MessageRepository;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.MessageService;
import is.hi.verkvitinn.service.ProjectService;

public class AddComment extends AppCompatActivity {
    MessageService messageService;
    MessageRepository messages;

    private SessionManager session;
    private String adminString;
    private String username;

    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";
    private Long projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        this.messageService = new MessageService(messages);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        adminString = user.get(SessionManager.KEY_ADMIN);
        username = user.get(SessionManager.KEY_NAME);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project id
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(Comments.PROJECT_ID, -1);
        this.projectId = projectId;
    }
    public void addComment(View view) {
        EditText messageTextElement = (EditText) findViewById(R.id.messageText);
        String messageText = messageTextElement.getText().toString();
        Message newMessage = new Message(this.projectId, new Date(), username, adminString.equals("admin"), false, messageText);
        if (messageService.create(newMessage, this) != null) {
            Toast.makeText(this, "Comment added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Comments.class);
            intent.putExtra(PROJECT_ID, this.projectId);
            startActivity(intent);
        }
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
