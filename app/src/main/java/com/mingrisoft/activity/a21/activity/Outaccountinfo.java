package com.mingrisoft.activity.a21.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mingrisoft.activity.a21.R;
import com.mingrisoft.activity.a21.dao.OutaccountDAO;
import com.mingrisoft.activity.a21.model.Tb_outaccount;

import java.util.List;

/**
 * Created by apple on 2017/12/29.
 */

public class Outaccountinfo extends AppCompatActivity {
    private static final String FLAG = "id";
    private ListView lvinfo;
    private String strType = "";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.outaccountinfo);
        lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);
        showInfo(R.id.btnoutinfo);                  //调用自定义方法显示收入信息
        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());                      //记录收入信息
                String strid = strInfo.substring(0, strInfo.indexOf('|'));                        //从收入信息中截取收入编号
                Intent intent = new Intent(Outaccountinfo.this, InfoManage.class);//创建Intent对象
                intent.putExtra(FLAG, new String[]{ strid, strType });                           //设置传递数据
                startActivity(intent);                                                              //执行Intent操作
            }
        });
    }

    private void showInfo(int intType){               //用来根据传入的管理类型，显示相应的信息
        String[] strInfos = null;                       //自定义自负床数组，用来存储收入信息
        ArrayAdapter<String> arrayAdapter = null;       //创建ArrayAdapter对象
        strType = "btnoutinfo";                       //为strType变量赋值
        OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this);//创建InaccountDAO对象
        //获取所有收入信息，并存储到List泛型集合中
        List<Tb_outaccount> listinfos = outaccountinfo.getScrollData(0,(int) outaccountinfo.getCount());
        strInfos = new String[listinfos.size()];      //设置字符串长度
        int m = 0;                                    //定义一个开始标识
        for(Tb_outaccount tb_outaccount : listinfos){
            //将收入相关信息组长一个字符串，存储到字符串数字的相应位置
            strInfos[m] = tb_outaccount.get_id() + "|" + tb_outaccount.getType() + " " + String.valueOf(tb_outaccount.getMoney())
                    + "元           " + tb_outaccount.getTime();
            m++;                                                       //标识加1
        }
        //使用字符串数组初始化ArrayAdapter对象
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);               //为ListView列表设置数据源
    }
}
