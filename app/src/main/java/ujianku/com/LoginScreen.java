package ujianku.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import ujianku.com.model.MahasiswaModel;
import ujianku.com.presenter.RealmHelper;
import ujianku.com.presenter.SharedPreferences;

public class LoginScreen extends AppCompatActivity {

    @BindView(R.id.et_username)
    TextInputEditText et_username;
    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @BindView(R.id.btn_login)
    MaterialButton btn_login;
    @BindView(R.id.btn_link_signup)
    TextView btn_signup;
    @BindView(R.id.tf_login_password)
    TextInputLayout tf_password;
    @BindView(R.id.tf_login_username)
    TextInputLayout tf_username;
    SharedPreferences sp;

    Realm realm;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        sp = new SharedPreferences(getApplicationContext());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    tf_password.setErrorEnabled(true);
                    tf_password.setError("Field tidak boleh kosong");
                } else {
                    tf_password.setErrorEnabled(false);
                    password = et_password.getText().toString();
                }

                if (TextUtils.isEmpty(et_username.getText().toString())) {
                    tf_username.setErrorEnabled(true);
                    tf_username.setError("Field tidak boleh kosong");
                } else {
                    tf_username.setErrorEnabled(false);
                    username = et_username.getText().toString();
                }

                if (username != null && password != null) {
                    RealmHelper realmHelper = new RealmHelper(realm, getApplicationContext());
                    if (realmHelper.getMahasiswaProfile(username, password).size() != 0) {
                        List<MahasiswaModel> model = realmHelper.getMahasiswaProfile(username, password);
                        //Toast.makeText(getApplicationContext(),""+model.get(0).getId_mahasiswa(),Toast.LENGTH_SHORT).show();
                        sp.saveSPString("id_mahasiswa", String.valueOf(model.get(0).getId_mahasiswa()));
                        sp.saveSPBoolean("spSudahLogin",true);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password atau username yang anda masukan tidak sesuai", Toast.LENGTH_SHORT).show();
                        et_username.setText("");
                        et_password.setText("");
                        et_username.requestFocus();
                    }

                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignupScreen.class));
            }
        });
    }
}
