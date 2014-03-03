package net.ilyh.rc4;
         
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
  
public class DBAdapter
{
	public static final String KEY_ROWID="_id";
	public static final String KEY_TEXT="CipherText";
	public static final String KEY_TITLE="TextTitle";
	
	private static final String DATABASE_NAME="MyDB";
	private static final String DATABASE_TABLE="TextList";
	private static final int DATABASE_VERSION=1;
	
	private static final String DATABASE_CREATE=
			"create table TextList(_id integer primary key autoincrement,"+
	"TextTitle text not null,CipherText text not null);";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public  DBAdapter(Context ctx)
	{
		this.context=ctx;
		DBHelper=new DatabaseHelper(context);
	}
	
	public static class DatabaseHelper extends SQLiteOpenHelper
	{
		public DatabaseHelper(Context context)
		{
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// TODO Auto-generated method stub
			try
			{
				db.execSQL(DATABASE_CREATE);
			}
			catch (SQLException e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			// TODO Auto-generated method stub
			try
			{
				db.execSQL("DROP TABLE IF EXISTS TextList");
			}
			catch (SQLException e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public DBAdapter open() throws SQLException
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{ 
		DBHelper.close();
	}
	public long insertText(String title,String  text)
	{
		ContentValues initialValues=new ContentValues();
		initialValues.put(KEY_TITLE,title);
		initialValues.put(KEY_TEXT,text);
		return db.insert(DATABASE_TABLE,null,initialValues);
	}
	public boolean deleteText(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID+"="+rowId,null)>0;
	}
	public Cursor getAllText()
	{
		return db.query(DATABASE_TABLE, new String[]{KEY_ROWID,KEY_TITLE,KEY_TEXT}, null, null, null, null, null);
	}
//	public boolean updataText(long rowId,String text)
//	{
//		ContentValues args=new ContentValues();
//		args.put(KEY_ROWID, rowId);
//		args.put(KEY_TEXT, text);
//		return db.update(DATABASE_TABLE, args, KEY_ROWID+"="+rowId,null)>0;
//	
//	}
}