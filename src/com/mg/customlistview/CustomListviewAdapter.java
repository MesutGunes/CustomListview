package com.mg.customlistview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author mesut
 *
 */
public class CustomListviewAdapter extends BaseAdapter{
	
	/**
	 * Bu değişkene SistemServisini referans göstermemiz gerekli.(LAYOUT_INFLATER_SERVICE)
	 * Bu değişken sayesinde özel oluşturduğumuz list_satir.xml okunur ve listview için görüntüye dönüştürülür.
	 */
	private LayoutInflater mInflater;
	/**
	 * Gönderilen verilerin tutalacağı Dizi.
	 */
	private String[] iconData;
	/**
	 * CustomAdaptere nerede çalışacağının bilgisini verir.
	 */
	private Context context;
	/**
	 * Listviewde itemlere tıklandığında renk değiştirmelerini sağlamak için gerekli ArrayList.
	 */
	ArrayList<Integer> renkDegis = new ArrayList<Integer>();
	
	public CustomListviewAdapter(Context context,String[] items){
		mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	    iconData = items;
	    this.context = context;
	}
	
	/**
	 * Satır sayısını döndürür.
	 */
	@Override
	public int getCount() {
		return iconData.length;
	}
	
	/**
	 * Pozisyonu verilen satırdaki Objeyi döndürür.
	 */
	@Override
	public Object getItem(int position) {
		return iconData[position];
	}
	
	/**
	 * Pozisyonu verilen satırın idsini döndürürür.
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Pozisyonu verilen satırın görüntüsünü döndürür.
	 * inflater değişkeni bu metodda görevini yapar.
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		notifyDataSetChanged();
		
		ViewHolder holder;
		
		/**
		 * Çevrilecek olan görüntü boşsa burda doldur.Ve her seferinde aynı şeyi yapmamak için holder kullan.
		 */
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.list_satir, null);
			holder = new ViewHolder();
		    holder.text = (TextView) convertView.findViewById(R.id.textIconAdi);
		    holder.buton = (Button)convertView.findViewById(R.id.butonSet);
		    holder.layout = (LinearLayout)convertView.findViewById(R.id.layoutGorunum);
		    holder.icon = (ImageView)convertView.findViewById(R.id.imageIcon);
		        
		    convertView.setTag(holder);  
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		/**
		 * Text kısmını doldur.Id ata.
		 */
		holder.text.setText(" "+getItem(position));
	    holder.buton.setId(position);
	    
	    /**
	     * Icon için gerekli resmi assets klasöründen al.
	     */
	    InputStream ims;
		try {
			ims = context.getAssets().open(getItem(position).toString().toLowerCase()+".png");
			Drawable d = Drawable.createFromStream(ims, null);
			holder.icon.setImageDrawable(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * Eğer item tıklanmışla renkDegis listesindedir ve button ve yazı rengi turuncu olmalı.
		 * Değilse button ve yazı rengi mavi olmalı.
		 */
		if (renkDegis.contains(position)) {		            
			holder.text.setTextColor(Color.rgb(255, 165, 0));
			holder.buton.setBackgroundResource(R.drawable.set1);
        } else {
        	holder.text.setTextColor(Color.rgb(16, 78, 139));
			holder.buton.setBackgroundResource(R.drawable.set2);
        } 
		
		/**
		 * herhangi bir elemana tıklandığında eleman renkDegis listesinde değilse listeye ekle.
		 * listeyi sıfırla ve data değişimini görüntüye haber ver.
		 */
		 holder.layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (!renkDegis.contains(position)) {
						renkDegis.clear();
		                renkDegis.add(position);
		                notifyDataSetChanged();
		            } else {
		                notifyDataSetChanged();
		                int po = renkDegis.indexOf(position);
		                renkDegis.remove(po);
		            }
					
					Toast.makeText(context, getItem(position).toString(), Toast.LENGTH_SHORT).show();
				}
			});
		
		return convertView;
	}
	
	/**
	 * Bu classı kullanmamızın sebebi listview hızını arttırmaktır.
	 * Yani getview metodunda her seferinde findViewById ile objeleri bulmak yerine ilk seferde bulup bi yerde tutuyoruz.
	 * @author mesut
	 *
	 */
	private class ViewHolder {
	    TextView text;
	    ImageView icon;
	    Button buton;
	    LinearLayout layout;
	}

}
