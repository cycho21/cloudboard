package kr.ac.uos.ai.cloudBoard.factory;

import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.ArgumentList;
import static kr.ac.uos.ai.cloudBoard.model.bean.NameConfiguration.DataName;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import kr.ac.uos.ai.cloudBoard.model.bean.JSONParsingBean;

public class JsonParser {

	public JsonParser() {
	}

	public JSONParsingBean parse(String string) {
		JSONParsingBean jBean = new JSONParsingBean();
		
		JSONObject object = (JSONObject) JSONValue.parse(string);
		JSONArray argumentsArray = (JSONArray) object.get(ArgumentList);
		String code = null;
		for (Object o : argumentsArray){
			JSONObject valueEntry = (JSONObject) o;
			String name = (String) valueEntry.keySet().iterator().next();
			String value = (String) valueEntry.get(name);
			jBean.setKey(name);
			jBean.setValue(value);
			code = value;
		}
		return jBean;
	}
	
}
