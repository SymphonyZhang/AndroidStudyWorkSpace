package francis.kylintest.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by Francis on 2015/4/8.
 */
public class SystemUtil {
	/**
	 * 获取ROM容量
	 */
	public static long getROMSize(boolean all) {
		long blocks = 0;
		// 取得ROM文件路径
		File path = Environment.getDataDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		if (all) {
			blocks = sf.getBlockCount();
		} else {
			blocks = sf.getAvailableBlocks();
		}
		// 返回ROM大小，单位之间进制转换视乎厂家而定，有的肯能是1000为进制
		// return blocks * blockSize; //单位Byte
		// return (blocks * blockSize)/1024; //单位KB
		return (blocks * blockSize) / 1024 / 1024; // 单位MB
		// return (blocks * blockSize) / 1024 / 1024 / 1024; //单位GB
		// return (blocks * blockSize)/1024/1024/1024/1024; //单位TB
	}

	/**
	 * 获取DDR的容量
	 */
	public static Long getTotalMemory() {
		String str1 = "/proc/meminfo";
		String str2 = "";
		Long memory = null;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			int start = str2.indexOf(":");
			int last = str2.lastIndexOf("kB");
			str2 = str2.substring(start + 1, last).trim();

			// memory = Long.parseLong(str2); //单位KB
			memory = ((Long.parseLong(str2) / 1024) / 512 + 1) * 512; // 单位MB
			// memory = Long.parseLong(str2)/1024/1024; //单位GB
			localBufferedReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memory;
	}

