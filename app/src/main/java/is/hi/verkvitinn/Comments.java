package is.hi.verkvitinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Message;
import is.hi.verkvitinn.persistence.repositories.MessageRepository;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.service.MessageService;
import is.hi.verkvitinn.service.ProjectService;

public class Comments extends AppCompatActivity {
    MessageService messageService;
    MessageRepository messages;

    private Long projectId;
    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        this.messageService = new MessageService(messages);

        // Enable back button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_icon);

        // Get project info
        Intent intent = getIntent();
        Long projectId = intent.getLongExtra(ProjectScreen.PROJECT_ID, -1);
        this.projectId = projectId;

        List<Message> foundMessages = messageService.findByProjectId(projectId, this);
        if (foundMessages != null) {
            ListView messageList = (ListView) findViewById(R.id.commentList);
            CommentList adapter = new CommentList(this, android.R.layout.simple_list_item_1, foundMessages);
            messageList.setAdapter(adapter);
        }
    }
    public void addComment(View view) {
        Intent intent = new Intent(this, AddComment.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
    }
    public void returnToProject(View view) {
        Intent intent = new Intent(this, ProjectScreen.class);
        intent.putExtra(PROJECT_ID, this.projectId);
        startActivity(intent);
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
