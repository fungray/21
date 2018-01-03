package com.mingrisoft.activity.a21.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.InaccountDAO;
import com.mingrisoft.activity.a21.dao.OutaccountDAO;
import com.mingrisoft.activity.a21.model.Tb_inaccount;
import com.mingrisoft.activity.a21.model.Tb_outaccount;

/**
 * Created by apple on 2017/12/29.
 */

public class InfoManage extends AppCompatActivity implements View.OnClickListener {
    private static final int DATE_DIALOG_ID = 0;
    private TextView tvtitle, textView;
    private EditText txtMoney, txtTime, txtHA, txtMark;
    private Spinner spType;
    private Button btnEdit, btnDel;
    private String[] strInfos;
    private String strid, strType;
    private int mYear;
    private int mMonth;
    private int mDay;
    private OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);
    private InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.infomanage);
        Intent intent = getIntent();                      //创建Intent对象
        Bundle bundle = intent.getExtras();               //获取传入的数据，并使用Bundle记录
        initView();
        strInfos = bundle.getStringArray(Showinfo.FLAG);//获取Bundle中记录的信息
        strid = strInfos[0];                           //记录id
        strType = strInfos[1];                         //记录类型
        if(strType.equals("btnoutinfo")){
            tvtitle.setText("支出管理");
            textView.setText("地   点：");
            //根据编号查找支出信息，并存储到Tb_outaccount对象中
            Tb_outaccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));
            txtTime.setText(tb_outaccount.getTime());
            spType.setPrompt(tb_outaccount.getType());
            txtHA.setText(tb_outaccount.getAddress());
            txtMark.setText(tb_outaccount.getMark());
        }else if(strType.equals("btnininfo")){
            tvtitle.setText("收入管理");
            textView.setText("付款方：");
            //根据编号查找支出信息，并存储到Tb_outaccount对象中
            Tb_inaccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));
            txtTime.setText(tb_inaccount.getTime());
            spType.setPrompt(tb_inaccount.getType());
            txtHA.setText(tb_inaccount.getHandler());
            txtMark.setText(tb_inaccount.getMark());
        }

    }

    private void initView(){
        tvtitle = (TextView) findViewById(R.id.inouttitle);
        textView = (TextView) findViewById(R.id.tvInOut);
        txtMoney = (EditText) findViewById(R.id.txtInOutMoney);
        txtTime = (EditText) findViewById(R.id.txtInOutTime);
        spType = (Spinner) findViewById(R.id.spInOutType);
        txtHA = (EditText) findViewById(R.id.txtInOut);
        txtMark = (EditText) findViewById(R.id.txtInOutMark);
        btnEdit = (Button) findViewById(R.id.btnInOutEdit);
        btnDel = (Button) findViewById(R.id.btnInOutDelete);
        btnEdit.setOnClickListener(this);
        btnDel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnInOutEdit:
                if(strType.equals("btnoutinfo")){
                    Tb_outaccount tb_outaccount = new Tb_outaccount();
                    tb_outaccount.set_id(Integer.parseInt(strid));
                    tb_outaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
                    tb_outaccount.setTime(txtTime.getText().toString());
                    tb_outaccount.setType(spType.getSelectedItem().toString());
                    tb_outaccount.setAddress(txtHA.getText().toString());
                    tb_outaccount.setMark(txtMark.getText().toString());
                    outaccountDAO.update(tb_outaccount);
                }else if(strType.equals("btnininfo")){
                    Tb_inaccount tb_inaccount = new Tb_inaccount();
                    tb_inaccount.set_id(Integer.parseInt(strid));
                    tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
                    tb_inaccount.setTime(txtTime.getText().toString());
                    tb_inaccount.setType(spType.getSelectedItem().toString());
                    tb_inaccount.setHandler(txtHA.getText().toString());
                    tb_inaccount.setMark(txtMark.getText().toString());
                    inaccountDAO.update(tb_inaccount);
                }
                Toast.makeText(InfoManage.this, "【数据】修改成功!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnInOutDelete:
                if(strType.equals("btnoutinfo")){
                    outaccountDAO.delete(Integer.parseInt(strid));
                }else if(strType.equals("btnininfo")){
                    inaccountDAO.delete(Integer.parseInt(strid));
                }
                Toast.makeText(InfoManage.this, "【数据】删除成功!", Toast.LENGTH_SHORT).show();
        }
    }


}
