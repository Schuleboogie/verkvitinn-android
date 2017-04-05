package is.hi.verkvitinn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import is.hi.verkvitinn.persistence.entities.Message;

/**
 * Created by skulii on 5.4.2017.
 */

public class CommentList extends ArrayAdapter {
    List<Message> comments;
    private static LayoutInflater inflater = null;

    public CommentList(Context context, int resource, List<Message> comments) {
        super(context, resource, comments);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.comments = comments;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return comments.get(position);
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
            vi = inflater.inflate(R.layout.messagelistitem, null);
        // Set information
        TextView messageContent = (TextView) vi.findViewById(R.id.mContent);
        TextView messageAuthor = (TextView) vi.findViewById(R.id.mAuthor);
        TextView messageTimestamp = (TextView) vi.findViewById(R.id.mTimestamp);
        final Message tMessage = comments.get(i);
        messageContent.setText(tMessage.getContent());
        messageAuthor.setText(tMessage.getAuthor());
        messageTimestamp.setText(String.valueOf(tMessage.getTimestamp()));

        return vi;
    }
}
