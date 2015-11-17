package com.example.hashmaptosharedpref;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity {
	Map<String, Map<String, Double>> nodes = new HashMap<>();
	Map<String, Double> innerMap = new HashMap<>();
	Map<String, Double> innerMapSecond = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		innerMap.put("BhimsenGola", 35.4343);
		innerMap.put("Chabahil", 24.343);
		innerMap.put("Ratnapark", 64.4343);
		innerMap.put("Sundhara", 65.4343);
		innerMap.put("Thapathali", 29.4343);
		// second innermap
		innerMapSecond.put("Jyoti", 32.534434);
		innerMapSecond.put("Sumedh", 83.534434);
		innerMapSecond.put("Thanka", 93.534434);
		innerMapSecond.put("Sahaj", 19.534434);
		innerMapSecond.put("Priscu", 49.534434);
		nodes.put("One", innerMap);
		nodes.put("Two", innerMapSecond);
		Log.d("crossover", "the list of nodes:" + nodes);
		saveMap(nodes);
		// retrieve hashmap values
		Map<String, Map<String, Double>> outputMap = loadMap();
		Log.d("crossover", "the returned output map:" + outputMap);

	}

	private void saveMap(Map<String, Map<String, Double>> inputMap) {
		SharedPreferences pSharedPref = getApplicationContext()
				.getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
		if (pSharedPref != null) {
			JSONObject jsonObject = new JSONObject(inputMap);
			String jsonString = jsonObject.toString();
			Editor editor = pSharedPref.edit();
			editor.remove("My_map").commit();
			editor.putString("My_map", jsonString);
			editor.commit();
		}
	}

	private Map<String, Map<String, Double>> loadMap() {
		Map<String, Map<String, Double>> outputMap = new HashMap<>();
		SharedPreferences pSharedPref = getApplicationContext()
				.getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
		try {
			if (pSharedPref != null) {
				String jsonString = pSharedPref.getString("My_map",
						(new JSONObject()).toString());
				JSONObject jsonObject = new JSONObject(jsonString);
				Iterator<String> keysItr = jsonObject.keys();
				while (keysItr.hasNext()) {

					String key = keysItr.next();
					Map<String, Double> temp = new HashMap<>();
					JSONObject tempjSONOBject = new JSONObject(
							(String) jsonObject.get(key));

					Iterator<String> tempKey = tempjSONOBject.keys();
					while (tempKey.hasNext()) {
						String key2 = tempKey.next();
						temp.put(key2, tempjSONOBject.getDouble(key2));
					}
					outputMap.put(key, temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputMap;
	}
}
