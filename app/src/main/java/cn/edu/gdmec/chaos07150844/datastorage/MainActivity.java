package cn.edu.gdmec.chaos07150844.datastorage;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText userName,passWord;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        tv1 = (TextView) findViewById(R.id.textView);

    }
    public void spWrite(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("account",userName.getText().toString());
        editor.putString("pass",passWord.getText().toString());
        editor.commit();
        Toast.makeText(this,"ShardPreferences写入成功",Toast.LENGTH_LONG).show();
    }
    public void spRead(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_PRIVATE);
        String acount = user.getString("account","没有这个键值");
        String pass = user.getString("pass","没有这个键值");
        tv1.setText("账号:"+acount+"\n"+"密码:"+pass);
        Toast.makeText(this,"ShardPreferences读取成功",Toast.LENGTH_LONG).show();
    }
    public void ROMWrite(View v){
        String account = userName.getText().toString();
        String pass = passWord.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(account+":"+pass);
            bw.flush();
            fos.close();
            Toast.makeText(this,"ROM写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ROMRead(View v){
        String account = userName.getText().toString();
        String pass = passWord.getText().toString();
        try {
            FileInputStream fis = openFileInput("user.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str=br.readLine())!=null){
                sb.append(str+"\n");
            }
            fis.close();
            tv1.setText(sb);
            Toast.makeText(this,"ROM读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDWrite(View v){
        String str = userName.getText().toString()+":"+passWord.getText().toString();
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/test.txt";
        System.out.println(filename);
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this,"SD卡写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void SDRead(View v){
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/test.txt";
        System.out.println(filename);
        File file = new File(filename);
        int length = (int) file.length();
        byte[] b = new byte[length];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            tv1.setText(new String(b));
            Toast.makeText(this,"SD卡读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
