package com.ytu.examine.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddQuestionActivity extends AppCompatActivity {
    EditText soruBasligi;
    Button soruKaydetButtonu;
    String soruBaslikText;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        soruBasligi = findViewById(R.id.soru_baslik);
        soruKaydetButtonu = findViewById(R.id.kaydet_soru_button);
        soruKaydetButtonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soruBaslikText = soruBasligi.getText().toString().trim();
                if (soruBaslikText.isEmpty()){
                    Toast.makeText(AddQuestionActivity.this, "Lütfen Soru Başlığını Giriniz!",Toast.LENGTH_SHORT).show();
                } else {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);
                        } else {
                            saveToTxtFile(soruBaslikText);
                        }
                    }
                    else {
                        saveToTxtFile(soruBaslikText);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveToTxtFile(soruBaslikText);
                } else {
                    Toast.makeText(this, "Dosyaya yazma izni gerekli!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveToTxtFile(String soruBaslikText) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/EXAMINE/");
            dir.mkdir();
            String fileName = "Examine_" + timeStamp + ".txt";
            File file =  new File(dir, fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(soruBaslikText);
            bw.close();

            Toast.makeText(this, fileName+" başarıyla "+dir+" adresinde kaydedildi \n",Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
}