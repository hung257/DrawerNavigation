package phumy.dmh.duongminhhung.drawernavigation.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import phumy.dmh.duongminhhung.drawernavigation.R;
import phumy.dmh.duongminhhung.drawernavigation.activity.model.Loaisp;

public class LoaispAdapter  extends BaseAdapter {
    ArrayList<Loaisp> arrayListloaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txttenloaisp;
        ImageView imgloaisp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp = view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = view.findViewById(R.id.imageloaisp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
//        Glide.with(context).load(loaisp.getHinhanhloaisp())
//                .into(viewHolder.imgloaisp);
        Picasso.get().load(loaisp.getHinhanhloaisp())
                .into(viewHolder.imgloaisp);

        return view;
    }
}
