package phumy.dmh.duongminhhung.drawernavigation.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import phumy.dmh.duongminhhung.drawernavigation.R;
import phumy.dmh.duongminhhung.drawernavigation.activity.model.Sanpham;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txttenchitiet, txtgiachitiet, txtmotachitiet;
    Spinner spinner;
    Button btndatmua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();
        ActionToolbar();
        GetIformation();
        CatchEventSpinner();


    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetIformation() {
        int idchitiet = 0;
        String Tenchitiet ="";
        int Giachitiet = 0;
        String Hinhanhchitiet = "";
        String Motachitiet = "";
        int Idsanphamchitiet = 0;

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        idchitiet = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        Idsanphamchitiet = sanpham.getIDsanpham();

        txttenchitiet.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiachitiet.setText("Gía: " + decimalFormat.format(Giachitiet) + "Đ");
        txtmotachitiet.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                     .into(imgchitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsp);
        imgchitiet = findViewById(R.id.imageviewchitietsp);
        txttenchitiet = findViewById(R.id.textviewtenchitietsp);
        txtgiachitiet = findViewById(R.id.textviewgiachitietsp);
        txtmotachitiet = findViewById(R.id.textviewmotachitietsp);
        spinner = findViewById(R.id.spinnerchitiet);
        btndatmua = findViewById(R.id.buttondatmua);

    }
}
