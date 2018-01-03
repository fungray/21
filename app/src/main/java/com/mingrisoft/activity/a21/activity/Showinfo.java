package com.mingrisoft.activity.a21.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.FlagDAO;
import com.mingrisoft.activity.a21.dao.InaccountDAO;
import com.mingrisoft.activity.a21.dao.OutaccountDAO;
import com.mingrisoft.activity.a21.model.Tb_flag;
import com.mingrisoft.activity.a21.model.Tb_inaccount;
import com.mingrisoft.activity.a21.model.Tb_outaccount;

import java.util.List;

/**
 * Created by apple on 2017/12/29.
 */

public class Showinfo extends AppCompatActivity implements View.OnClickListener {
    private Button btnflaginfo,btnoutinfo,btnininfo;
    private ListView lvinfo;
    private String strType;
    public static final String FLAG="id";
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.showinfo);
        initView();
    }

    private void initView() {
        btnflaginfo = (Button) findViewById(R.id.btnflaginfo);
        btnoutinfo = (Button) findViewById(R.id.btnoutinfo);
        btnininfo = (Button) findViewById(R.id.btnininfo);
        lvinfo = (ListView) findViewById(R.id.lvinfo);
        btnflaginfo.setOnClickListener(this);
        btnininfo.setOnClickListener(this);
        btnoutinfo.setOnClickListener(this);
        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strInfo = String.valueOf(((TextView) view).getText());
                String strid = strInfo.substring(0, strInfo.indexOf('|'));
                Intent intent = null;
                if(strType == "btnoutinfo" || strType == "btnininfo"){
                    intent = new Intent(Showinfo.this, InfoManage.class);
                    intent.putExtra(FLAG,new String[]{strid, strType});
                }else if(strType == "btnflaginfo"){
                    intent = new Intent(Showinfo.this, FlagManage.class);
                    intent.putExtra(FLAG, strid);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnininfo:
                showInfo(R.id.btnininfo);
                break;
            case R.id.btnoutinfo:
                showInfo(R.id.btnoutinfo);
                break;
            case R.id.btnflaginfo:
                showInfo(R.id.btnflaginfo);
                break;
        }
    }

    private void showInfo(int intType){
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        switch (intType){
            case R.id.btnoutinfo:
                strType = "btnoutinfo";
                OutaccountDAO outaccountinfo = new OutaccountDAO(Showinfo.this);
                List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,(int) outaccountinfo.getCount());
                strInfos = new String[listoutinfos.size()];
                int i = 0;
                for (Tb_outaccount tb_outaccount : listoutinfos){
                    strInfos[i] = tb_outaccount.get_id()+"|"+tb_outaccount.getType()+" "+String.valueOf(tb_outaccount.getMoney())+
                            "元   "+tb_outaccount.getTime();
                    i++;
                }
                break;
            case R.id.btnininfo:
                strType = "btnininfo";
                InaccountDAO inaccountinfo = new InaccountDAO(Showinfo.this);
                List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0, (int) inaccountinfo.getCount());
                strInfos = new String[listinfos.size()];
                int m = 0;
                for(Tb_inaccount tb_inaccount : listinfos){
                    strInfos[m] = tb_inaccount.get_id()+"|"+tb_inaccount.getType()+" "+String.valueOf(tb_inaccount.getMoney())+
                            "元   "+tb_inaccount.getTime();
                    m++;
                }
                break;
            case R.id.btnflaginfo:
                strType = "btnflaginfo";
                FlagDAO flaginfo = new FlagDAO(Showinfo.this);
                List<Tb_flag> listFlags = flaginfo.getScrollData(0,(int) flaginfo.getCount());
                strInfos = new String[listFlags.size()];
                int n = 0;
                for(Tb_flag tb_flag : listFlags){
                    strInfos[n] = tb_flag.get_id()+"|"+tb_flag.getFlag();
                    if(strInfos[n].length() > 15){
                        strInfos[n] = strInfos[n].substring(0,15) + "......";
                    }
                    n++;
                }
                break;
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strInfos);
        lvinfo.setAdapter(arrayAdapter);
    }

}
