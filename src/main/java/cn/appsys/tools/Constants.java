package cn.appsys.tools;

/**
 * 全局变量类
 * @author Lenovo
 *
 */
public class Constants {
	//存储后台管理session
	public final static String USER_SESSION = "userSession";
	
	//存储开发者信息
	public final static String DEV_USER_SESSION = "devUserSession";
	
	public final static String FILE_MESSAGE = "fileUploadError";
	//消息
	public final static String SYS_MESSAGE = "message";
	
	//存储分页
	public final static int pageSize = 5;
	
	/*
	 * 上传相关
	 */
	public final static String FILEUPLOAD_ERROR_1 = " * APK信息不完整！";
	public final static String FILEUPLOAD_ERROR_2 = " * 上传失败！";
	public final static String FILEUPLOAD_ERROR_3 = " * 上传文件格式不正确！";
	public final static String FILEUPLOAD_ERROR_4 = " * 上传文件过大！";
}