	/**
	 * 获取SDCard的容量
	 * 
	 * @param all
	 *            如果是true则返回总容量，如果是false则返回剩余空间
	 * @return
	 */
	public static long getSDSize(boolean all) {
		long blocks = 0;
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		// List<String> paths = SDCardScanner.getExtSDCardPaths();
		StatFs sf = new StatFs(path.getPath());
		// StatFs sf = new StatFs(getSDCardPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		if (all) {
			blocks = sf.getBlockCount();
		} else {
			blocks = sf.getAvailableBlocks();
		}
		// 返回SD卡大小，单位之间进制转换视乎厂家而定，有的肯能是1000为进制
		// return blocks * blockSize; //单位Byte
		// return (blocks * blockSize)/1024; //单位KB
		return (blocks * blockSize) / 1024 / 1024; // 单位MB
		// return (blocks * blockSize) / 1024 / 1024 / 1024; //单位GB
		// return (blocks * blockSize)/1024/1024/1024/1024; //单位TB
	}
	/**
	 * 
	 * @param all
	 * @return
	 */
	public static long getUSBSize(String path,boolean all) {
		long blocks = 0;
		// 取得SD卡文件路径
		
		StatFs sf = new StatFs(path);
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		if (all) {
			blocks = sf.getBlockCount();
		} else {
			blocks = sf.getAvailableBlocks();
		}
		// 返回SD卡大小，单位之间进制转换视乎厂家而定，有的肯能是1000为进制
		// return blocks * blockSize; //单位Byte
		// return (blocks * blockSize)/1024; //单位KB
		return (blocks * blockSize) / 1024 / 1024; // 单位MB
		// return (blocks * blockSize) / 1024 / 1024 / 1024; //单位GB
		// return (blocks * blockSize)/1024/1024/1024/1024; //单位TB
	}

	public static long getTFSize(boolean all) {
		long blocks = 0;
		// 取得SD卡文件路径
		// File path = "/mnt/external_sd";
		StatFs sf = new StatFs("/mnt/external_sd");
		// StatFs sf = new StatFs(getSDCardPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		if (all) {
			blocks = sf.getBlockCount();
		} else {
			blocks = sf.getAvailableBlocks();
		}
		// 返回SD卡大小，单位之间进制转换视乎厂家而定，有的肯能是1000为进制
		// return blocks * blockSize; //单位Byte
		// return (blocks * blockSize)/1024; //单位KB
		return (blocks * blockSize) / 1024 / 1024; // 单位MB
		// return (blocks * blockSize) / 1024 / 1024 / 1024; //单位GB
		// return (blocks * blockSize)/1024/1024/1024/1024; //单位TB
	}

	/**
	 * 获取SD卡的制造厂商
	 */
	public static String getBuilder() {
		String str = null;
		String sd_name = null;
		Object localOb;
		try {
			localOb = new FileReader("/sys/block/mmcblk0/device/type");
			localOb = new BufferedReader((Reader) localOb).readLine()
					.toLowerCase().contentEquals("sd");
			if (localOb != null) {
				str = "/sys/block/mmcblk0/device/";
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		try {
			localOb = new FileReader("/sys/block/mmcblk1/device/type");
			localOb = new BufferedReader((Reader) localOb).readLine()
					.toLowerCase().contentEquals("sd");
			if (localOb != null) {
				str = "/sys/block/mmcblk1/device/";
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		try {
			localOb = new FileReader("/sys/block/mmcblk2/device/type");
			localOb = new BufferedReader((Reader) localOb).readLine()
					.toLowerCase().contentEquals("sd");
			if (localOb != null) {
				str = "/sys/block/mmcblk2/device/";
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		localOb = "";
		try {
			localOb = new FileReader(str + "name");
			sd_name = new BufferedReader((Reader) localOb).readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sd_name;
	}

	/**
	 * 判断是否有网络连接
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取IP
	 * 
	 * @return IpAddress
	 */
	public static String getLocalIpAddress() {
		String ip = null;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				if (!intf.isUp()) {
					continue;
				}
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ip = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * 把时间写入txt中
	 * 
	 * @param file
	 */
	public static String getTime() {
		// 获取系统时间
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyy年MM月dd日  HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 获取外置SD卡路径 经测试无效
	 * 
	 * @return
	 */

	// 获取SD卡根目录
	public static String getSDPath() {
		String sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);// 判断SD卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory().getPath();// 获取根目录
		}
		return sdDir.toString();
	}

	// 加载本地图片
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入txt
	 * 
	 */
	public static void writeToTxt(String fileName, String content) {
		try {
			OutputStreamWriter write = null;
			BufferedWriter out = null;
			if (fileName != null) {
				try { // new FileOutputStream(fileName, true) 第二个参数表示追加写入
					write = new OutputStreamWriter(new FileOutputStream(
							fileName, true), Charset.forName("UTF-16"));// 一定要使用gbk格式
					out = new BufferedWriter(write);
				} catch (Exception e) {
				}
			}
			out.write(content + "\r\n");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取txt
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public static String readTxt(File file) {
		String s = "";
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	//获取重启前次数
	public static int read(File file) {
		String s = "";
		int count=0;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
			while ((s = br.readLine()) != null) {
				count ++;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	//rebootNumber专用
	public static String readRebootNumber(File file) {
		String s = "";
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
			if((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 读取txt指定行
	 * 
	 * 
	 * 
	 */
	public static String readFromTxt(File file, int i) {
		String s = "";
		int count = 1;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
			while ((s = br.readLine()) != null) {
				if (count == i) {
					sb.append(s);
				}
				count++;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	// 截取标记
	public static String rebootcut(String s, int i, int j) {
		if (j == 0) {
			s = s.substring(i);
		} else {
			s = s.substring(i, j);
		}
		return s;
	}

	/**
	 * 
	 * @param file
	 *            文件名
	 * @param lineToRemove
	 *            某行内容
	 */
	public static void removeLineFromFile(String file, String lineToRemove) {

		try {

			File inFile = new File(file);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			// Construct the new file that will later be renamed to the original
			// filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			// Read from the original file and write to the new
			// unless content matches data to be removed.
			while ((line = br.readLine()) != null) {

				if (!line.trim().equals(lineToRemove)) {

					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			// Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 获得SD卡目录，默認內置的
	 */
	public static String getSDPath(Context context) {
		String sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory().toString();// 获取跟目录
		} else {
			// sdDir = context.getFilesDir().getParent();
			sdDir = null;
		}
		return sdDir.toString();

	}

	// 这个方法得到的目录是：/mnt/sdcard，这个是android设备默认的SD卡目录，也是内置的。
	// 下面来帖一个读取外置SD卡路径的方法，本人觉得很好用，不仅能读取SD卡，USB设备也可以的。

	/*
	 * 尝试获取当前外置SD卡路径
	 * 
	 * 04-15 17:16:45.409: I/外置SD卡路径(10395):
	 */
	// */mnt/private
	// */mnt/sdcard
	// */mnt/extsd

	public static String getOutSDPath() {
		String mount = new String();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;

			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mount;
	}

	// 如果你不插TF卡或者U盘，会获取

	// mnt/private
	// mnt/sdcard 这两个路径
	// 如果插上SD卡或者TF卡，会获取

	// mnt/private
	// mnt/sdcard
	// mnt/extsd 这三个路径，很明显，/mnt/extsd 这个就是外置SD卡路径，但不同设备可能不太一样，你可以尝试
	// 如果只插上 USB设备，会获取
	// mnt/private

	// mnt/sdcard

	// mnt/usbhost0 这个usb设备如果有多个接口的话，可能是/mnt/usbhost1,2,3......

	public static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息

				if (lineStr.contains("sdcard")
						&& lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure",
								"");
						return result;
					}
				}
				// 检查命令是否执行失败。
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0表示正常结束，1：非正常结束
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			return Environment.getExternalStorageDirectory().getPath();
		}

		return Environment.getExternalStorageDirectory().getPath();
	}
	
	/**
	 * 写入Statetxt
	 * 
	 */
	public static void writeToState(String fileName, String content) {
		try {
			OutputStreamWriter write = null;
			BufferedWriter out = null;
			if (fileName != null) {
				try { // new FileOutputStream(fileName, true) 第二个参数表示追加写入
					write = new OutputStreamWriter(new FileOutputStream(
							fileName, true), Charset.forName("UTF-16"));// 一定要使用gbk格式
					out = new BufferedWriter(write);
				} catch (Exception e) {
				}
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
