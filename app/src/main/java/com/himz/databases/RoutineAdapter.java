package com.himz.databases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.himz.entities.RoutineDataForAdapter;

import com.himz.selftrainer.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by himanshu on 2/21/15.
 */
public class RoutineAdapter extends BaseAdapter {
    private final Context context;
    private List<RoutineDataForAdapter> items;
    private final Map<View, Map<Integer, View>> cache = new HashMap<View, Map<Integer, View>>();
    private static boolean flagUpvote = true;
    private static boolean flagDownVote = false;
    public RoutineAdapter(Context context, List<RoutineDataForAdapter> items) {
        this.context = context;
        this.items = items;
    }

    public void setItemList(List<RoutineDataForAdapter> items){
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tv;
        final ImageView ivUpvote;
        final ImageView ivDownvote;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item, parent, false);
        }
        Map<Integer, View> itemMap = cache.get(v);
        if (itemMap == null) {
            itemMap = new HashMap<Integer, View>();
            tv = (TextView) v.findViewById(android.R.id.text1);
            ivUpvote = (ImageView) v.findViewById(R.id.imageView);
            ivDownvote = (ImageView) v.findViewById(R.id.imageViewDown);
            itemMap.put(android.R.id.text1, tv);
            itemMap.put(R.id.imageView, ivUpvote);
            itemMap.put(R.id.imageViewDown, ivDownvote);
            cache.put(v, itemMap);
        } else {
            tv = (TextView) itemMap.get(android.R.id.text1);
            ivUpvote = (ImageView) itemMap.get(R.id.imageView);
            ivDownvote = (ImageView) itemMap.get(R.id.imageViewDown);
        }
        RoutineDataForAdapter routineDataForAdapterItem = (RoutineDataForAdapter) getItem(position);

        final String item = String.valueOf(routineDataForAdapterItem.getExercise().getExerciseName());
        tv.setText(item);
        ivUpvote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagUpvote) {
                    ivUpvote.setImageResource(R.drawable.upvotegrey);
                    flagUpvote = false;
                } else {
                    ivUpvote.setImageResource(R.drawable.upvotegreen);
                    flagUpvote = true;
                    flagDownVote = false;
                    ivDownvote.setImageResource(R.drawable.upvotegrey);
                }
                Toast.makeText(context,
                        String.format("Image Up clicked: %s", item),
                        Toast.LENGTH_SHORT).show();
            }
        });

        ivDownvote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagDownVote) {
                    ivDownvote.setImageResource(R.drawable.upvotegrey);
                    flagDownVote = false;
                } else {
                    ivDownvote.setImageResource(R.drawable.upvotered);
                    flagDownVote = true;
                    flagUpvote = false;
                    ivUpvote.setImageResource(R.drawable.upvotegrey);
                }
                Toast.makeText(context,
                        String.format("Image Down clicked: %s", item),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}