package com.example.p6_mysql_1124040031;

public class Mahasiswa extends Koneksi{
    String URL = "http://10.0.2.2/P6_MySQL1/server.php";
    String url = "";
    String response = "";

    public String tampilMahasiswa() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Mahasiswa: " + url);
            response = call(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String insertMahasiswa(String npm, String nama, String kelas) {
        try {
            nama = nama.replace(" ", "%20");
            url = URL + "?operasi=insert&npm=" + npm + "&nama=" + nama + "&kelas=" + kelas;
            System.out.println("URL Insert Mahasiswa: " + url);
            response = call(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getMahasiswaById(int id) {
        try {
            url = URL + "?operasi=get_mahasiswa_by_id&id=" + id;
            System.out.println("URL Get Mahasiswa By ID: " + url);
            response = call(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String updateMahasiswa(String id, String npm, String nama, String kelas) {
        try {
            nama = nama.replace(" ", "%20");
            url = URL + "?operasi=update&id=" + id + "&npm=" + npm + "&nama=" + nama + "&kelas=" + kelas;
            System.out.println("URL Update Mahasiswa: " + url);
            response = call(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String deleteMahasiswa(int id) {
        try {
            url = URL + "?operasi=delete&id=" + id;
            System.out.println("URL Hapus Mahasiswa: " + url);
            response = call(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

