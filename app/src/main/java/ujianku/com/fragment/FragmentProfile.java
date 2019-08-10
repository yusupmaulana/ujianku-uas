package ujianku.com.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.xml.sax.Parser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import ujianku.com.LoginScreen;
import ujianku.com.R;
import ujianku.com.model.MahasiswaModel;
import ujianku.com.presenter.RealmHelper;
import ujianku.com.presenter.SharedPreferences;

/*
 * Nim : 10116093
 * Nama : Yusup Maulanadireja
 * Kelas : IF-3/AKB-3
 * Tanggal Pengerjaan : 19 Mei 2019
 * */

public class FragmentProfile extends Fragment {


    @BindView(R.id.nim_profile)
    TextView nim_profile;
    @BindView(R.id.nama_profile)
    TextView nama_profile;
    @BindView(R.id.kelas_profile)
    TextView kelas_profile;
    @BindView(R.id.aboutme_profile)
    TextView aboutme_profile;
    @BindView(R.id.btn_logout)
    MaterialButton btn_logout;
    SharedPreferences sp;
    Realm realm;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        sp = new SharedPreferences(getContext());
        realm = Realm.getDefaultInstance();

       // Toast.makeText(getContext(),sp.getString("id_mahasiswa"),Toast.LENGTH_SHORT).show();
        RealmHelper realmHelper = new RealmHelper(realm,getContext());
        List<MahasiswaModel> data= realmHelper.getMahasiswaById(Integer.parseInt(sp.getString("id_mahasiswa")));
        if (data.size()!= 0){
            nim_profile.setText("("+data.get(0).getNim()+")");
            nama_profile.setText(data.get(0).getNama());
             kelas_profile.setText("Kelas "+data.get(0).getKelas()+" di Universitas Komputer Indonesia");
            aboutme_profile.setText(data.get(0).getDesc());
        }else{
            Toast.makeText(getContext(),"Data tidak ditemukan",Toast.LENGTH_SHORT).show();
        }


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.remove("id_mahasiswa");
                sp.saveSPBoolean("spSudahLogin",false);
                startActivity(new Intent(getContext(),LoginScreen.class));
                getActivity().finish();
            }
        });
    };
}
