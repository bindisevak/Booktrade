package sjsu.com.booktrade;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;

/**
 * Created by Bindi on 4/26/2016.
 */
public class BooksAdapter extends BaseAdapter {
    private List<BooksTO> bookList;
    //private ArrayList<BooksTO> bookList;
   FragmentActivity context;
    LayoutInflater inflater;
    public BooksAdapter(FragmentActivity context, int textViewResourceId, List<BooksTO> bookList) {
        Log.d("Adapter","In Constructor");
        this.bookList = bookList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }



    private class ViewHolder {
        TextView bookId;
        TextView bookName;
        TextView author;
        TextView price;
        TextView category;
        TextView edition;
        TextView shippingInfo;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ConvertView", String.valueOf(position));
        View view = convertView;
        ViewHolder holder = null;


        Log.d("MSG", "I am in adapter");
        if (convertView == null) {

             Log.d("MSG", "I am in adapter");

            view = inflater.inflate(R.layout.display_books, parent, false);
            holder = new ViewHolder();

            holder.bookId = (TextView) view.findViewById(R.id.book_id);
            holder.bookName = (TextView) view.findViewById(R.id.book_name);
            holder.author = (TextView) view.findViewById(R.id.book_author);
            holder.price = (TextView) view.findViewById(R.id.book_price);
            holder.category = (TextView) view.findViewById(R.id.book_category);
            holder.edition = (TextView) view.findViewById(R.id.book_edition);
            holder.shippingInfo = (TextView) view.findViewById(R.id.book_pickUpShip);
            holder.image = (ImageView) view.findViewById(R.id.book_image);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        BooksTO books = (BooksTO)bookList.get(position);
        Log.d("Book ID",books.getBookId()+"");
        holder.bookId.setText(books.getBookId()+"");
        holder.bookName.setText(books.getBookName());
        holder.author.setText(books.getAuthor());
        holder.price.setText(books.getPrice()+"");
        holder.category.setText(books.getCategory());
        holder.edition.setText(books.getEdition()+"");
        holder.shippingInfo.setText(books.getPickUpOrShip());
        holder.image.setImageURI(Uri.parse(books.getImageURLSmall()));

        return view;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
