package com.example.android.newsfeedapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class NewsAdapter extends ArrayAdapter<News> {

    private Context context;

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News news = getItem(position);
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();

            holder.newsTitle = (TextView) convertView.findViewById(R.id.news_title_textView);

            holder.newsSection = (TextView) convertView.findViewById(R.id.news_section_textView);

            holder.newsDate = (TextView) convertView.findViewById(R.id.news_date_textView);

            holder.newsType = (TextView) convertView.findViewById(R.id.news_type_textView);

            holder.newsImage = (ImageView) convertView.findViewById(R.id.news_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.newsTitle.setText(news.getTitleName());
        holder.newsSection.setText(news.getSectionName());
        holder.newsDate.setText(news.getDate());
        holder.newsType.setText(news.getType());
        Picasso.with(getContext()).load(news.getImage()).into(holder.newsImage);

        return convertView;
    }


    static class ViewHolder {

        TextView newsTitle;
        TextView newsSection;
        TextView newsDate;
        TextView newsType;
        ImageView newsImage;
    }
}