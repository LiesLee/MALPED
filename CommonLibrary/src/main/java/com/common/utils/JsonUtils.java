
package com.common.utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 */
public class JsonUtils {

	public static boolean isPrintException = true;

	/**
	 * get Long from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
	 *         <li>return {@link JSONObject#getLong(String)}</li>
	 *         </ul>
	 */
	public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getLong(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Long from jsonData
	 *
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
	 *         </ul>
	 */
	public static Long getLong(String jsonData, String key, Long defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getLong(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
		return getLong(jsonObject, key, (Long)defaultValue);
	}

	/**
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(String jsonData, String key, long defaultValue) {
		return getLong(jsonData, key, (Long)defaultValue);
	}

	/**
	 * get Int from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getInt(String)} exception, return defaultValue</li>
	 *         <li>return {@link JSONObject#getInt(String)}</li>
	 *         </ul>
	 */
	public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Int from jsonData
	 *
	 * @param jsonData//com.com.berchina.mobile.util.json.JsonUtils
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
	 *         </ul>
	 */
	public static Integer getInt(String jsonData, String key, Integer defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getInt(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
		return getInt(jsonObject, key, (Integer)defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String jsonData, String key, int defaultValue) {
		return getInt(jsonData, key, (Integer)defaultValue);
	}

	/**
	 * get Double from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getDouble(String)} exception, return defaultValue</li>
	 *         <li>return {@link JSONObject#getDouble(String)}</li>
	 *         </ul>
	 */
	public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Double from jsonData
	 *
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
	 *         </ul>
	 */
	public static Double getDouble(String jsonData, String key, Double defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getDouble(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
		return getDouble(jsonObject, key, (Double)defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(String jsonData, String key, double defaultValue) {
		return getDouble(jsonData, key, (Double)defaultValue);
	}

	/**
	 * get String from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
	 *         <li>return {@link JSONObject#getString(String)}</li>
	 *         </ul>
	 */
	public static String getString(JSONObject jsonObject, String key, String defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	public static String getString(JSONObject jsonObject, String key) {
		return getString(jsonObject, key, "");
	}

	/**
	 * get String from jsonData
	 *
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
	 *         </ul>
	 */
	public static String getString(String jsonData, String key, String defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getString(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	public static String getString(String jsonData, String key) {
		return getString(jsonData, key, "");
	}

	/**
	 * get String array from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
	 *         <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
	 *         <li>return string array</li>
	 *         </ul>
	 */
	public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				String[] value = new String[statusArray.length()];
				for (int i = 0; i < statusArray.length(); i++) {
					value[i] = statusArray.getString(i);
				}
				return value;
			}
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * get String array from jsonData
	 *
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
	 *         </ul>
	 */
	public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getStringArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}
	/**
	 * Convert string data to JSONObject
	 *
	 * @param jsonData
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonData) {
		if (StringUtils.isEmpty(jsonData)) {
			return null;
		}

		JSONObject object = null;
		try {
			object = new JSONObject(jsonData);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * get JSONArray from jsonObject
	 *
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
	 *         <li>return {@link JSONObject#getJSONArray(String)}</li>
	 *         </ul>
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from jsonData
	 *
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from a jsonData
	 */
	public static JSONArray getJSONArray(String jsonData) {
		if (StringUtils.isEmpty(jsonData)) {
			return null;
		}

		JSONArray array = null;
		try {
			array = new JSONArray(jsonData);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return array;
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String arrName) {
		JSONArray array = null;
		try {
			array = jsonObject.getJSONArray(arrName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

}
