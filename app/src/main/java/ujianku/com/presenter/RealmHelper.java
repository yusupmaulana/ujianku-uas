package ujianku.com.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ujianku.com.adapter.MyFirendsAdapter;
import ujianku.com.model.FriendModel;
import ujianku.com.model.MahasiswaModel;

public class RealmHelper {
    Realm realm;
    Context context;

    public RealmHelper(Realm realm, Context context) {
        this.realm = realm;
        this.context = context;
    }

    //   private Integer id_mahasiswa;
    public void saveMahasiswa(Integer nim, String nama, String kelas, String desc, String tlp, String email, String sosmed, String username, String password) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("UJIANKU", "Database was created");
                    Number currentId = realm.where(MahasiswaModel.class).max("id_mahasiswa");
                    int nextId;
                    if (currentId == null) {
                        nextId = 1;
                    } else {
                        nextId = currentId.intValue() + 1;
                    }
                    MahasiswaModel mahasiswaModel = realm.createObject(MahasiswaModel.class, nextId);
                    mahasiswaModel.setNim(nim);
                    mahasiswaModel.setNama(nama);
                    mahasiswaModel.setKelas(kelas);
                    mahasiswaModel.setDesc(desc);
                    mahasiswaModel.setTlp(tlp);
                    mahasiswaModel.setEmail(email);
                    mahasiswaModel.setSosmed(sosmed);
                    mahasiswaModel.setUsername(username);
                    mahasiswaModel.setPassword(password);

                } else {
                    Log.e("UJIANKU", "execute: Database not Exist");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Berhasil membuat akun,silahkan login..", Toast.LENGTH_SHORT).show();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, error + " Gagal membuat akun,silahkan coba kembali..", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //simpan teman
    public void saveTeman(Integer id_mahasiswa, Integer nim, String nama, String kelas, String tlp, String email, String sosmed) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("UJIANKU", "Database was created");
                    Number currentId = realm.where(FriendModel.class).max("id_friend");
                    int nextId;
                    if (currentId == null) {
                        nextId = 1;
                    } else {
                        nextId = currentId.intValue() + 1;
                    }
                    FriendModel friendModel = realm.createObject(FriendModel.class, nextId);
                    friendModel.setId_mahasiswa(id_mahasiswa);
                    friendModel.setNim(nim);
                    friendModel.setNama(nama);
                    friendModel.setKelas(kelas);
                    friendModel.setTlp(tlp);
                    friendModel.setEmail(email);
                    friendModel.setSosmed(sosmed);

                } else {
                    Log.e("UJIANKU", "execute: Database not Exist");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Berhasil menambahkan pertemanan", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Pull down, untuk refresh data",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, error + " Gagal menambahkan pertemanan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //tampil teman berdasaran id mahasiswa
    public List<FriendModel> getAllFriendmahasiswa(Integer id_mahasiswa) {
        RealmResults<FriendModel> results = realm.where(FriendModel.class)
                .equalTo("id_mahasiswa", id_mahasiswa)
                .findAll();
        return results;
    }

    // untuk menghapus data teman
    public void deleteFriend(Integer id_friend) {
        final RealmResults<FriendModel> model = realm.where(FriendModel.class)
                .equalTo("id_friend", id_friend)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //  model.deleteFirstFromRealm();
                model.deleteFromRealm(0);
            }
        });
    }

    //update teman
    public void updateFriend(Integer id_friend, Integer nim, String nama, String kelas, String tlp, String email, String sosmed) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FriendModel model = realm.where(FriendModel.class)
                        .equalTo("id_friend", id_friend)
                        .findFirst();
                model.setNim(nim);
                model.setNama(nama);
                model.setKelas(kelas);
                model.setTlp(tlp);
                model.setEmail(email);
                model.setSosmed(sosmed);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Update data berhasil", Toast.LENGTH_SHORT).show();
                Log.e("UJIANKU", "onSuccess: Update Successfully");
                Toast.makeText(context,"Pull down, untuk refresh data",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, "Update tidak berhasil " + error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

    //mencari data berdasarkan id_mahasiswa
    public List<MahasiswaModel> getMahasiswaById(Integer id_mahasiswa) {
        RealmResults<MahasiswaModel> results = realm.where(MahasiswaModel.class)
                .equalTo("id_mahasiswa", id_mahasiswa)
                .findAll();
        return results;
    }

    //mencari data yg sesuai
    public List<MahasiswaModel> getMahasiswaProfile(String username, String password) {
        RealmResults<MahasiswaModel> results = realm.where(MahasiswaModel.class)
                .equalTo("username", username)
                .and()
                .equalTo("password", password)
                .findAll();
        return results;
    }

}
