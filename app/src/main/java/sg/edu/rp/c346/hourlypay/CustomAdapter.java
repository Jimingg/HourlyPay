package sg.edu.rp.c346.hourlypay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CustomAdapter extends ArrayAdapter{

    Context parent_context;
    int layout_id;
    ArrayList<Records> recordsList;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Records> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        recordsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtain the LayoutInflater object.
        LayoutInflater inflater = (LayoutInflater) parent_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate a new view hierarchy from the specified xml resource (layout_id)
        // and return the root View of the inflated hierarchy.
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI elements and bind them to their respective Java variable.
        TextView tvDesc = rowView.findViewById(R.id.textViewDateDesc);
        TextView tvSD = rowView.findViewById(R.id.textViewStart);
        TextView tvED =rowView.findViewById(R.id.textViewEnd);
        TextView tvhour = rowView.findViewById(R.id.textViewhour);
        TextView tvbreaks = rowView.findViewById(R.id.textViewbreak);
        TextView tvTotal =rowView.findViewById(R.id.textViewtotal);
        TextView tvdate = rowView.findViewById(R.id.textViewDateDate);

        // Obtain the property values from the Data Class object
        // and set them to the corresponding UI elements.
        Records currentItem = recordsList.get(position);
        tvDesc.setText(currentItem.getDesc());
        tvdate.setText(currentItem.getDate());
        tvSD.setText(currentItem.getStart());
        tvED.setText(currentItem.getEnd());
        tvhour.setText(String.valueOf(currentItem.getHour()));
        tvbreaks.setText(currentItem.getBreaks());
        tvTotal.setText(currentItem.getPay().toString());
// Return the View corresponding to the data at the specified position.
        return rowView;
    }
}
