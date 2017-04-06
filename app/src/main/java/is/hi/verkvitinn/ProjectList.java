package is.hi.verkvitinn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.ProjectScreen;


/**
 * Created by skulii on 17.3.2017.
 */

public class ProjectList extends ArrayAdapter {
    List<Project> projects;
    private static LayoutInflater inflater = null;
    public static final String PROJECT_ID = "is.hi.verkvitinn.PROJECT_ID";

    public ProjectList(Context context, int resource, List<Project> projects) {
        super(context, resource, projects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.projects = projects;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int i, View view, ViewGroup vg) {
        // TODO Auto-generated method stub
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.projectlistitem, null);
        // Set information
        TextView projectName = (TextView) vi.findViewById(R.id.pName);
        TextView projectWorkers = (TextView) vi.findViewById(R.id.pWorkers);
        TextView projectLocation = (TextView) vi.findViewById(R.id.pLocation);
        TextView projectStatus = (TextView) vi.findViewById(R.id.pStatus);
        final Project tProject = projects.get(i);
        projectName.setText(tProject.getName());
        projectWorkers.setText("" + tProject.getWorkers().length + " workers");
        projectLocation.setText(tProject.getLocation());
        projectStatus.setText(tProject.getStatus());

        // Add click listener for viewing project
        vi.findViewById(R.id.seeMoreButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProjectScreen.class);
                intent.putExtra(PROJECT_ID, tProject.getId());
                getContext().startActivity(intent);
            }
        });
        return vi;
    }
}
