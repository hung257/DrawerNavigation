package phumy.dmh.duongminhhung.drawernavigation.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import phumy.dmh.duongminhhung.drawernavigation.R;
import phumy.dmh.duongminhhung.drawernavigation.activity.adapter.DienthoaiAdapter;
import phumy.dmh.duongminhhung.drawernavigation.activity.model.Sanpham;
import phumy.dmh.duongminhhung.drawernavigation.activity.ultil.CheckConnection;
import phumy.dmh.duongminhhung.drawernavigation.activity.ultil.Server;

public class DienthoaiActivity extends AppCompatActivity {

    Toolbar toolbardt;
    ListView lvdt;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    boolean isloading = false;
    boolean limitdata = false;
//    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);

        AnhXa();
        GetIdDt();
        ActionToolbar();
        GetData(page);
        LoadMoreData();
        
    }

    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",mangdt.get(i));
                startActivity(intent);
            }
        });

//        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int FirstItem, int visibleItem, int TotalItem) {
//                if(FirstItem + visibleItem == TotalItem && TotalItem != 0 && isloading == false && limitdata == false){
//                    isloading = true;
//                    ThreadData threadData = new ThreadData();
//                    threadData.start();
//                }
//
//
//            }
//        });
    }

    private void GetData(int Pagee) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+String.valueOf(Pagee);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt ="";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Motadt = "";
                int Idspdt = 0;

                if (response !=null && response.length() !=2){
                    lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensp");
                            Giadt = jsonObject.getInt("giasp");
                            Hinhanhdt = jsonObject.getString("hinhanhsp");
                            Motadt = jsonObject.getString("motasp");
                            Idspdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Motadt,Idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitdata = true;
                    lvdt.removeFooterView(footerview);
                    CheckConnection.ShowToast_short(getApplicationContext(),"Da het du lieu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdDt() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
    }

    private void AnhXa() {
        toolbardt = findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progresbar,null);
        mangdt = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(),mangdt);
        lvdt.setAdapter(dienthoaiAdapter);

//        mHandler = new mHandler();
    }
//    public class mHandler extends Handler{
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 0:
//                    lvdt.addFooterView(footerview);
//                    break;
//                case 1:
//                    GetData(++page);
//                    isloading = false;
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//    public class ThreadData extends Thread {
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Message message = mHandler.obtainMessage(1);
//            mHandler.sendMessage(message);
//            super.run();
//        }
//    }
}
