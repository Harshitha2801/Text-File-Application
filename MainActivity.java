package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button open_btn, create_btn, save_btn;
    EditText user_text;
    TextView Load_text;
    String filename= "";
    String filepath ="";
    String fileContent="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_btn = findViewById(R.id.save_button);
        open_btn=findViewById(R.id.open_button);
        create_btn=findViewById(R.id.create_button);
        user_text=findViewById(R.id.text_box);
        Load_text = findViewById(R.id.Load_text);
        filename="myFile1.txt";
        filepath="MyFileDir";

        if(!isExternalStorageAvailableForRW()){
            save_btn.setEnabled(false);
        }



        create_btn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                if(!fileContent.equals("")){
                    Toast.makeText(MainActivity.this,"Text file already created",Toast.LENGTH_SHORT).show();
                }
                else {
                    fileContent = user_text.getText().toString().trim();
                    if (fileContent.equals("")) {
                        Toast.makeText(MainActivity.this, "Text file cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myExternalFile);
                            fos.write(fileContent.getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        user_text.setText("");
                        Toast.makeText(MainActivity.this, "Text file created", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(fileContent.equals("")){
                    Toast.makeText(MainActivity.this,"Text file not created",Toast.LENGTH_SHORT).show();
                }

                else{
                    Load_text.setText("");
                    fileContent=user_text.getText().toString().trim();
                    if(fileContent.equals("")){
                        Toast.makeText(MainActivity.this,"Text file cannot be empty",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        File myExternalFile = new File(getExternalFilesDir(filepath),filename);
                        FileOutputStream fos=null;
                        try{
                            fos=new FileOutputStream(myExternalFile);
                            fos.write(fileContent.getBytes());
                        }
                        catch(FileNotFoundException e){
                            e.printStackTrace();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        user_text.setText("");
                        Toast.makeText(MainActivity.this,"Info saved to Sd card",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        open_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                FileReader file_reader=null;
                File myExternalFile = new File(getExternalFilesDir(filepath),filename);
                StringBuilder stringBuilder = new StringBuilder();

                try{
                    file_reader= new FileReader(myExternalFile);
                    BufferedReader br = new BufferedReader(file_reader);
                    String line = br.readLine();
                    while(line!=null) {
                        stringBuilder.append(line).append("\n");
                        line=br.readLine();
                    }

                }catch(FileNotFoundException e){
                    //e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Exception"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }catch(IOException e){
                    Toast.makeText(MainActivity.this,"Exception"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                finally {
                    String fileContents = "File Contents\n" + stringBuilder.toString();
                    Load_text.setText(fileContents);
                }
            }
        });
    }
    private boolean isExternalStorageAvailableForRW(){
        String extStorageState = Environment.getExternalStorageState();
        if(extStorageState.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
}
