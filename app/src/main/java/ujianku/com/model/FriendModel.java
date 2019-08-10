package ujianku.com.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FriendModel extends RealmObject {
    @PrimaryKey
    private Integer id_friend;
    private Integer id_mahasiswa;
    private Integer nim;
    private String nama;
    private String kelas;
    private String tlp;
    private String email;
    private String sosmed;


    public Integer getId_friend() {
        return id_friend;
    }

    public void setId_friend(Integer id_friend) {
        this.id_friend = id_friend;
    }

    public Integer getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(Integer id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public Integer getNim() {
        return nim;
    }

    public void setNim(Integer nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTlp() {
        return tlp;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSosmed() {
        return sosmed;
    }

    public void setSosmed(String sosmed) {
        this.sosmed = sosmed;
    }
}
