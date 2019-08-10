package ujianku.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ujianku.com.model.MahasiswaModel;
import ujianku.com.presenter.RealmHelper;

public class SignupScreen extends AppCompatActivity {

    @BindView(R.id.et_nim_signup)
    TextInputEditText et_nim;
    @BindView(R.id.et_nama_signup)
    TextInputEditText et_nama;
    @BindView(R.id.et_kelas_signup)
    TextInputEditText et_kelas;
    @BindView(R.id.et_desc_signup)
    TextInputEditText et_desc;
    @BindView(R.id.et_tlp_signup)
    TextInputEditText et_tlp;
    @BindView(R.id.et_email_signup)
    TextInputEditText et_email;
    @BindView(R.id.et_sosmed_signup)
    TextInputEditText et_sosmed;
    @BindView(R.id.et_username_signup)
    TextInputEditText et_username;
    @BindView(R.id.et_password_signup)
    TextInputEditText et_password;
    @BindView(R.id.btn_signup)
    MaterialButton btn_signup;

    @BindView(R.id.tf_nim)
    TextInputLayout tf_nim;
    @BindView(R.id.tf_nama)
    TextInputLayout tf_nama;
    @BindView(R.id.tf_kelas)
    TextInputLayout tf_kelas;
    @BindView(R.id.tf_desc)
    TextInputLayout tf_desc;
    @BindView(R.id.tf_tlp)
    TextInputLayout tf_tlp;
    @BindView(R.id.tf_email)
    TextInputLayout tf_email;
    @BindView(R.id.tf_sosmed)
    TextInputLayout tf_sosmed;
    @BindView(R.id.tf_username)
    TextInputLayout tf_username;
    @BindView(R.id.tf_password)
    TextInputLayout tf_password;
    @BindView(R.id.display)
    TextView txt_display;

    String nama, kelas, desc,tlp, email, sosmed, username, password;
    Integer nim;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //konfigurasi
        realm = Realm.getDefaultInstance();
/*

menampilkan data

 RealmResults<MahasiswaModel> mahasiswa = realm.where(MahasiswaModel.class).findAll();
        String text="";
        for (MahasiswaModel data_mahasiswa:mahasiswa){
            text= text+data_mahasiswa.getId_mahasiswa()+"\n";
            text= text+data_mahasiswa.getNim()+"\n";
            text= text+data_mahasiswa.getNama()+"\n";
            text= text+data_mahasiswa.getKelas()+"\n";
            text= text+data_mahasiswa.getDesc()+"\n";
            text= text+data_mahasiswa.getTlp()+"\n";
            text= text+data_mahasiswa.getEmail()+"\n";
            text= text+data_mahasiswa.getSosmed()+"\n";
            text= text+data_mahasiswa.getUsername()+"\n";
            text= text+data_mahasiswa.getPassword()+"\n";
        }
        txt_display.setText(text);
 */


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //validasi
                if (TextUtils.isEmpty(et_nim.getText().toString())) {
                    tf_nim.setErrorEnabled(true);
                    tf_nim.setError("Field tidak boleh kosong");
                } else {
                    tf_nim.setErrorEnabled(false);
                    nim = Integer.valueOf(et_nim.getText().toString());
                }

                if (TextUtils.isEmpty(et_nama.getText().toString())) {
                    tf_nama.setErrorEnabled(true);
                    tf_nama.setError("Field tidak boleh kosong");
                } else {
                    tf_nama.setErrorEnabled(false);
                    nama = et_nama.getText().toString();
                }

                if (TextUtils.isEmpty(et_kelas.getText().toString())) {
                    tf_kelas.setErrorEnabled(true);
                    tf_kelas.setError("Field tidak boleh kosong");
                } else {
                    tf_kelas.setErrorEnabled(false);
                    kelas = et_kelas.getText().toString();
                }

                if (TextUtils.isEmpty(et_desc.getText().toString())) {
                    tf_desc.setErrorEnabled(true);
                    tf_desc.setError("Field tidak boleh kosong");
                } else {
                    tf_desc.setErrorEnabled(false);
                    desc = et_desc.getText().toString();
                }

                if (TextUtils.isEmpty(et_tlp.getText().toString())) {
                    tf_tlp.setErrorEnabled(true);
                    tf_tlp.setError("Field tidak boleh kosong");
                } else {
                    tf_tlp.setErrorEnabled(false);
                    tlp = et_tlp.getText().toString();
                }

                if (TextUtils.isEmpty(et_email.getText().toString())) {
                    tf_email.setErrorEnabled(true);
                    tf_email.setError("Field tidak boleh kosong");
                } else {
                    tf_email.setErrorEnabled(false);
                    email = et_email.getText().toString();
                }

                if (TextUtils.isEmpty(et_sosmed.getText().toString())) {
                    tf_sosmed.setErrorEnabled(true);
                    tf_sosmed.setError("Field tidak boleh kosong");
                } else {
                    tf_sosmed.setErrorEnabled(false);
                    sosmed = et_sosmed.getText().toString();
                }

                if (TextUtils.isEmpty(et_username.getText().toString())) {
                    tf_username.setErrorEnabled(true);
                    tf_username.setError("Field tidak boleh kosong");
                } else {
                    tf_username.setErrorEnabled(false);
                    username = et_username.getText().toString();
                }

                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    tf_password.setErrorEnabled(true);
                    tf_password.setError("Field tidak boleh kosong");
                } else {
                    tf_password.setErrorEnabled(false);
                    password = et_password.getText().toString();
                }

                if (nim != null &&
                        nama != null &&
                        kelas != null &&
                        desc != null &&
                        tlp != null &&
                        email != null &&
                        sosmed != null &&
                        username != null &&
                        password != null) {


                    RealmHelper realmHelper = new RealmHelper(realm,getApplicationContext());
                    realmHelper.saveMahasiswa(nim,nama,kelas,desc,tlp,email,sosmed,username,password);
                    et_nim.setText("");
                    et_tlp.setText("");
                    et_nama.setText("");
                    et_kelas.setText("");
                    et_desc.setText("");
                    et_email.setText("");
                    et_sosmed.setText("");
                    et_username.setText("");
                    et_password.setText("");
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
