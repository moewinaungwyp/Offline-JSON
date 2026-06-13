package com.offjson.ll1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONArray;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    
	private ListView lv;
	
	private List<String>titleList;
	private List<String>linkList;
	private List<String>dateList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		lv=findViewById(R.id.lv);
		
		titleList=new ArrayList<>();
		linkList=new ArrayList<>();
		dateList=new ArrayList<>();
		
        loadAndParseJson(); // assets folder ထဲက JSON ဖိုင်ကို ဖတ်ရန်
    }

	private void loadAndParseJson() {
		try{
			InputStream is=getAssets().open("news.json");
			BufferedReader reader=new BufferedReader(new InputStreamReader(is));
			StringBuilder builder=new StringBuilder();
			String line;
			
			while((line=reader.readLine())!=null){
				builder.append(line);
			}
			reader.close();
			is.close();
			
			String jsonString=builder.toString();
			
			parseJsonDisplay(jsonString); // json ကို parse လုပ်တယ်
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(this,"JSON ဖတ်ရန် အမှားရှိသည်"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
	}
    // JSON string ကို parse လုပ်​ြပီး ListView ထဲ​ြပ
	private void parseJsonDisplay(String jsonString) {
		
		try{
			// JSON Object  အ​ြဖစ်​ေ​ြပာင်းလဲ
			JSONObject jo=new JSONObject(jsonString);
			
			// "news" key ​ေအာက်က Array ကို ထုတ်ယူ​ြခင်း
			JSONArray ja=jo.getJSONArray("news");
			
			// Array ထဲက item တစ်ခုချငတစ်ခုချင်းဆီ သွား​ေရာက်ဖက်
			for (int i=0; i<ja.length(); i++){
				JSONObject item=ja.getJSONObject(i);
				
				String title = item.getString("title");
                String link = item.getString("link");
                String date = item.getString("date");

                // ArrayList များထဲသို့ ထည့်ခြင်း
                titleList.add(title);
                linkList.add(link);
                dateList.add(date);
			}
			
			// listView မှာ text တစ်ခုတည်းသံုးသည့်အခာ 
			ArrayAdapter<String>ad=new ArrayAdapter<>(
			this,android.R.layout.simple_list_item_1,titleList);
			lv.setAdapter(ad);
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int p, long p1) {
					
						// နှိပ်လိုက်သော item ၏ link ကို ရယူခြင်း
						String selectedLink = linkList.get(p);
						String selectedTitle = titleList.get(p);

						// NewsDetailActivity ကို ဖွင့်ရန် Intent ပြင်ဆင်ခြင်း
						Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
						intent.putExtra("url", selectedLink);
						intent.putExtra("title", selectedTitle);
						startActivity(intent);
					}
				});
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(this,"JSON ဖတ်ရန် အမှားရှိသည်"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
	}}
   /***private class NewsAdapter extends ArrayAdapter<String> {
		public NewsAdapter(Context context, List<String> titles) {
			super(context, 0, titles);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
			}

			TextView tvTitle = convertView.findViewById(R.id.tvTitle);
			TextView tvDate = convertView.findViewById(R.id.tvDate);

			tvTitle.setText(titleList.get(position));
			tvDate.setText("📅 " + dateList.get(position));

			return convertView;
		}
	}
}**/
	
