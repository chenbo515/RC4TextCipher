package net.ilyh.rc4;

import java.io.UnsupportedEncodingException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.ClipboardManager;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
{
	public static final int PASSDIALOG = 0;
	public static final int TITLEDIALOG=1;
	EditText passwordEditText, editText1, editText2,titleEditText;
	ClipboardManager clipboardManager;
	int request_code = 1;
	OnClickListener btnPassClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			showDialog(PASSDIALOG);

		}
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode==request_code)
		{
			if(resultCode==RESULT_OK)
			{
				editText2.setText(data.getDataString());
				editText1.getText().clear();
			}
		}
	};
	@Override
	protected Dialog onCreateDialog(int id)
	{

		switch (id)
		{
			case PASSDIALOG :
				return new AlertDialog.Builder(this)
						.setTitle("Enter password.")
						.setView(passwordEditText)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener()
								{

									public void onClick(DialogInterface dialog,
											int which)
									{
										// TODO Auto-generated method stub
										if (passwordEditText.getText().length() != 0)
										{
											Toast.makeText(
													getApplicationContext(),
													"Password saved",
													Toast.LENGTH_SHORT).show();
										}
										else
										{
											Toast.makeText(
													getApplicationContext(),
													"Invalid password",
													Toast.LENGTH_SHORT).show();
										}

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener()
								{

									public void onClick(DialogInterface dialog,
											int which)
									{
										// TODO Auto-generated method stub
										// passwordEditText.getText().clear();
										dismissDialog(PASSDIALOG);
									}
								}).create();
			case TITLEDIALOG:
				return new AlertDialog.Builder(this).setTitle("Enter the title of the message").setView(titleEditText).setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
						dbAdapter.open();
						if (titleEditText.getText().length()!=0)
						{
							dbAdapter.insertText(titleEditText.getText().toString(),editText2.getText().toString());
						}
						else {
							dbAdapter.insertText("Untitled", editText2.getText().toString());
						}
						Toast.makeText(getApplicationContext(),
								"Text was saved to database!", Toast.LENGTH_SHORT)
								.show();
						dbAdapter.close();
						titleEditText.getText().clear();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						dismissDialog(TITLEDIALOG);
					}
				}).create();
			default :
				return null;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		passwordEditText = new EditText(this);
		passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		passwordEditText.setId(0x12340000);
		titleEditText=new EditText(this);

		((Button) findViewById(R.id.btnCopyPlain))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						if (editText1.getText().length() != 0)
						{
							clipboardManager.setText(editText1.getText()
									.toString());
							Toast.makeText(
									getApplicationContext(),
									"Plaintext was copyed to system clipboard!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		((Button) findViewById(R.id.btnPastePlain))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						try
						{
							editText1.append(clipboardManager.getText());

						}
						catch (Exception e)
						{
							// TODO: handle exception
						}
						
					}
				});
		((Button) findViewById(R.id.btnCopyCipher))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						if (editText2.getText().length() != 0)
						{
							clipboardManager.setText(editText2.getText()
									.toString());
							Toast.makeText(
									getApplicationContext(),
									"Ciphertext was copyed to system clipboard!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		((Button) findViewById(R.id.btnPasteCipher))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						try
						{
							editText2.append(clipboardManager.getText());
						}
						catch (Exception e)
						{

						}

					}
				});

		((Button) findViewById(R.id.btnEncrypt))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						if (passwordEditText.getText().length() != 0)
						{
							try
							{
								RC4 rc4 = new RC4(passwordEditText.getText()
										.toString().getBytes("UTF-8"));
								editText2.setText(Base64.encodeToString(rc4
										.make(editText1.getText().toString()),
										Base64.DEFAULT));
								Toast.makeText(getApplicationContext(),
										"Text encrypted successfully!",
										Toast.LENGTH_SHORT).show();
							}
							catch (UnsupportedEncodingException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						else
						{
							Toast.makeText(getApplicationContext(),
									"Password is empty", Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
		((Button) findViewById(R.id.btnDecrypt))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						if (passwordEditText.getText().length() != 0)
						{
							try
							{
								RC4 rc4 = new RC4(passwordEditText.getText()
										.toString());
								editText1.setText(new String(rc4.make(Base64
										.decode(editText2.getText().toString(),
												Base64.DEFAULT)), "UTF-8"));

								Toast.makeText(getApplicationContext(),
										"text successfully decrypted!",
										Toast.LENGTH_SHORT).show();
							}
							catch (UnsupportedEncodingException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							catch (IllegalArgumentException e) {
								// TODO: handle exception
								Toast.makeText(getApplicationContext(), "Unable to decrypt text", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(),
									"Password is empty", Toast.LENGTH_SHORT)
									.show();
						}
					}
				});

		((Button) findViewById(R.id.btnPass))
				.setOnClickListener(btnPassClickListener);
		((Button) findViewById(R.id.btnPlainClear))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						editText1.getText().clear();
					}
				});
		((Button) findViewById(R.id.btnCipherClear))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						editText2.getText().clear();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.menu_save :
				if (editText2.getText().length() != 0)
				{
					showDialog(TITLEDIALOG);
				}
				else {
					Toast.makeText(getApplicationContext(), "Can not save empty message!", Toast.LENGTH_SHORT).show();
				}
				return true;
			case R.id.menu_viewList :
				startActivityForResult(new Intent("net.ilyh.rc4.textlist"),
						request_code);
				// startActivity(this,TextListActivity.class);
				return true;
			case R.id.menu_SMS:
				Intent i=new Intent(android.content.Intent.ACTION_VIEW);
				i.putExtra("sms_body", editText2.getText().toString());
				i.setType("vnd.android-dir/mms-sms");
				startActivity(i);
				return true;
			case R.id.menu_EMAIL:
				Intent emailIntent=new Intent(Intent.ACTION_SEND);
				emailIntent.setData(Uri.parse("mailto:"));
				emailIntent.putExtra(Intent.EXTRA_TEXT,editText2.getText().toString());
				emailIntent.setType("message/rfc822");
				startActivity(Intent.createChooser(emailIntent, "Email"));
				return true;
			case R.id.menu_About:
				Intent aboutIntent=new Intent("net.ilyh.rc4.about");
				startActivity(aboutIntent);
				return true;
			case R.id.menu_SHAER:
				if (editText2.getText().length() != 0)
				{
					Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, editText2.getText().toString());
					sendIntent.setType("text/plain");
					startActivity(sendIntent); 
				}
				else {
					Toast.makeText(getApplicationContext(), "Can not share empty message!", Toast.LENGTH_SHORT).show();
				}
				return true;

			default :
				return true;
		}
	}
}
