package com.eleganzit.vkcvendor.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcvendor.MarkPOCompleteActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.ArticleData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyclerViewPOAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewPOAdapter.MyViewHolder> {

    private static final int HEADER_VIEW = 1;
    private static final int FOOTER_VIEW = 11;
    private List<ArticleData> data; // Take any list that matches your requirement.
    private Context context;
    private String po_number;

    // Define a constructor
    public HorizontalRecyclerViewPOAdapter(Context context, List<ArticleData> data,String po_number) {
        this.context = context;
        this.data = data;
        this.po_number = po_number;
    }

    // Define a ViewHolder for Footer view
    public class FooterViewHolder extends MyViewHolder {
        LinearLayout next;

        public FooterViewHolder(final View itemView) {
            super(itemView);
            next = itemView.findViewById(R.id.next);

        }
    }

    public class HeaderViewHolder extends MyViewHolder {

        public HeaderViewHolder(final View itemView) {
            super(itemView);

        }
    }

    // Now define the ViewHolder for Normal list item
    public class NormalViewHolder extends MyViewHolder {

        RecyclerView rc_po_list;
        TextView txt_po_number,txt_line_number,txt_article;

        public NormalViewHolder(View itemView) {
            super(itemView);

            rc_po_list=itemView.findViewById(R.id.rc_po_list);
            txt_po_number=itemView.findViewById(R.id.txt_po_number);
            txt_line_number=itemView.findViewById(R.id.txt_line_number);
            txt_article=itemView.findViewById(R.id.txt_article);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }

    // And now in onCreateViewHolder you have to pass the correct view
    // while populating the list item.

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        /*if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(context).inflate(R.layout.list_item_footer_po, parent, false);
            HeaderViewHolder vh = new HeaderViewHolder(v);
            return vh;
        } else if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(context).inflate(R.layout.row_po, parent, false);
            FooterViewHolder vh = new FooterViewHolder(v);
            return vh;
        } else {*/
            v = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item_normal, parent, false);

            NormalViewHolder vh = new NormalViewHolder(v);

            return vh;
        /*}*/
    }

    // Now bind the ViewHolder in onBindViewHolder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            if (holder instanceof NormalViewHolder) {
                ArticleData articleData=data.get(position);
                NormalViewHolder vh = (NormalViewHolder) holder;

                vh.rc_po_list.setAdapter(new RecyclerViewPOAdapter(context,articleData.getGridData()));
                vh.txt_po_number.setText(po_number);
                vh.txt_line_number.setText(articleData.getLineNumber());
                vh.txt_article.setText(articleData.getArticleName());

                vh.bindView(position);
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.bindView(position);
                vh.next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, MarkPOCompleteActivity.class));

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Now the critical part. You have return the exact item count of your list
    // I've only one footer. So I returned data.size() + 1
    // If you've multiple headers and footers, you've to return total count
    // like, headers.size() + data.size() + footers.size()

    @Override
    public int getItemCount() {
        /*if (data == null) {
            return 0;
        }

        if (data.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }
*/
        // Add extra view to show the footer view
        return data.size();
    }

    // Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {
        /*if (position == 0) {
            // This is where we'll add footer.
            return HEADER_VIEW;
        }*/
        /*if (position == data.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }*/

        return super.getItemViewType(position);
    }

    // So you're done with adding a footer and its action on onClick.
    // Now set the default ViewHolder for NormalViewHolder

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        public MyViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }
    }


}
