package com.mg.customlistview;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;

public class MainActivity extends Activity {
	
	/**
	 * Gerekli datamız.
	 */
	private String iconAdlari[]={"Skype",
			"Youtube",
			"Evernote",
			"Vimeo",
			"Facebook",
			"GooglePlus",
			"Twitter",
			"LinkedIn",};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/**
		 * Listvievi tanımladık
		 */
		ListView list = (ListView)findViewById(R.id.listView1);
		/**
		 * Özel adapter oluşturduk.
		 */
		CustomListviewAdapter adapter = new CustomListviewAdapter(this, iconAdlari);
		/**
		 * Listview içine atadık.
		 */

		
	}
	

}
