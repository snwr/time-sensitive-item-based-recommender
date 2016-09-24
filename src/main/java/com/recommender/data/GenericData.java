package com.recommender.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;

import com.recommender.model.Purchase;

public class GenericData implements Data{

	private final Map<Integer,Map<Integer,Purchase>> userMap;
	private final Map<Integer,List<Integer>> itemMap;
	
	public GenericData(String filePath) throws IOException, ParseException {
		userMap = new HashMap<>();
		itemMap = new HashMap<>();
		loadData(filePath);
	}

	private void loadData(String filePath) throws FileNotFoundException, IOException {
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader(getFile(filePath)));
			String line = br.readLine();
			while (line != null) {
				String[] split = line.split("::");
				int userId = Integer.parseInt(split[0].trim());
				int itemId = Integer.parseInt(split[1].trim());
				long time = Long.parseLong(split[2]);
				if(userMap.get(userId)==null){
					userMap.put(userId, new HashMap<Integer, Purchase>());
				}
				userMap.get(userId).put(itemId, new Purchase(time));
				
				if(itemMap.get(itemId)==null){
					itemMap.put(itemId, new ArrayList<Integer>());
				}
				itemMap.get(itemId).add(userId);
				line = br.readLine();
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public Map<Integer, Purchase> getUser(int userId) {
		return userMap.get(userId);
	}

	public List<Integer> getItem(int itemId) {
		return itemMap.get(itemId);
	}

	public int getNumberOfItems() {
		return itemMap.size();
	}
	private File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
	public int getNumberOfUsers() {
		return userMap.size();
	}

	public Map<Integer, List<Integer>> getItemMap() {
		return itemMap;
	}
}
