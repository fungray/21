package com.mingrisoft.activity.a21.activity;

import android.content.Intent;
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
 * Created by apple on 2018/1/2.
 */

public class FlagManage extends AppCompatActivity implements View.OnClickListener {
    private EditText txtFlag;
    private Button btnEdit, btnDel;
    private String strid;
    private FlagDAO flagDAO = new FlagDAO(FlagManage.this);
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.flagmanage);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        initView();
        strid = bundle.getString(Showinfo.FLAG);
        final FlagDAO flagDAO = new FlagDAO(FlagManage.this);
        txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());
    }

    private void initView(){
        txtFlag = (EditText) findViewById(R.id.txtFlagManage);
        btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);
        btnDel = (Button) findViewById(R.id.btnFlagManageDelete);

        btnEdit.setOnClickListener(this);
        btnDel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFlagManageEdit:
                Tb_flag tb_flag = new Tb_flag();
                tb_flag.set_id(Integer.parseInt(strid));
                tb_flag.setFlag(txtFlag.getText().toString());
                flagDAO.update(tb_flag);
                Toast.makeText(FlagManage.this, "【便签数据】修改成功!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnFlagManageDelete:
                flagDAO.delete(Integer.parseInt(strid));
                Toast.makeText(FlagManage.this, "【便签数据】删除成功!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
