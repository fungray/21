package com.mingrisoft.activity.a21.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.PwdDAO;

/**
 * Created by apple on 2017/12/27.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtlogin;
    private Button btnlogin;
    private Button btnclose;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    private void initView(){
        txtlogin = (EditText) findViewById(R.id.txtLogin);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        btnclose = (Button) findViewById(R.id.btnClose);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                PwdDAO pwdDAO = new PwdDAO(LoginActivity.this);
                if((pwdDAO.getCount()==0 || pwdDAO.find().getPassword().isEmpty()) && txtlogin.getText().toString().isEmpty()){
                    startActivity(intent);
                }else{
                    if(pwdDAO.find().getPassword().equals(txtlogin.getText().toString())){
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"请输入正确的密码!",Toast.LENGTH_SHORT).show();
                    }
                }
                txtlogin.setText("");
                break;
            case R.id.btnClose:
                finish();
                break;
        }
    }

}
