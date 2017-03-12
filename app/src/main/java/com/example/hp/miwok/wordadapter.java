package com.example.hp.miwok;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp on 28-02-2017.
 */

public class wordadapter extends ArrayAdapter<word> {
    public int colorid;
    public wordadapter(FragmentActivity context , ArrayList<word> wordmagic, int imageid){
        super(context,0,wordmagic);
        colorid = imageid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemview = convertView;
        if(listitemview == null){
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.single_scrap_view,parent,false);
        }
        word currentword = getItem(position);
        ImageView imageView = (ImageView) listitemview.findViewById((R.id.placeHolder));
        if(currentword.hasimage()!=-1){
            imageView.setImageResource(currentword.getMimageid());
            //imageView.setVisibility(imageView.VISIBLE);
        }
        else{
            imageView.setVisibility(imageView.GONE);
        }
        TextView nameview = (TextView) listitemview.findViewById(R.id.miwok_view);
        nameview.setText(currentword.getMmiwok());
        int color = ContextCompat.getColor(getContext(),colorid);
        nameview.setBackgroundColor(color);
        TextView nameview1 = (TextView) listitemview.findViewById(R.id.eng_view);
        nameview1.setText(currentword.getMmeng());
        //int color = ContextCompat.getColor(getContext(),colorid);
        nameview1.setBackgroundColor(color);



        return listitemview;

    }

}
