package ujianku.com.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import ujianku.com.R;
import ujianku.com.adapter.MyFirendsAdapter;
import ujianku.com.model.FriendModel;
import ujianku.com.model.FriendsModel;
import ujianku.com.model.MahasiswaModel;
import ujianku.com.presenter.RealmHelper;
import ujianku.com.presenter.SharedPreferences;
import ujianku.com.view.MainFriends;

/*
 * Nim : 10116093
 * Nama : Yusup Maulanadireja
 * Kelas : IF-3/AKB-3
 * Tanggal Pengerjaan : 19 Mei 2019
 * */

public class FragmentFriends extends Fragment implements MainFriends {

    @BindView(R.id.btn_tambah)
    FloatingActionButton btnTambah;
    @BindView(R.id.rv_friends)
    RecyclerView rvFriends;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout sw_refresh;

    private MyFirendsAdapter myFirendsAdapter;
    Realm realm;
    RealmHelper realmHelper;
    SharedPreferences sp;


    public FragmentFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inisialisasi
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        sp = new SharedPreferences(getContext());
        realmHelper = new RealmHelper(realm,getContext());

        //memangil data rv
        setRecyclerFriends();

        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRecyclerFriends();
                        sw_refresh.setRefreshing(false);
                    }
                },2000);

            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_tambah, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        TextInputEditText etNim = (TextInputEditText) dialogView.findViewById(R.id.et_nim);
        TextInputEditText etNama = (TextInputEditText) dialogView.findViewById(R.id.et_nama);
        TextInputEditText etKelas = (TextInputEditText) dialogView.findViewById(R.id.et_kelas);
        TextInputEditText etTelp = (TextInputEditText) dialogView.findViewById(R.id.et_tlp);
        TextInputEditText etEmail = (TextInputEditText) dialogView.findViewById(R.id.et_email);
        TextInputEditText etSosmed = (TextInputEditText) dialogView.findViewById(R.id.et_sosmed);
        MaterialButton btnSimpan = (MaterialButton) dialogView.findViewById(R.id.btn_simpan);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNim.getText().toString().equals("")
                        && etNama.getText().toString().equals("")
                        && etKelas.getText().toString().equals("")
                        && etTelp.getText().toString().equals("")
                        && etEmail.getText().toString().equals("")
                        && etSosmed.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Data tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    //mendapatkan id_mahasiswa
                    List<MahasiswaModel> data_mahasiswa = realmHelper.getMahasiswaById(Integer.parseInt(sp.getString("id_mahasiswa")));
                    if (data_mahasiswa.size()!= 0 ){
                        try {
                            realmHelper.saveTeman(Integer.parseInt(sp.getString("id_mahasiswa")),
                                    Integer.valueOf(etNim.getText().toString()),
                                    etNama.getText().toString(),
                                    etKelas.getText().toString(),
                                    etTelp.getText().toString(),
                                    etEmail.getText().toString(),
                                    etSosmed.getText().toString());
                            myFirendsAdapter.notifyDataSetChanged();

                        }catch (Exception e){
                            Toast.makeText(getContext(),"Data nim tidak boleh melebihi 10 kata",Toast.LENGTH_SHORT).show();
                        }


                    }
                  //  setRecyclerFriends();
                    alertDialog.dismiss();

                }
            }
        });
    }

    @Override
    public void setRecyclerFriends() {
        List<FriendModel> friendModels = realmHelper.getAllFriendmahasiswa(Integer.parseInt(sp.getString("id_mahasiswa")));
        if (friendModels.size()!= 0){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            myFirendsAdapter = new MyFirendsAdapter(getContext(), friendModels,realmHelper,realm);
            rvFriends.setItemAnimator(new DefaultItemAnimator());
            rvFriends.setAdapter(myFirendsAdapter);
            rvFriends.setLayoutManager(layoutManager);
        }else{
            Toast.makeText(getContext(),"Mulai tambahkan pertemanan anda sekarang..",Toast.LENGTH_SHORT).show();
        }


    }
}
