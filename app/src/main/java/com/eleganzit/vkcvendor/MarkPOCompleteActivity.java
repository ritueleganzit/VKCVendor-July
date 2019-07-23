package com.eleganzit.vkcvendor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.vkcvendor.adapter.RecyclerViewPOAdapter;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

public class MarkPOCompleteActivity extends AppCompatActivity {
RecyclerView rc_po_complete_list;
    RecyclerView rc_image;
    ArrayList<String> arrayList=new ArrayList<>();
    private static final int REQUEST_IMAGE = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 202;
    private ArrayList<String> mSelectPath;


    ArrayList<String> str_photo_array=new ArrayList<>();
    private String mediapath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_pocomplete);
        rc_po_complete_list=findViewById(R.id.rc_po_complete_list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MarkPOCompleteActivity.this,LinearLayoutManager.VERTICAL,false);
        rc_po_complete_list.setLayoutManager(layoutManager);

        arrayList.add("fsdfsfsd");
        arrayList.add("fsdfsfsd");
        arrayList.add("fsdfsfsd");
        arrayList.add("fsdfsfsd");

        rc_po_complete_list.setAdapter(new MarkPOCompleteAdapter(MarkPOCompleteActivity.this,arrayList));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }



    public class MarkPOCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int HEADER_VIEW = 1;
        private static final int FOOTER_VIEW = 11;
        private static final int FOOTER_VIEW2 = 12;
        private ArrayList<String> data; // Take any list that matches your requirement.
        private Context context;

        // Define a constructor
        public MarkPOCompleteAdapter(Context context, ArrayList<String> data) {
            this.context = context;
            this.data = data;
        }

        // Define a ViewHolder for Footer view
        public class FooterViewHolder extends ViewHolder {
            public FooterViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do whatever you want on clicking the item
                    }
                });
            }
        }  public class FooterViewHolderUpload extends ViewHolder {
            EditText ed_email;
            public FooterViewHolderUpload(View itemView) {
                super(itemView);
                rc_image=itemView.findViewById(R.id.rc_image);
                ed_email=itemView.findViewById(R.id.ed_email);

            //    aadhar_back_pic=itemView.findViewById(R.id.aadhar_back_pic);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do whatever you want on clicking the item
                    }
                });
            }
        }

        // Now define the ViewHolder for Normal list item
        public class NormalViewHolder extends ViewHolder {
            public NormalViewHolder(View itemView) {
                super(itemView);

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
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;

            if (viewType == HEADER_VIEW) {
                v = LayoutInflater.from(context).inflate(R.layout.list_item_footer_po, parent, false);
                FooterViewHolder vh = new FooterViewHolder(v);
                return vh;
            }
            if (viewType == FOOTER_VIEW) {
                v = LayoutInflater.from(context).inflate(R.layout.row_po_complete, parent, false);
                FooterViewHolder vh = new FooterViewHolder(v);
                return vh;
            } if (viewType == FOOTER_VIEW2) {
                v = LayoutInflater.from(context).inflate(R.layout.row_markpo_uploadpic, parent, false);
                FooterViewHolderUpload vh = new FooterViewHolderUpload(v);
                return vh;
            }

            v = LayoutInflater.from(context).inflate(R.layout.list_item_normal, parent, false);

            NormalViewHolder vh = new NormalViewHolder(v);

            return vh;
        }

        // Now bind the ViewHolder in onBindViewHolder
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            try {
                if (holder instanceof NormalViewHolder) {
                    NormalViewHolder vh = (NormalViewHolder) holder;

                    vh.bindView(position);
                } else if (holder instanceof FooterViewHolder) {
                    FooterViewHolder vh = (FooterViewHolder) holder;
                }else if (holder instanceof FooterViewHolderUpload) {
                    FooterViewHolderUpload vh = (FooterViewHolderUpload) holder;
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MarkPOCompleteActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    rc_image.setLayoutManager(layoutManager);

                   vh.ed_email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
pickImage();

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
            if (data == null) {
                return 0;
            }

            if (data.size() == 0) {
                //Return 1 here to show nothing
                return 1;
            }

            // Add extra view to show the footer view
            return data.size() + 1;
        }

        // Now define getItemViewType of your own.

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                // This is where we'll add footer.
                return HEADER_VIEW;
            }
            if (position == data.size()) {
                // This is where we'll add footer.
                return FOOTER_VIEW;
            }
            if (position == data.size()-1) {
                // This is where we'll add footer.
                return FOOTER_VIEW2;
            }

            return super.getItemViewType(position);
        }

        // So you're done with adding a footer and its action on onClick.
        // Now set the default ViewHolder for NormalViewHolder

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Define elements of a row here
            public ViewHolder(View itemView) {
                super(itemView);
                // Find view by ID and initialize here
            }

            public void bindView(int position) {
                // bindView() method to implement actions
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }


                mediapath = sb.toString().trim();
                Log.d("LOG_TAG", "Selected Images 1.5" + mediapath);

                rc_image.setAdapter(new ImageAdapter(mSelectPath,MarkPOCompleteActivity.this));
            }
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(MarkPOCompleteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(MarkPOCompleteActivity.this);
            selector.multi();
            selector.count(6);
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(MarkPOCompleteActivity.this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MarkPOCompleteActivity.this, permission)) {
            new AlertDialog.Builder(MarkPOCompleteActivity.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MarkPOCompleteActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(MarkPOCompleteActivity.this, new String[]{permission}, requestCode);
        }
    }
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
    {
        ArrayList<String> img;
        Context context;

        public ImageAdapter(ArrayList<String> img, Context context) {
            this.img = img;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_defects, viewGroup, false);
            ViewHolder myViewHolder = new ViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            Log.d("ddddddd",""+mediapath);

            Glide.with(getApplicationContext()).load(img.get(i)).into(viewHolder.imageView);

            viewHolder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return img.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageButton mRemoveButton;
            public ImageView imageView;

            public ViewHolder(View v){
                super(v);
                imageView = (ImageView) v.findViewById(R.id.img);
                mRemoveButton = (ImageButton) v.findViewById(R.id.ib_remove);
            }
        }

        private void removeAt(int position) {

            img.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, img.size());
            notifyItemChanged(position);
        }

    }

}
