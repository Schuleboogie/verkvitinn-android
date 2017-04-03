package is.hi.verkvitinn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.entities.Project;

import static is.hi.verkvitinn.R.id.projectLocation;

/**
 * Created by skulii on 3.4.2017.
 */

public class MilestoneList extends ArrayAdapter {
    List<Milestone> milestones;
    private static LayoutInflater inflater = null;

    public MilestoneList(Context context, int resource, List<Milestone> milestones) {
        super(context, resource, milestones);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.milestones = milestones;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return milestones.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return milestones.get(position);
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
            vi = inflater.inflate(R.layout.milestonelistitem, null);
        // Set information
        TextView milestoneTitle = (TextView) vi.findViewById(R.id.mTitle);
        TextView milestoneTimestamp = (TextView) vi.findViewById(R.id.mTimestamp);
        final Milestone tMilestone = milestones.get(i);
        milestoneTitle.setText(tMilestone.getTitle());
        milestoneTimestamp.setText(String.valueOf(tMilestone.getTimestamp()));

        return vi;
    }
}
