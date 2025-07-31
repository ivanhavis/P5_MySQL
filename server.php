<?php
$server = "localhost";
$username = "root";
$password = "";
$database = "db_android_1";

$koneksi = mysqli_connect($server, $username, $password, $database);

if (!$koneksi) {
    die("Connection failed: " . mysqli_connect_error());
}

@$operasi = $_GET['operasi'];

switch ($operasi) {
    case "view":
        /* Source code untuk Menampilkan mahasiswa */
        $query_tampil_mahasiswa = mysqli_query($koneksi, "SELECT * FROM mahasiswa") or die(mysqli_error($koneksi));
        $data_array = array();
        while ($data = mysqli_fetch_assoc($query_tampil_mahasiswa)) {
            $data_array[] = $data;
        }
        echo json_encode($data_array);
        break;

  case "insert":
    @$npm = $_GET['npm'];
    @$nama = $_GET['nama'];
    @$kelas = $_GET['kelas'];
    $query_insert_data = mysqli_query($koneksi, "INSERT INTO mahasiswa (npm, nama, kelas) VALUES('$npm', '$nama', '$kelas')");
    
    if ($query_insert_data) {
        // Send a JSON success response
        echo json_encode([
            'status' => 'success',
            'message' => 'Data Berhasil Disimpan'
        ]);
    } else {
        // Send a JSON error response
        echo json_encode([
            'status' => 'error',
            'message' => 'Error: ' . mysqli_error($koneksi)
        ]);
    }
    break;

    case "get_mahasiswa_by_id":
        /* Source code untuk Edit data dan mengirim data berdasarkan id yang diminta */
        @$id = $_GET['id'];
        $query_tampil_mahasiswa = mysqli_query($koneksi, "SELECT * FROM mahasiswa WHERE id='$id'") or die(mysqli_error($koneksi));
        $data_array = array();
        $data_array = mysqli_fetch_assoc($query_tampil_mahasiswa);
        echo "[" . json_encode($data_array) . "]";
        break;

    case "update":
        /* Source code untuk Update data */
        @$npm = $_GET['npm'];
        @$nama = $_GET['nama'];
        @$kelas = $_GET['kelas'];
        @$id = $_GET['id'];
        $query_update_mahasiswa = mysqli_query($koneksi, "UPDATE mahasiswa SET npm='$npm', nama='$nama', kelas='$kelas' WHERE id='$id'");
        if ($query_update_mahasiswa) {
            echo "Update Data Berhasil";
        } else {
            echo mysqli_error($koneksi);
        }
        break;

    case "delete":
        /* Source code untuk Delete data */
        @$id = $_GET['id'];
        $query_delete_mahasiswa = mysqli_query($koneksi, "DELETE FROM mahasiswa WHERE id='$id'");
        if ($query_delete_mahasiswa) {
            echo "Delete Data Berhasil";
        } else {
            echo mysqli_error($koneksi);
        }
        break;

    default:
        break;
}
?>