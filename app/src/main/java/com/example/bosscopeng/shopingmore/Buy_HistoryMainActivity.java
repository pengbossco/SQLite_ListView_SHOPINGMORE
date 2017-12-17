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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/*
查看銷售紀錄
 */
public class Buy_HistoryMainActivity extends AppCompatActivity {
    Button button_backToPre,button_query;
    ListView ListView_sale_history;
    TextView text_shop_name_Intent,text_shop_phone_Intent,text_shop_address_Intent,text_shop_address_xy_Intent;
    SQLiteDatabase buydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__history_main);
        text_shop_name_Intent=(TextView)findViewById(R.id.text_shop_name_Intent);
        text_shop_phone_Intent=(TextView)findViewById(R.id.text_shop_phone_Intent);
        text_shop_address_xy_Intent=(TextView)findViewById(R.id.text_shop_address_xy_Intent);
        text_shop_address_Intent=(TextView)findViewById(R.id.text_shop_address_Intent);
        ShopingMore_db buydbhelper=new ShopingMore_db(this);
        buydb=buydbhelper.getWritableDatabase();

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String SHOP_NAME=bundle.getString("shop_name");
        String SHOP_ADDRESS=bundle.getString("shop_address");
        String SHOP_ADDRESS_XY=bundle.getString("shop_address_xy");
        String SHOP_PHONE=bundle.getString("shop_phone");

        text_shop_name_Intent.setText(SHOP_NAME);
        text_shop_address_Intent.setText(SHOP_ADDRESS);
        text_shop_address_xy_Intent.setText(SHOP_ADDRESS_XY);
        text_shop_phone_Intent.setText(SHOP_PHONE);
        button_query=(Button)findViewById(R.id.button_query);
        button_query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*
        將db的資料放入ListViewItem
 */

       /*

            colum/commodity_id 是賣出商品貨號/商品所在商店名稱 / 商品名稱/ 商品價格/商品的購買數量/購買商品的時間

        db.execSQL("CREATE TABLE commodity_buyTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"commodity_id integer no null,"+"commodity_name_buy text no null,"+"commodity_price_buy real no null,"+"commodity_buyNumber integer no null,"+"commodity_buyTime text no null)");
        */

                Cursor c;


                c=buydb.query("commodity_buyTable",null,"shop_name="+"'"+text_shop_name_Intent.getText().toString()+"'",null,null,null,null,null);
                if(c.getCount()>0){
                    c.moveToFirst();
                    final BuyData[] buyData=new BuyData[c.getCount()];

                    for(int ii=0;ii<buyData.length;ii++){
                        buyData[ii]=new BuyData();
                    }

            /*
            class BuyData{
        String commodity_name_buy;
        String commodity_id;
        String commodity_buyNumber  ;
        String commodity_price_buy ;
        String commodity_buy_time;

    }
             */
                    for (int i=0;i<buyData.length;i++){

                        buyData[i].commodity_name_buy =c.getString(3);
                        buyData[i].commodity_id  =c.getString(2);
                        buyData[i].commodity_buyNumber  =c.getString(5);
                        buyData[i].commodity_price_buy  =c.getString(4);
                        buyData[i].commodity_buy_time  =c.getString(6);

                        c.moveToNext();
                    }
                    BuyAdapter buyAdapter=new BuyAdapter(buyData,R.layout.layout_item_commodity_buy_hsitory);
                    ListView_sale_history=(ListView)findViewById(R.id.ListView_sale_history);
                    ListView_sale_history.setAdapter(buyAdapter);
                    Toast.makeText(getApplicationContext(),"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

                }
            }
        });
        button_backToPre=(Button)findViewById(R.id.button_backToPre);
        button_backToPre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });






    }
    class BuyData{
        String commodity_name_buy;
        String commodity_id;
        String commodity_buyNumber  ;
        String commodity_price_buy ;
        String commodity_buy_time;

    }

    public class BuyAdapter extends BaseAdapter{
        private BuyData[] buyData;
        private int view;
        public BuyAdapter(BuyData[] buyData,int view){
            this.buyData=buyData;
            this.view=view;
        }
        @Override
        public int getCount() {return buyData.length;}
        @Override
        public BuyData getItem(int position){return buyData[position];}
        @Override
        public long getItemId(int position){return 0;}
        @Override
        public View getView(int position , View rowView,ViewGroup parent){
            rowView=getLayoutInflater().inflate(view,parent,false);
            TextView text_commodity_name_buy=(TextView)rowView.findViewById(R.id.text_commodity_name_buy);
            TextView text_commodity_id=(TextView)rowView.findViewById(R.id.text_commodity_id);
            TextView text_commodity_buyNumber =(TextView)rowView.findViewById(R.id.text_commodity_buyNumber );
            TextView text_commodity_buy_time=(TextView)rowView.findViewById(R.id.text_commodity_buy_time);
            TextView text_commodity_price_buy=(TextView)rowView.findViewById(R.id.text_commodity_price_buy) ;
            text_commodity_name_buy.setText(buyData[position].commodity_name_buy);
            text_commodity_buyNumber .setText(buyData[position].commodity_buyNumber );
            text_commodity_id.setText(buyData[position].commodity_id);
            text_commodity_buy_time.setText(buyData[position].commodity_buy_time);
            text_commodity_price_buy.setText(buyData[position].commodity_price_buy);
            return rowView;
        }

    }


}
