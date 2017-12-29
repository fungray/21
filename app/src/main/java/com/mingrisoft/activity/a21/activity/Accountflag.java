package com.mingrisoft.activity.a21.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.FlagDAO;
import com.mingrisoft.activity.a21.model.Tb_flag;

/**
 * Created by apple on 2017/12/29.
 */

class Accountflag extends AppCompatActivity implements View.OnClickListener {
    private EditText txtFlag;
    private Button btnflagSave;
    private Button btnflagCancel;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.accountflag);
        initView();
    }

    private void initView(){
        txtFlag = (EditText) findViewById(R.id.txtFlag);
        btnflagSave = (Button) findViewById(R.id.btnflagSave);
        btnflagCancel = (Button) findViewById(R.id.btnflagCancel);
        btnflagSave.setOnClickListener(this);
        btnflagCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnflagSave:
                String strFlag = txtFlag.getText().toString();
                if(!strFlag.isEmpty()){
                    FlagDAO flagDAO = new FlagDAO(Accountflag.this);
                    Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId()+1, strFlag);
                    flagDAO.add(tb_flag);
                    Toast.makeText(Accountflag.this,"【新增便签】数据添加成功!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Accountflag.this,"请输入便签!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnflagCancel:
                txtFlag.setText("");
        }
    }
}
