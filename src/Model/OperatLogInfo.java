package Model;




public class OperatLogInfo implements java.io.Serializable {
	/**
	 * 序列化Id
	 */
	private static final long serialVersionUID = -1549549772349662451L;
	// 操作命令
	private String cmd;
	// key
	private String key;
	// 内容
	private String content;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(cmd).append(" ").append(key).append(" ").append(content);
		return buffer.toString();
	}


	
}