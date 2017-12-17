package com.example.bosscopeng.shopingmore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.ViewGroup;
import android.content.Intent;

/*
管理商店
 */
public class Shop_MainActivity extends AppCompatActivity {
   EditText editText_shop_name,editText_shop_address,editText_shop_map_address_x,editText_shop_map_address_y,editText_shop_phone;
   Button button_shop_add,button_shop_edit,button_shop_search,button_shop_delet;
    ListView ListView_shop;
    SQLiteDatabase shopdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__main);

        editText_shop_name=(EditText)findViewById(R.id.editText_shop_name);
        editText_shop_address=(EditText)findViewById(R.id.editText_shop_address);
        editText_shop_map_address_x=(EditText)findViewById(R.id.editText_shop_map_address_x);
        editText_shop_map_address_y=(EditText)findViewById(R.id.editText_shop_map_address_y);
        editText_shop_phone=(EditText)findViewById(R.id.editText_shop_phone);
        button_shop_add=(Button)findViewById(R.id.button_shop_add);
        button_shop_edit=(Button)findViewById(R.id. button_shop_edit);
        button_shop_search=(Button)findViewById(R.id. button_shop_search);
        button_shop_delet=(Button)findViewById(R.id.button_shop_delet);
        ShopingMore_db shopdbhelper =new  ShopingMore_db(this);
        shopdb =shopdbhelper.getWritableDatabase();


        button_shop_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newShop();
            }
        });
        button_shop_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                renewShop();
            }
        });
        button_shop_delet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deletShop();
            }
        });
        button_shop_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                queryShop();

            }
        });





    }
    class ShopData{
        String shop_name;
        String shop_address;
        String shop_address_xy ;
        String shop_phone_number ;
        String shop_address_x;
        String shop_address_y;

    }

   public class ShopAdapter extends BaseAdapter{
       private ShopData[] shopData;
       private int view;
       public ShopAdapter(ShopData[] shopData,int view){
           this.shopData=shopData;
           this.view=view;
       }

       @Override
       public int getCount() {return shopData.length;}
       @Override
       public ShopData getItem(int position){return shopData[position];}
       @Override
       public long getItemId(int position){return 0;}
       @Override
       public View getView(final int position , View rowView, ViewGroup parent){

           rowView=getLayoutInflater().inflate(view,parent,false);
           TextView text_shop_name=(TextView)rowView.findViewById(R.id.text_shop_name);
           TextView text_shop_address=(TextView)rowView.findViewById(R.id.text_shop_address);
           TextView text_shop_address_xy=(TextView)rowView.findViewById(R.id.text_shop_address_xy);
           TextView text_shop_phone=(TextView)rowView.findViewById(R.id.text_shop_phone);

           text_shop_name.setText(shopData[position].shop_name);
           text_shop_address_xy.setText(shopData[position].shop_address_xy);
           text_shop_address.setText(shopData[position].shop_address);
           text_shop_phone.setText(shopData[position].shop_phone_number);



           return rowView;
       }
       /*

       public ListView.OnItemClickListener itemListener=new ListView.OnItemClickListener(){

           public void onItemClick(AdapterView<?>parent,View view ,int position,long id){
               Toast.makeText(getApplicationContext(),
                       "點擊layout:" + shopData[position].shop_name, Toast.LENGTH_SHORT).show();

           }
       };  */

   }






    public void newShop(){
         if(editText_shop_name.getText().toString().equals("") || editText_shop_address.getText().toString().equals("")||editText_shop_map_address_x.getText().toString().equals("")||editText_shop_map_address_y.getText().toString().equals("")||editText_shop_phone.getText().toString().equals("")){
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        }else {
            String name=editText_shop_name.getText().toString();

            String address_xy="("+editText_shop_map_address_x.getText().toString()+","+editText_shop_map_address_y.getText().toString()+")";

            String address=editText_shop_address.getText().toString();
            double address_x=Double.parseDouble(editText_shop_map_address_x.getText().toString());
            double address_y=Double.parseDouble(editText_shop_map_address_y.getText().toString());
            int phone=Integer.parseInt(editText_shop_phone.getText().toString());

             /*
                 colum/商店名稱 / 商店地址/ 商店經緯度  / 商店連絡電話//

             db.execSQL("CREATE TABLE shopTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"shop_address  text no null,"+"shop_address_x real no null,"+"shop_address_y real no null,"+"shop_address_xy Text no null,"+"shop_phone_number integer no null)");
             */
             ContentValues cv=new ContentValues();
            cv.put("shop_name",name);
            cv.put("shop_address",address);
            cv.put("shop_address_x",address_x);
            cv.put("shop_address_y",address_y);
            cv.put("shop_address_xy",address_xy);
            cv.put("shop_phone_number",phone);
            shopdb.insert("shopTable",null,cv);

            Toast.makeText(this,"新商店:"+editText_shop_name.getText().toString()+"電話:"+phone,Toast.LENGTH_SHORT).show();

            editText_shop_name.setText("");
            editText_shop_address.setText("");
            editText_shop_map_address_x.setText("");
            editText_shop_map_address_y.setText("");
            editText_shop_phone.setText("");
        }


    }
    public void renewShop(){
        if(editText_shop_name.getText().toString().equals("") || editText_shop_address.getText().toString().equals("")||editText_shop_map_address_x.getText().toString().equals("")||editText_shop_map_address_y.getText().toString().equals("")||editText_shop_phone.getText().toString().equals("")){
            Toast.makeText(this,"沒有輸入更新資料",Toast.LENGTH_SHORT).show();
        }else {

            String newaddress_xy="("+editText_shop_map_address_x.getText().toString()+","+editText_shop_map_address_y.getText().toString()+")";
            String newaddress=editText_shop_address.getText().toString();
            double newaddress_x=Double.parseDouble(editText_shop_map_address_x.getText().toString());
            double newaddress_y=Double.parseDouble(editText_shop_map_address_y.getText().toString());
            int newphone=Integer.parseInt(editText_shop_phone.getText().toString());

            ContentValues cv=new ContentValues();

            cv.put("shop_address",newaddress);
            cv.put("shop_address_x",newaddress_x);
            cv.put("shop_address_y",newaddress_y);
            cv.put("shop_address_xy",newaddress_xy);
            cv.put("shop_phone_number",newphone);

            shopdb.update("shopTable",cv,"shop_name="+"'"+editText_shop_name.getText().toString()+"'",null);

            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

            editText_shop_name.setText("");
            editText_shop_address.setText("");
            editText_shop_map_address_x.setText("");
            editText_shop_map_address_y.setText("");
            editText_shop_phone.setText("");
        }
    }
    public void deletShop(){
        if(editText_shop_name.getText().toString().equals("") ){
            Toast.makeText(this,"請輸入要刪除的店名",Toast.LENGTH_SHORT).show();
        }else {
            shopdb.delete("shopTable","shop_name="+"'"+editText_shop_name.getText().toString()+"'",null);
            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editText_shop_name.setText("");

        }
    }

    public void queryShop(){
         /*
                 colum/商店名稱 / 商店地址/ 商店經緯度  / 商店連絡電話//

             db.execSQL("CREATE TABLE shopTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"shop_address  text no null,"+"shop_address_x real no null,"+"shop_address_y real no null,"+"shop_address_xy Text no null,"+"shop_phone_number integer no null)");
*/


        Cursor c;
        if(editText_shop_name.getText().toString().equals(""))
            c=shopdb.query("shopTable",null,null,null,null,null,null,null);
        else
            c=shopdb.query("shopTable",null,"shop_name="+"'"+editText_shop_name.getText().toString()+"'",null,null,null,null);
        if(c.getCount()>0){
            c.moveToFirst();
           final ShopData[] shopData=new ShopData[c.getCount()];

            for(int ii=0;ii<shopData.length;ii++){
               shopData[ii]=new ShopData();
            }
            for (int i=0;i<shopData.length;i++){

                   shopData[i].shop_name =c.getString(1);
                   shopData[i].shop_address  =c.getString(2);
                   shopData[i].shop_address_xy  =c.getString(5);
                   shopData[i].shop_address_x=c.getString(3);
                   shopData[i].shop_address_y=c.getString(4);
                   shopData[i].shop_phone_number  ="0"+c.getString(6);
                   c.moveToNext();
            }
            ShopAdapter shopAdapter=new ShopAdapter(shopData,R.layout.layout_item_shop);
            ListView_shop=(ListView)findViewById(R.id.ListView_shop);
            ListView_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"進入商店:" + shopData[position].shop_name, Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent();
                    intent.setClass(Shop_MainActivity.this,Commodity_MainActivity.class);

                    String SHOP_NAME=shopData[position].shop_name;
                    String SHOP_ADDRESS=shopData[position].shop_address;
                    String SHOP_ADDRESS_XY=shopData[position].shop_address_xy;
                    String SHOP_PHONE=shopData[position].shop_phone_number;
                    String SHOP_ADDRESS_X=shopData[position].shop_address_x;
                    String SHOP_ADDRESS_Y=shopData[position].shop_address_y;

                    Bundle bundle=new Bundle();
                    bundle.putString("shop_name",SHOP_NAME);
                    bundle.putString("shop_address",SHOP_ADDRESS);
                    bundle.putString("shop_address_xy",SHOP_ADDRESS_XY);
                    bundle.putString("shop_phone",SHOP_PHONE);

                    bundle.putString("shop_address_x",SHOP_ADDRESS_X);
                    bundle.putString("shop_address_y",SHOP_ADDRESS_Y);

                    intent.putExtras(bundle);
                    startActivityForResult(intent,888);
                }
            });
            ListView_shop.setAdapter(shopAdapter);

            Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

        }




    }


}
