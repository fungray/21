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

import com.mingrisoft.activity.a21.dao.InaccountDAO;
import com.mingrisoft.activity.a21.model.Tb_inaccount;

import java.util.Calendar;

/**
 * Created by apple on 2017/12/29.
 */

public class AddInaccount extends AppCompatActivity implements View.OnClickListener {
    private static final int DATE_DIALOG_ID = 0;
    private EditText txtInMoney, txtInTime, txtInHandler, txtInMark;
    private Spinner spInType;
    private Button btnInSaveButton;
    private Button btnInCancelButton;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.AddInaccount);
        txtInMoney = (EditText) findViewById(R.id.txtInMoney);
        txtInTime = (EditText) findViewById(R.id.txtInTime);
        txtInHandler = (EditText) findViewById(R.id.txtInHandler);
        txtInMark = (EditText) findViewById(R.id.txtInMark);
        spInType = (Spinner) findViewById(R.id.spInType);
        btnInSaveButton = (Button) findViewById(R.id.btnInSave);
        btnInCancelButton = (Button) findViewById(R.id.btnInCancel);

        txtInTime.setOnClickListener(this);
        btnInSaveButton.setOnClickListener(this);
        btnInCancelButton.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtInTime:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.btnInSave:
                String strInMoney = txtInMoney.getText().toString();         //获取金额文本框的值
                if(!strInMoney.isEmpty()){                                    //判断金额不为空
                    //创建InaccountDAO对象
                    InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
                    //创建Tb_inaccount对象
                    Tb_inaccount tb_inaccount = new Tb_inaccount(inaccountDAO.getMaxId()+1, Double.parseDouble(strInMoney),
                            txtInTime.getText().toString(), spInType.getSelectedItem().toString(), txtInHandler.getText().toString(),
                            txtInMark.getText().toString());
                    inaccountDAO.add(tb_inaccount);                         //添加收入信息
                    //弹出信息提示
                    Toast.makeText(AddInaccount.this, "【新增收入】数据添加成功!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddInaccount.this, "请输入收入金额!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnInCancel:
                    txtInMoney.setText("");
                    txtInMoney.setHint("0.00");
                    txtInTime.setText("");
                    txtInTime.setHint("2011-01-01");
                    txtInHandler.setText("");
                    txtInMark.setText("");
                    spInType.setSelection(0);//设置类别下拉列表默认选择第一项
                break;
            }
        }

    private void updateDisplay(){
        txtInTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
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
