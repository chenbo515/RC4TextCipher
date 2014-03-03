package net.ilyh.rc4;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TextListActivity extends ListActivity {
	DBAdapter dbAdapter;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	public void updateList()
	{
		cursor = dbAdapter.getAllText();

		adapter= new SimpleCursorAdapter(this,
				R.layout.activity_text_list, cursor, new String[]{"TextTitle",
						"CipherText"}, new int[]{R.id.text_title,R.id.text});
		this.setListAdapter(adapter);
	}
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_list);
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		updateList();
		
//		((LinearLayout)findViewById(R.id.linearLayout)).setOnCreateContextMenuListener(this);
//		this.getListView().setOnCreateContextMenuListener(this);
		registerForContextMenu(getListView());
	}
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);
    	Intent data=new Intent();
//    	data.setData(Uri.parse(((TextView)v).getText().toString()));
//    	data.setAction(Uri.parse(((LinearLayout)v).getChildAt(1)
    	TextView textView=(TextView)((LinearLayout)v).getChildAt(2);
    	data.setData(Uri.parse(textView.getText().toString()));
    	
    	setResult(RESULT_OK, data);
    	finish();
    }
 
    @Override
    protected void onDestroy()
    {
    	// TODO Auto-generated method stub
    	dbAdapter.close();
    	super.onDestroy();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo)
    {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	getMenuInflater().inflate(R.menu.activity_list, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	// TODO Auto-generated method stub
//    	return super.onContextItemSelected(item);
//    	item.
    	AdapterContextMenuInfo info=(AdapterContextMenuInfo)item.getMenuInfo();
    	switch (item.getItemId())
		{
			case R.id.menu_delete :
				dbAdapter.deleteText(info.id);
				Toast.makeText(getApplicationContext(),"Message  was deleted", Toast.LENGTH_SHORT).show();
				updateList();
				return true;
			default :
				return true;
		}
    
    	
//    	return true;
    }
}
