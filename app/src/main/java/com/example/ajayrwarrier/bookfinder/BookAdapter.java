package com.example.ajayrwarrier.bookfinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Ajay R Warrier on 04-11-2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> bookArrayList) {

        super(context, 0, bookArrayList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        TextView nameTextView =(TextView)listItemView.findViewById(R.id.nameView);
        nameTextView.setText(currentBook.getBookName());
        TextView addrTextView =(TextView)listItemView.findViewById(R.id.authorView);
        addrTextView.setText(currentBook.getAuthorName());


        return listItemView;
    }
}
