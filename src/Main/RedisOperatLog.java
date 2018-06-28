package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import Model.OperatLogInfo;
import Utils.DateUtil;
import Utils.Utils;



public class RedisOperatLog {

	// 保存路径
	private static String basePath = Utils.getValueByKey("redis.data.path");
	// 文件流
	private static Writer writer = null;
	private static String initFormatPath = null;
	private static String initFileName = null;
	static {
		initFormatPath = DateUtil.getNow(DateUtil.FORMAT_PATH);
		initFileName = DateUtil.getNow(DateUtil.FORMAT_SHORT) + ".txt";
		getFileWriter();
	}
	
	
	public static void test (Object... values){
		System.err.println(values.toString());
		if(values.length > 0){
			Object o = values[0];
			if(o.getClass() == OperatLogInfo.class)
				System.err.println("是否匹配上对象");
		}
	}
	
	
	public static void main(String[] args) {
		
		
		
		OperatLogInfo info = new OperatLogInfo();
		info.setCmd("SET");
		info.setKey("OOOO");
		info.setContent("12345678");
		
		test(info);
		
//		try {
//			RedisOperatLog.saveLogInfo(info);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 得到文件操作流
	 */
	private static void getFileWriter() {
		// 初始化文件信息
		File path = null;
		File dataFile = null;
		// 初始创建文件关联
		try {
			path = new File(basePath + initFormatPath);
			if (!path.exists())
				path.mkdirs();
			if (path.exists())
				dataFile = new File(path.getPath(), initFileName);
			if (!dataFile.exists())
				dataFile.createNewFile();
			// 判断流是否关闭
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			writer = new FileWriter(dataFile, true);
		} catch (IOException e) {
			//Log4jUtil.i(log, "初始化文件流关联 ......");
			e.printStackTrace();
		}
	}

	/**
	 * 将信息记录到日志文件
	 * 
	 * @param info
	 *            日志文件
	 */
	public static void saveLogInfo(OperatLogInfo info) throws IOException {
		
		System.err.println(info.getClass() == OperatLogInfo.class);
		
		
		// 判断是否为当天时间当天时间
		String nowPath = DateUtil.getNow(DateUtil.FORMAT_PATH);
		// 第二天重新生成一个文件
		if (!initFormatPath.equals(nowPath)) {
			// 重新生成路径和文件
			initFormatPath = nowPath;
			// 重新生成文件名
			initFileName = DateUtil.getNow(DateUtil.FORMAT_SHORT) + ".txt";
			getFileWriter();
		}
		// 写入文件
		if (writer == null) {
			getFileWriter();
		}
		// 将文件写入
		writer.write(info.toString() + "\r\n");
		// 刷新缓存
		writer.flush();
	}

//	/**
//	 * 恢复数据
//	 * 
//	 * @param filePath
//	 * @param fileName
//	 * @throws IOException
//	 */
//	public static String recoverByPath(String fileName) {
//		// 先判断是否为文本类型
//		if (!fileName.endsWith("txt")) {
//			return null;//new String(//StatusCodeUtil.ERROR_42006,
//					//StatusCodeUtil.getMsg(//StatusCodeUtil.ERROR_42006), null);
//		}
//		File dataFile = null;
//		BufferedReader reader = null;
//		try {
//			// 路径是否存在
//			dataFile = new File(fileName);
//			if (!dataFile.exists() || !dataFile.isFile())
//				return null;//new String(//StatusCodeUtil.ERROR_42004,
//						//StatusCodeUtil.getMsg(//StatusCodeUtil.ERROR_42004), null);
//			// 这边读取文件
//			try {
//				reader = new BufferedReader(new FileReader(dataFile));
//				String lineStr = null;
//				while ((lineStr = reader.readLine()) != null) {
//					// 恢复至 Redis 缓存中
//					recoverData(lineStr);
//				}
//				return null;//new String(//StatusCodeUtil.SUCCESS, //StatusCodeUtil.getMsg(//StatusCodeUtil.SUCCESS),
//						//"数据日志恢复成功");
//			} catch (Exception e) {
//				return null;//new String(//StatusCodeUtil.ERROR_42007,
//						//StatusCodeUtil.getMsg(//StatusCodeUtil.ERROR_42007), null);
//			}
//		} catch (Exception e) {
//			return null;//new String(//StatusCodeUtil.ERROR_42005,
//					//StatusCodeUtil.getMsg(//StatusCodeUtil.ERROR_42005), null);
//		}
//	}

//	/**
//	 * 恢复数据
//	 * 
//	 * @param lineData
//	 */
//	private static void recoverData(String lineData) {
//		OperatLogInfo operatInfo = null;
//		Cache cache = Redis.use();
//		try {
//			operatInfo = JSON.parseObject(lineData, OperatLogInfo.class);
//			if (operatInfo != null) {
//				String opType = operatInfo.getOpType();
//				String key = operatInfo.getKey();
//				String content = operatInfo.getOpContent();
//				// 插入操作
//				if (StaticUtil.REDIS_ADD.equals(opType)) {
//					cache.recoverSet(key, content);
//				} else if (StaticUtil.REDIS_DEL.equals(opType)) {
//					cache.recoverDel(key);
//				} else if (StaticUtil.REDIS_UPDATE.equals(opType)) {
//					cache.recoveRename(key, content);
//				}
//			}
//		} catch (Exception e) {
//			return;
//		}
//	}
}
