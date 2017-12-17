package com.example.bosscopeng.shopingmore;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bossco.Peng on 2017/12/13.
 */

public class ShopingMore_db extends SQLiteOpenHelper {
    private static final String database="mydata.db";
    private static final int version=1;
    public ShopingMore_db(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    public ShopingMore_db(Context context){
        this(context,database,null,version);
    }
    @Override
    public void onCreate (SQLiteDatabase db){
        /*
                 colum/商店名稱 / 商店地址/ 商店經緯度  / 商店連絡電話//
         */
        db.execSQL("CREATE TABLE shopTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"shop_address  text no null,"+"shop_address_x real no null,"+"shop_address_y real no null,"+"shop_address_xy Text no null,"+"shop_phone_number integer no null)");
        /*
           _id為貨號/商品所在商店名稱 /商品名稱/商品描述/商品價格
         */

        db.execSQL("CREATE TABLE commodityTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"commodity_name text no null,"+"commodity_description text no null,"+"commodity_price real no null)");
        /*
            colum/commodity_id 是賣出商品貨號/商品所在商店名稱 / 商品名稱/ 商品價格/商品的購買數量/購買商品的時間
         */
        db.execSQL("CREATE TABLE commodity_buyTable(_id integer primary key autoincrement,"+"shop_name text no null,"+"commodity_id integer no null,"+"commodity_name_buy text no null,"+"commodity_price_buy real no null,"+"commodity_buyNumber integer no null,"+"commodity_buyTime text no null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS shopTable");
        db.execSQL("DROP TABLE IF EXISTS commodityTable");
        db.execSQL("DROP TABLE IF EXISTS commodity_buyTable");
        onCreate(db);
    }
}
