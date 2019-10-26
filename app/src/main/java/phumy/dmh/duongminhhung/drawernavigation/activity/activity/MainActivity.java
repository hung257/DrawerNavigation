package phumy.dmh.duongminhhung.drawernavigation.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import phumy.dmh.duongminhhung.drawernavigation.R;
import phumy.dmh.duongminhhung.drawernavigation.activity.adapter.LoaispAdapter;
import phumy.dmh.duongminhhung.drawernavigation.activity.adapter.SanphamAdapter;
import phumy.dmh.duongminhhung.drawernavigation.activity.model.Loaisp;
import phumy.dmh.duongminhhung.drawernavigation.activity.model.Sanpham;
import phumy.dmh.duongminhhung.drawernavigation.activity.ultil.CheckConnection;
import phumy.dmh.duongminhhung.drawernavigation.activity.ultil.Server;

import static phumy.dmh.duongminhhung.drawernavigation.R.anim.slide_in_right;
import static phumy.dmh.duongminhhung.drawernavigation.R.anim.slide_out_right;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView recyclerViewmanhinhchinh;
    ListView listView;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;

    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AnhXa();
        AddControl();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListview();

        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
            finish();
        }


    }

    private void CatchOnItemListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienthoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra internet");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,LapTopActivity.class);
                        intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                        startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                        startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                        startActivity(intent);
                         }else {
                             CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra internet");
                             }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String Tensanpham = "";
                    int Giasanpham = 0;
                    String Hinhsanpham = "";
                    String Motasanpham = "";
                    int IDsanpham = 0;

                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            IDsanpham = jsonObject.getInt("idsanpham");

                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int id = 0;
                    String tenloaisp = "";
                    String hinhanhloaisp = "";

                    for (int i = 0; i <response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3, new Loaisp(0,"Liên Hệ","http://icons.iconarchive.com/icons/graphicloads/100-flat-2/96/phone-icon.png"));
                    mangloaisp.add(4, new Loaisp(0,"Thông Tin","http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/96/personal-information-icon.png"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://phuvuongjsc.vn/Media/slide/109.jpg");
        mangquangcao.add("http://phuvuongjsc.vn/Media/products/450_525.jpg");
        mangquangcao.add("https://hocvien.tiki.vn/wp-content/uploads/2019/08/TIKI-BANNER-RA-M%E1%BA%AET-TIKI-ADS_tiki-ads.png");
        mangquangcao.add("https://manhinhquangcaolcd.com/wp-content/uploads/2019/06/man-hinh-quang-cao-treo-tuong-cho-nha-hang.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Chuong-trinh-20.10-800-170(1)-800x170-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Reno2-800-170-800x170.png");

        for (int i = 0; i<mangquangcao.size(); i ++){
            ImageView imageView = new ImageView(getApplicationContext());

//            Glide.with(getApplicationContext())
//                    .load(mangquangcao.get(i))
//                    .into(imageView);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    viewFlipper.addView(imageView);

            Picasso.get()
                    .load(mangquangcao.get(i))
                    .into(imageView);
                     imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                     viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {

        drawerLayout = findViewById(R.id.drawer_layoutMain);
        toolbar = findViewById(R.id.toolbarmain);
        viewFlipper = findViewById(R.id.viewlippermain);
        navigationView = findViewById(R.id.nav_viewmain);
        recyclerViewmanhinhchinh = findViewById(R.id.recycleview);
        listView = findViewById(R.id.listviewmanhinhchinh);

    }

    private void AddControl() {
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","http://icons.iconarchive.com/icons/graphicloads/100-flat/96/home-icon.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispAdapter);

        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
    }
}
