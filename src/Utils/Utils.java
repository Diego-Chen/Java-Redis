package Utils;

import java.io.InputStreamReader;
import java.util.Properties;


public class Utils
{
	private static Properties configPro;
	static
	{
		try
		{
			configPro = new Properties();
			InputStreamReader in = new InputStreamReader(Utils.class.getResourceAsStream("/config.properties"), "UTF-8");
			configPro.load(in);
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getValueByKey(String key)
	{
		if (null != key && !"".equals(key.trim()))
		{
			if (!isTest())
			{
				// 表示是正式的
				key = key + ".pro";
			}
			else
			{
				// 表示测试
				key = key + ".test";
			}
			return getValueByKeyOne(key);
		}
		return null;
	}

	

	

	/**
	 * 是否是测试环境 true：测试 false:正式
	 * 
	 * @return
	 */
	public static boolean isTest()
	{
		boolean isTest = true;
		isTest = Boolean.valueOf(getValueByKeyOne("is.test"));
		return isTest;
	}

	/**
	 * 根据key得到value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueByKeyOne(String key)
	{
		String value = null;

		if (null != key && !"".equals(key.trim()))
		{
			value = configPro.getProperty(key);
		}
		return value;
	}

	
	

	

}
