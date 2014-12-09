package cs442.team6.memoirs;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {
	public final Context context;
    public final ArrayList<Event> itemsArrayList;
    Typeface font;

    public EventAdapter(Context context, ArrayList<Event> itemsArrayList) {

        super(context, R.layout.event_single, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        
    }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 

		 	EventViewHolder holder = null; 
		 	String inflater;
     
            if (convertView == null) {
            	
                convertView = new LinearLayout(getContext());
                inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
                convertView = vi.inflate(R.layout.event_single, parent, false);
               
                holder = new EventViewHolder();
                holder.time = (TextView)convertView.findViewById(R.id.txtLTime);
                holder.date = (TextView)convertView.findViewById(R.id.txtDate);
                holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
                holder.description = (TextView)convertView.findViewById(R.id.txtDescription);
                
               //img.setImageResource(products.get(position).getImage());
               //title.setText(products.get(position).getTitle());
                
                convertView.setTag(holder);
            }
            else{
                holder = (EventViewHolder) convertView.getTag();
            }
    
            holder.populate(getItem(position));
            
            
			return convertView;
    }
        
        class EventViewHolder {
        	
        	public TextView date;
        	public TextView time;
	        public TextView title;
	        public TextView description;
	        
	        void populate(Event p) {
	        	time.setText(p.eventTime);
	        	date.setText(p.eventDate);
	            title.setText(p.eventTitle);
	            description.setText(p.eventDescription);
	 
	        }	 
	 }

}
