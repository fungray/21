package com.mingrisoft.activity.a21.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.OutaccountDAO;
import com.mingrisoft.activity.a21.model.Tb_inaccount;
import com.mingrisoft.activity.a21.model.Tb_outaccount;

import java.util.Calendar;

/**
 * Created by apple on 2017/12/29.
 */

public class AddOutaccount extends AppCompatActivity implements View.OnClickListener {
    private static final int DATE_DIALOG_ID = 0;
    private EditText txtOutMoney, txtOutTime, txtOutAddress, txtOutMark;
    private Spinner spOutType;
    private Button btnOutSaveButton;
    private Button btnOutCancelButton;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.addoutaccount);
        initView();
        updateDisplay();
    }

    private void initView(){
        txtOutMoney = (EditText) findViewById(R.id.txtOutMoney);
        txtOutTime = (EditText) findViewById(R.id.txtOutTime);
        txtOutAddress = (EditText) findViewById(R.id.txtOutAddress);
        txtOutMark = (EditText) findViewById(R.id.txtOutMark);
        spOutType = (Spinner) findViewById(R.id.spOutType);
        btnOutSaveButton = (Button) findViewById(R.id.btnOutSave);
        btnOutCancelButton = (Button) findViewById(R.id.btnOutCancel);

        txtOutTime.setOnClickListener(this);
        btnOutSaveButton.setOnClickListener(this);
        btnOutCancelButton.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtOutTime:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.btnOutSave:
                String strOutMoney = txtOutMoney.getText().toString();         //获取金额文本框的值
                if(!strOutMoney.isEmpty()){                                    //判断金额不为空
                    //创建InaccountDAO对象
                    OutaccountDAO outaccountDAO = new OutaccountDAO(AddOutaccount.this);
                    //创建Tb_inaccount对象
                    Tb_outaccount tb_outaccount = new Tb_outaccount(outaccountDAO.getMaxId()+1, Double.parseDouble(strOutMoney),
                            txtOutTime.getText().toString(), spOutType.getSelectedItem().toString(), txtOutAddress.getText().toString(),
                            txtOutMark.getText().toString());
                    outaccountDAO.add(tb_outaccount);                         //添加收入信息
                    //弹出信息提示
                    Toast.makeText(AddOutaccount.this, "【新增支出】数据添加成功!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddOutaccount.this, "请输入支出金额!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnOutCancel:
                txtOutMoney.setText("");
                txtOutMoney.setHint("0.00");
                txtOutTime.setText("");
                txtOutTime.setHint("2011-01-01");
                txtOutAddress.setText("");
                txtOutMark.setText("");
                spOutType.setSelection(0);//设置类别下拉列表默认选择第一项
                break;
        }
    }

    private void updateDisplay(){
        txtOutTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:                     //弹出日期选择对话框
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
}
