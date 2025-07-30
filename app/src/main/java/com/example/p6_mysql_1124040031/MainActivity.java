package com.example.p6_mysql_1124040031;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Mahasiswa mahasiswa = new Mahasiswa();
    TableLayout tbMahasiswa;
    Button btTambahMahasiswa, btRefreshDataMahasiswa;
    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();
    JSONArray arrayMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tbMahasiswa = (TableLayout) findViewById(R.id.tbMahasiswa);
        btTambahMahasiswa = (Button) findViewById(R.id.btTambahMahasiswa);
        btRefreshDataMahasiswa = (Button) findViewById(R.id.btRefreshDataMahasiswa);


        btTambahMahasiswa.setOnClickListener(this);
        btRefreshDataMahasiswa.setOnClickListener(this);

        tampildataMahasiswa();
    }

    public void tampildataMahasiswa() {

        while(tbMahasiswa.getChildCount() > 1){
            tbMahasiswa.removeViewAt(1);
        }

        buttonEdit.clear();
        buttonDelete.clear();

        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderNpm = new TextView(this);
        TextView viewHeaderNama = new TextView(this);
        TextView viewHeaderKelas = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        viewHeaderNpm.setText("NPM");
        viewHeaderNama.setText("Nama");
        viewHeaderKelas.setText("Kelas");
        viewHeaderAction.setText("Action");

        viewHeaderNpm.setPadding(5, 1, 5, 1);
        viewHeaderNama.setPadding(5, 1, 5, 1);
        viewHeaderKelas.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderNpm);
        barisTabel.addView(viewHeaderNama);
        barisTabel.addView(viewHeaderKelas);
        barisTabel.addView(viewHeaderAction);

        tbMahasiswa.addView(barisTabel, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        try {
            arrayMahasiswa = new JSONArray(mahasiswa.tampilMahasiswa());
            for (int i = 0; i < arrayMahasiswa.length(); i++) {
                JSONObject jsonChildNode = arrayMahasiswa.getJSONObject(i);
                String id = jsonChildNode.optString("id");
                String npm = jsonChildNode.optString("npm");
                String nama = jsonChildNode.optString("nama");
                String kelas = jsonChildNode.optString("kelas");

                System.out.println("ID: " + id);
                System.out.println("NPM: " + npm);
                System.out.println("Nama: " + nama);
                System.out.println("Kelas: " + kelas);

                barisTabel = new TableRow(this);
                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewNpm = new TextView(this);
                viewNpm.setText(npm);
                viewNpm.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNpm);

                TextView viewNama = new TextView(this);
                viewNama.setText(nama);
                viewNama.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNama);

                TextView viewKelas = new TextView(this);
                viewKelas.setText(kelas);
                viewKelas.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewKelas);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));

                tbMahasiswa.addView(barisTabel, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void tambahMahasiswa() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editNpm = new EditText(this);
        editNpm.setHint("NPM");
        layoutInput.addView(editNpm);

        final EditText editNama = new EditText(this);
        editNama.setHint("Nama");
        layoutInput.addView(editNama);

        final EditText editKelas = new EditText(this);
        editKelas.setHint("Kelas");
        layoutInput.addView(editKelas);

        AlertDialog.Builder builderInsertMahasiswa = new AlertDialog.Builder(this);
        builderInsertMahasiswa.setTitle("Insert Mahasiswa");
        builderInsertMahasiswa.setView(layoutInput);
        builderInsertMahasiswa.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String npm = editNpm.getText().toString();
                String nama = editNama.getText().toString();
                String kelas = editKelas.getText().toString();

                System.out.println("NPM: " + npm + " Nama: " + nama + " Kelas: " + kelas);
                String laporan = mahasiswa.insertMahasiswa(npm, nama, kelas);
                Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
            }
        });

        builderInsertMahasiswa.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertMahasiswa.show();
    }

    public void getDataByID(int id) {
        String npmEdit = null;
        String namaEdit = null;
        String kelasEdit = null;
        JSONArray arrayPersonal;

        try {
            arrayPersonal = new JSONArray(mahasiswa.getMahasiswaById(id));
            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                npmEdit = jsonChildNode.optString("npm");
                namaEdit = jsonChildNode.optString("nama");
                kelasEdit = jsonChildNode.optString("kelas");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editNpm = new EditText(this);
        editNpm.setText(npmEdit);
        layoutInput.addView(editNpm);

        final EditText editNama = new EditText(this);
        editNama.setText(namaEdit);
        layoutInput.addView(editNama);

        final EditText editKelas = new EditText(this);
        editKelas.setText(kelasEdit);
        layoutInput.addView(editKelas);

        AlertDialog.Builder builderEditMahasiswa = new AlertDialog.Builder(this);
        builderEditMahasiswa.setTitle("Update Mahasiswa");
        builderEditMahasiswa.setView(layoutInput);
        builderEditMahasiswa.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String npm = editNpm.getText().toString();
                String nama = editNama.getText().toString();
                String kelas = editKelas.getText().toString();

                String laporan = mahasiswa.updateMahasiswa(viewId.getText().toString(), npm, nama, kelas);
                Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
            }
        });

        builderEditMahasiswa.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderEditMahasiswa.show();
    }

    public void deleteMahasiswa(int id) {
        mahasiswa.deleteMahasiswa(id);
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btTambahMahasiswa){
            tambahMahasiswa();
        } else if (view.getId() == R.id.btRefreshDataMahasiswa){
            finish();
            startActivity(getIntent());
        } else {
            for (int i = 0; i < buttonEdit.size(); i++) {
                if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);
                } else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                    int id = buttonDelete.get(i).getId();
                    deleteMahasiswa(id);
                }
            }
        }
    }
}