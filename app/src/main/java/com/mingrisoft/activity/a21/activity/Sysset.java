package com.mingrisoft.activity.a21.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.PwdDAO;
import com.mingrisoft.activity.a21.model.Tb_pwd;

/**
 * Created by apple on 2017/12/29.
 */

public class Sysset extends AppCompatActivity implements View.OnClickListener {
    private EditText txtpwd;
    private Button btnSet, btnsetCancel;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.sysset);
        initView();
    }

    private void initView(){
        txtpwd = (EditText) findViewById(R.id.txtPwd);
        btnSet = (Button) findViewById(R.id.btnSet);
        btnsetCancel = (Button) findViewById(R.id.btnsetCancel);

        btnSet.setOnClickListener(this);
        btnsetCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSet:
                PwdDAO pwdDAO = new PwdDAO(Sysset.this);
                Tb_pwd tb_pwd = new Tb_pwd(txtpwd.getText().toString());
                if(pwdDAO.getCount() == 0){
                    pwdDAO.add(tb_pwd);
                }else{
                    pwdDAO.update(tb_pwd);
                }
                Toast.makeText(Sysset.this, "【密码】设置成功!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnsetCancel:
                txtpwd.setText("");
                txtpwd.setHint("请输入密码");
        }
    }
}
