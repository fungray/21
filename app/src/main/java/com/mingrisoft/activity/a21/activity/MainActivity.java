package com.mingrisoft.activity.a21.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingrisoft.activity.a21.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gvInfo;
    private String[] titles = new String[]{"新增支出","新增收入","我的支出","我的收入","数据管理","系统设置","收支便签"," 退出"};
    private int[] images = new int[]{R.drawable.addoutaccount, R.drawable.addinaccount, R.drawable.outaccountinfo,
                            R.drawable.inaccountinfo, R.drawable.showinfo, R.drawable.sysset, R.drawable.accountflag, R.drawable.exit};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gvInfo = (GridView) findViewById(R.id.gvInfo); //获取布局文件中的gvInfo组件
        PictureAdapter adapter = new PictureAdapter(titles,images,this);
        gvInfo.setAdapter(adapter);
        gvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                switch (i){
                    case 0:
                        intent = new Intent(MainActivity.this, AddOutaccount.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, AddInaccount.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, Outaccountinfo.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, Inaccountinfo.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, Showinfo.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, Sysset.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, Accountflag.class);
                        startActivity(intent);
                        break;
                    case 7:
                        finish();
                        break;
                }
            }
        });
    }

    class ViewHolder{
        public TextView title;
        public ImageView image;
    }

    class Picture{
        private String title;
        private int imageId;
        public Picture(){
            super();
        }
        public Picture(String title,int imageId){
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }


    class PictureAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<Picture> pictures;

        public PictureAdapter(String[] titles, int[] images, Context context){
            super();
            pictures = new ArrayList<Picture>();
            inflater = LayoutInflater.from(context);
            for(int i=0; i<images.length; i++){
                Picture picture = new Picture(titles[i], images[i]);
                pictures.add(picture);
            }
        }

        @Override
        public int getCount() {
            if(pictures != null){
                return  pictures.size();
            }else{
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return pictures.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;                                           //创建ViewHolder对象
            if(view == null){                                               // 判断图像标识是否为空
                view = inflater.inflate(R.layout.gvitem, null);    //设置图像标识
                viewHolder = new ViewHolder();                              //初始化ViewHolder对象
                viewHolder.title = (TextView) view.findViewById(R.id.ItemTitle);//设置图像标题
                viewHolder.image = (ImageView) view.findViewById(R.id.ItemImage);//设置图像的二进制值
                view.setTag(viewHolder);                                     //设置提示
            }else{
                viewHolder = (ViewHolder) view.getTag();                     //设置提示
            }
            viewHolder.title.setText(pictures.get(i).getTitle());          //设置图像的标题
            viewHolder.image.setImageResource(pictures.get(i).getImageId());//设置图像的二进制值
            return view;                                                     //返回图像的标识
        }
    }

}
