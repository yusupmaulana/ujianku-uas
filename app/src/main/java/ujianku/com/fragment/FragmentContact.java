package ujianku.com.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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

public class FragmentContact extends Fragment {


    @BindView(R.id.btn_emailContact)
    CardView btnEmail;
    @BindView(R.id.btn_phoneContact)
    CardView btnPhone;
    @BindView(R.id.btn_sosmedContact)
    CardView btnSosmed;
    @BindView(R.id.txt_phoneContact)
    TextView txt_phone;
    @BindView(R.id.txt_emailContact)
    TextView txt_email;
    @BindView(R.id.txt_sosmedContact)
    TextView txt_sosmed;
    Realm realm;
    SharedPreferences sp;

    public FragmentContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        sp = new SharedPreferences(getContext());
        realm = Realm.getDefaultInstance();

        RealmHelper realmHelper = new RealmHelper(realm, getContext());
        List<MahasiswaModel> data = realmHelper.getMahasiswaById(Integer.parseInt(sp.getString("id_mahasiswa")));
        txt_email.setText(data.get(0).getEmail());
        txt_phone.setText(data.get(0).getTlp());
        txt_sosmed.setText(data.get(0).getSosmed());

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact(data.get(0).getEmail(), "email");
            }
        });


        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact(data.get(0).getTlp(), "phone");
            }
        });

        btnSosmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact(data.get(0).getSosmed(), "sosmed");
            }
        });

    }

    private void openContact(String uri, String kategori) {
        if (kategori.equals("sosmed")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
        } else if (kategori.equals("email")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + uri)));
        } else if (kategori.equals("phone")) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri)));
        }

    }

}
