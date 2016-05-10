package sjsu.com.booktrade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import sjsu.com.booktrade.beans.BooksTO;

/**
 * Created by Bindi on 5/9/2016.
 */
public class SoldHistoryAdapter extends BaseAdapter {

    private List<BooksTO> bookList;
    //private ArrayList<BooksTO> bookList;
    FragmentActivity context;
    LayoutInflater inflater;

    public SoldHistoryAdapter(FragmentActivity context, int textViewResourceId, List<BooksTO> bookList) {
        Log.d("Adapter", "In Constructor");
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
        TextView book_userId;
        TextView userContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ConvertView", String.valueOf(position));
        View view = convertView;
        ViewHolder holder = null;


        Log.d("MSG", "I am in adapter");
        if (convertView == null) {

            Log.d("MSG", "I am in adapter");

            view = inflater.inflate(R.layout.display_soldhistory, parent, false);
            holder = new ViewHolder();

            holder.bookId = (TextView) view.findViewById(R.id.book_idHistory);
            holder.bookName = (TextView) view.findViewById(R.id.book_nameHistory);
            holder.author = (TextView) view.findViewById(R.id.book_authorHistory);
            holder.price = (TextView) view.findViewById(R.id.book_priceHistory);
            holder.category = (TextView) view.findViewById(R.id.book_categoryHistory);
            holder.edition = (TextView) view.findViewById(R.id.book_editionHistory);
            holder.shippingInfo = (TextView) view.findViewById(R.id.book_pickUpShipHistory);
            holder.image = (ImageView) view.findViewById(R.id.book_imageHistory);
            holder.book_userId = (TextView) view.findViewById(R.id.book_userIdHistory);
            holder.userContact = (TextView) view.findViewById(R.id.book_userContactHistory);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BooksTO books = (BooksTO) bookList.get(position);
        Log.d("Book ID", books.getBookId() + "");
        holder.bookId.setText(books.getBookId() + "");
        holder.bookName.setText(books.getBookName());
        holder.author.setText(books.getAuthor());
        holder.price.setText(books.getPrice() + "");
        holder.category.setText(books.getCategory());
        holder.edition.setText(books.getEdition() + "");
        holder.shippingInfo.setText(books.getPickUpOrShip());
        Log.d("UserId:: ", "" + books.getUser().getUserId());
        holder.book_userId.setText(books.getUser().getUserId() + "");
        holder.userContact.setText(books.getUser().getContactNumber() + "");
        Log.d("BookName", books.getBookName());
        holder.image.setImageURI(Uri.parse(books.getImageURLSmall()));

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        try {
            Bitmap bitmap;
            Log.d("BooksAdapter", "" + books.getImageURLSmall());
            //ImageView i = (ImageView) myView.findViewById(R.);
            if (books.getImageURLSmall() != null) {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(books.getImageURLSmall()).getContent());

            } else {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464").getContent());
            }
            holder.image.setImageBitmap(bitmap);
            ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //holder.image.setImageURI(Uri.parse(books.getImageURLSmall()));


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




