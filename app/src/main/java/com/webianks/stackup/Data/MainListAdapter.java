package com.webianks.stackup.Data;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.webianks.stackup.R;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainListAdapter extends BaseAdapter{

    List<SetterGetterClass> list;
    Context context;
    public  final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("year","month","day","hour","min","sec");

    public MainListAdapter(List<SetterGetterClass> list,Context context) {
        this.list=list;
        this.context=context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.single_question_ui,parent,false);
        }else {
            view=convertView;
        }

        TextView tags= (TextView) view.findViewById(R.id.tags);
        TextView user= (TextView) view.findViewById(R.id.user);
        TextView question= (TextView) view.findViewById(R.id.question);
        TextView score= (TextView) view.findViewById(R.id.score);
        TextView date= (TextView) view.findViewById(R.id.date);
        CircleImageView profile= (CircleImageView) view.findViewById(R.id.profile);
        TextView createdOn = (TextView) view.findViewById(R.id.created);


        String title=list.get(position).getTitle();
        if(title!=null)
            question.setText(Html.fromHtml(title));
        else{
            question.setText(" ");
        }

        long duration = list.get(position).getDate();
        long creation_duration = list.get(position).getCreationDate();


        long time_diff = System.currentTimeMillis() - (duration*1000);
        long creation_diff =  System.currentTimeMillis() - (creation_duration*1000);

        String last_date = toDuration(time_diff);
        String creation_date = toDuration(creation_diff);

        String[] tagsArray=list.get(position).getTags();
        String tagsString="";

        for(int i=0;i<tagsArray.length;i++){

            tagsString = tagsString+tagsArray[i]+" / ";
        }


        Picasso.with(context).load(list.get(position).getProfile()).into(profile);

        tags.setText(Html.fromHtml(tagsString));
        user.setText(Html.fromHtml(list.get(position).getName()));
        score.setText("score : "+Integer.toString(list.get(position).getScore()));
        date.setText("modified "+last_date);
        question.setText(Html.fromHtml(list.get(position).getTitle()));
        createdOn.setText("created "+creation_date);

        return view;

    }

    public String toDuration(long duration) {

        Log.d("Webi","Different times are " + duration);

        StringBuffer res = new StringBuffer();
        for(int i=0;i< times.size(); i++) {
            Long current = times.get(i);
            long temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append(timesString.get(i) ).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if("".equals(res.toString()))
            return "just now";
        else
            return res.toString();
    }


}
