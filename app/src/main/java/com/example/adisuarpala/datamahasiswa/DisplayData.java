package com.example.adisuarpala.datamahasiswa;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class DisplayData extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DBHelper db_mahasiswa;

    TextView nama, nim, jurusan, notlp;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        nama = (TextView)findViewById(R.id.editNama);
        nim = (TextView)findViewById(R.id.editNIM);
        jurusan = (TextView)findViewById(R.id.editJurusan);
        notlp = (TextView)findViewById(R.id.editNotlp);

        db_mahasiswa = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int Value = extras.getInt("id");
            if (Value > 0){
                Cursor rs = db_mahasiswa.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String na = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAMA));
                String nm = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NIM));
                String jn = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_JURUSAN));
                String nt = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NOTLP));

                if (!rs.isClosed()) {
                    rs.close();
                }

                Button btnSave = (Button)findViewById(R.id.btnSave);
                btnSave.setVisibility(View.INVISIBLE);

                nama.setText((CharSequence)na);
                nama.setFocusable(false);
                nama.setClickable(false);

                nim.setText((CharSequence)nm);
                nim.setFocusable(false);
                nim.setClickable(false);

                jurusan.setText((CharSequence)jn);
                jurusan.setFocusable(false);
                jurusan.setClickable(false);

                notlp.setText((CharSequence)nt);
                notlp.setFocusable(false);
                notlp.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_data, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.Edit_Data:
                Button btnSave = (Button)findViewById(R.id.btnSave);
                btnSave.setVisibility(View.INVISIBLE);

                nama.setEnabled(true);
                nama.setFocusableInTouchMode(true);
                nama.setClickable(true);

                nim.setEnabled(true);
                nim.setFocusableInTouchMode(true);
                nim.setClickable(true);

                jurusan.setEnabled(true);
                jurusan.setFocusableInTouchMode(true);
                jurusan.setClickable(true);

                notlp.setEnabled(true);
                notlp.setFocusableInTouchMode(true);
                notlp.setClickable(true);

                return true;
            case R.id.Delete_Data:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteData)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                db_mahasiswa.deleteData(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d =builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
                default: return super.onOptionsItemSelected(item);

            }
        }

    public void run(View view){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                if (db_mahasiswa.updateData(id_To_Update,
                        nama.getText().toString(),
                        nim.getText().toString(),
                        jurusan.getText().toString(),
                        notlp.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data not Updated", Toast.LENGTH_LONG).show();

                }
            }
            else{
                if (db_mahasiswa.insertData(
                        nama.getText().toString(),
                        nim.getText().toString(),
                        jurusan.getText().toString(),
                        notlp.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data not Saved", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
