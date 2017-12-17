package com.example.bosscopeng.shopingmore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
管理商品
 */
public class Commodity_MainActivity extends AppCompatActivity {
    EditText editText_commodity_name,editText_commodity_price,editText_commodity_description;
    Button button_commodity_add,button_commodity_edit,button_commodity_search,button_commodity_delet,button_commodity_buy_history,button_backToShop,button_GoogleMap;
    ListView ListView_commodity;
    TextView text_shop_name_Intent,text_shop_phone_Intent,text_shop_address_Intent,text_shop_address_xy_Intent;
    SQLiteDatabase commoditydb,buydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity__main);


        editText_commodity_name=(EditText)findViewById(R.id.editText_commodity_name);
        editText_commodity_price=(EditText)findViewById(R.id. editText_commodity_price);
        editText_commodity_description=(EditText)findViewById(R.id.editText_commodity_description);

        text_shop_name_Intent=(TextView)findViewById(R.id.text_shop_name_Intent);
        text_shop_phone_Intent=(TextView)findViewById(R.id.text_shop_phone_Intent);
        text_shop_address_xy_Intent=(TextView)findViewById(R.id.text_shop_address_xy_Intent);
        text_shop_address_Intent=(TextView)findViewById(R.id.text_shop_address_Intent);

        button_commodity_add=(Button)findViewById(R.id.button_commodity_add);
        button_commodity_edit=(Button)findViewById(R.id. button_commodity_edit);
        button_commodity_search=(Button)findViewById(R.id. button_commodity_search);
        button_commodity_delet=(Button)findViewById(R.id.button_commodity_delet);

        button_commodity_buy_history=(Button)findViewById(R.id.button_commodity_buy_history);
        button_backToShop=(Button)findViewById(R.id.button_backToShop);
        button_GoogleMap=(Button)findViewById(R.id.button_GoogleMap);
        ShopingMore_db commoditydbhelper =new  ShopingMore_db(this);
        commoditydb =commoditydbhelper.getWritableDatabase();

        ShopingMore_db buydbhelper=new ShopingMore_db(this);
        buydb=buydbhelper.getWritableDatabase();

        final Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String SHOP_NAME=bundle.getString("shop_name");
        String SHOP_ADDRESS=bundle.getString("shop_address");
        String SHOP_ADDRESS_XY=bundle.getString("shop_address_xy");
        String SHOP_PHONE=bundle.getString("shop_phone");
        final String SHOP_ADDRESS_X=bundle.getString("shop_address_x");
        final String SHOP_ADDRESS_Y=bundle.getString("shop_address_y");



        text_shop_name_Intent.setText(SHOP_NAME);
        text_shop_address_Intent.setText(SHOP_ADDRESS);
        text_shop_address_xy_Intent.setText(SHOP_ADDRESS_XY);
        text_shop_phone_Intent.setText(SHOP_PHONE);


        button_commodity_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newCommodity();
            }
        });
        button_commodity_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                renewCommodity();
            }
        });
        button_commodity_delet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deletCommodity();
            }
        });
        button_commodity_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                queryCommodity();

            }
        });


        button_backToShop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        button_commodity_buy_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(Commodity_MainActivity.this,Buy_HistoryMainActivity.class);

                String SHOP_NAME=text_shop_name_Intent.getText().toString();
                String SHOP_ADDRESS=text_shop_address_Intent.getText().toString();
                String SHOP_ADDRESS_XY=text_shop_address_xy_Intent.getText().toString();
                String SHOP_PHONE=text_shop_phone_Intent.getText().toString();

                Bundle bundle=new Bundle();
                bundle.putString("shop_name",SHOP_NAME);
                bundle.putString("shop_address",SHOP_ADDRESS);
                bundle.putString("shop_address_xy",SHOP_ADDRESS_XY);
                bundle.putString("shop_phone",SHOP_PHONE);

                intent.putExtras(bundle);
                startActivityForResult(intent,888);
            }
        });
        button_GoogleMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.setClass(Commodity_MainActivity.this,shop_locationMapsActivity.class);

                String SHOP_NAME=text_shop_name_Intent.getText().toString();
                String SHOP_ADDRESS=text_shop_address_Intent.getText().toString();
                String SHOP_PHONE=text_shop_phone_Intent.getText().toString();
                Float ADDRESS_X=Float.parseFloat(SHOP_ADDRESS_X);
                Float ADDRESS_Y=Float.parseFloat(SHOP_ADDRESS_Y);

                Bundle bundle=new Bundle();
                bundle.putString("shop_name",SHOP_NAME);
                bundle.putString("shop_address",SHOP_ADDRESS);
                bundle.putString("shop_phone",SHOP_PHONE);
                bundle.putFloat("SHOP_ADDRESS_X",ADDRESS_X);
                bundle.putFloat("SHOP_ADDRESS_Y",ADDRESS_Y);

                intent.putExtras(bundle);
                startActivityForResult(intent,888);

            }
        });

    }
    class CommodityData{
        String commodity_name;
        String commodity_id;
        String commodity_description ;
        String commodity_price ;

    }
    public class CommodityAdapter extends BaseAdapter{
        private  CommodityData[] commodityData;
        private int view;
        public CommodityAdapter(CommodityData[] commodityData,int view){
            this.commodityData=commodityData;
            this.view=view;
        }
        @Override
        public int getCount() {return commodityData.length;}
        @Override
        public CommodityData getItem(int position){return commodityData[position];}
        @Override
        public long getItemId(int position){return 0;}
        @Override
        public View getView(int position , View rowView,ViewGroup parent){
            rowView=getLayoutInflater().inflate(view,parent,false);
            TextView text_commodity_name=(TextView)rowView.findViewById(R.id.text_commodity_name);
            TextView text_commodity_id=(TextView)rowView.findViewById(R.id.text_commodity_id);
            TextView text_commodity_description=(TextView)rowView.findViewById(R.id.text_commodity_description);
            TextView text_commodity_price=(TextView)rowView.findViewById(R.id.text_commodity_price);

            text_commodity_name.setText(commodityData[position].commodity_name);
            text_commodity_id.setText(commodityData[position].commodity_id);
            text_commodity_description.setText(commodityData[position].commodity_description);
            text_commodity_price.setText(commodityData[position].commodity_price);
            /*
            button_commodity_buy.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v){
                   buy();
               }
           });   */
            return rowView;
        }

    }
      /*
    public void buy(){
       //* 購買商品 , 將商品資料丟進BUYTABLE
    }*/
      public void newCommodity(){
          if(editText_commodity_name.getText().toString().equals("") || editText_commodity_price.getText().toString().equals("")||editText_commodity_description.getText().toString().equals("")){
              Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
          }else {
              String name=editText_commodity_name.getText().toString();
              String description=editText_commodity_description.getText().toString();
              double price=Double.parseDouble(editText_commodity_price.getText().toString());
              String shop_name=text_shop_name_Intent.getText().toString();
              /*
           _id為貨號/商品所在商店名稱 /商品名稱/商品描述/商品價格


              db.execSQL("CREATE TABLE commodityTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"commodity_name text no null,"+"commodity_description text no null,"+"commodity_price real no null)");
      */

              ContentValues cv=new ContentValues();
              cv.put("shop_name",shop_name);
              cv.put("commodity_name",name);
              cv.put("commodity_description",description);
              cv.put("commodity_price",price);
              commoditydb.insert("commodityTable",null,cv);

              Toast.makeText(this,"新商品:"+editText_commodity_name.getText().toString()+"價格:"+price,Toast.LENGTH_SHORT).show();

              editText_commodity_name.setText("");
             editText_commodity_price.setText("");
             editText_commodity_description.setText("");
          }


      }
    public void renewCommodity(){
        if(editText_commodity_name.getText().toString().equals("") || editText_commodity_price.getText().toString().equals("")||editText_commodity_description.getText().toString().equals("")){
            Toast.makeText(this,"沒有輸入更新資料",Toast.LENGTH_SHORT).show();
        }else {

            String newname=editText_commodity_name.getText().toString();
            String newdescription=editText_commodity_description.getText().toString();
            double newprice=Double.parseDouble(editText_commodity_price.getText().toString());
            String shop_name=text_shop_name_Intent.getText().toString();

            ContentValues cv=new ContentValues();

            cv.put("commodity_name",newname);
            cv.put("commodity_description",newdescription);
            cv.put("commodity_price",newprice);

            commoditydb.update("commodityTable",cv,"shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'"+"AND commodity_name="+"'"+editText_commodity_name.getText().toString()+"'",null);

            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

            editText_commodity_name.setText("");
            editText_commodity_price.setText("");
            editText_commodity_description.setText("");
        }
    }
    public void deletCommodity(){
        if(editText_commodity_name.getText().toString().equals("") ){
            Toast.makeText(this,"請輸入要刪除的商品名稱",Toast.LENGTH_SHORT).show();
        }else {
            commoditydb.delete("commodityTable","shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'"+"AND commodity_name="+"'"+editText_commodity_name.getText().toString()+"'",null);
            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editText_commodity_name.setText("");

        }
    }
    public void queryCommodity(){
         /*
                 colum/商店名稱 / 商店地址/ 商店經緯度  / 商店連絡電話//

             db.execSQL("CREATE TABLE shopTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"shop_address  text no null,"+"shop_address_x real no null,"+"shop_address_y real no null,"+"shop_address_xy Text no null,"+"shop_phone_number integer no null)");
*/


        final Cursor c;
        if(editText_commodity_name.getText().toString().equals(""))
            c=commoditydb.query("commodityTable",null,"shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'",null,null,null,null,null);
        else
            /*
            c=commoditydb.query("commodityTable",null,"shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'"+"commodity_name="+"'"+editText_commodity_name.getText().toString()+"'",null,null,null,null);
       */
            c=commoditydb.query("commodityTable",null,"shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'"+"AND commodity_name="+"'"+editText_commodity_name.getText().toString()+"'",null,null,null,null);
        if(c.getCount()>0){
            c.moveToFirst();
           final CommodityData[] commodityData=new CommodityData[c.getCount()];

            for(int ii=0;ii<commodityData.length;ii++){
                commodityData[ii]=new CommodityData();
            }

            /*
            class CommodityData{
        String commodity_name;
        String commodity_id;
        String commodity_description ;
        String commodity_price ;

    }
             */
            for (int i=0;i<commodityData.length;i++){

                commodityData[i].commodity_name =c.getString(2);
                commodityData[i].commodity_id  =c.getString(0);
                commodityData[i].commodity_description  =c.getString(3);
                commodityData[i].commodity_price  =c.getString(4);
                c.moveToNext();
            }
            CommodityAdapter commodityAdapter=new CommodityAdapter(commodityData,R.layout.layout_item_commodity);
            ListView_commodity=(ListView)findViewById(R.id.ListView_commodity);
            ListView_commodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"您購買了:" + commodityData[position].commodity_name+"一件", Toast.LENGTH_SHORT).show();
                    SimpleDateFormat buyTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date time=new Date();
                    /*
                    購買時間
                     */
                    String buyTimes=buyTime.format(time);

                    String commid=commodityData[position].commodity_id;
                    /*
                    貨號
                     */
                    int commodity_id=Integer.parseInt(commid);
                    /*
                    商店
                     */
                    String shop=text_shop_name_Intent.getText().toString();
               /*
                    商品名稱
                     */
                    String commodity_name=commodityData[position].commodity_name;

                    String price=commodityData[position].commodity_price;
                      /*
                    商品價格
                     */
                    Double buy_commodity=Double.parseDouble(price);
                    /*
                    數量
                     */
                    int num=1;
                    ContentValues cv=new ContentValues();
                    cv.put("commodity_id",commodity_id);
                    cv.put("shop_name",shop);
                    cv.put("commodity_name_buy",commodity_name);
                    cv.put("commodity_price_buy",buy_commodity);
                    cv.put("commodity_buyNumber",num);
                    cv.put("commodity_buyTime",buyTimes);
                    buydb.insert("commodity_buyTable",null,cv);







                }
            });
            ListView_commodity.setAdapter(commodityAdapter);

            Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

        }




    }

}
