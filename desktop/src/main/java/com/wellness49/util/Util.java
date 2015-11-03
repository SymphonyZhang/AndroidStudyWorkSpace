package com.wellness49.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class Util {
	private static PackageManager pm;

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

	public static void defaultToData(File default_file,File data_file){
		if(!data_file.exists()){
			try {
				data_file.createNewFile();
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(default_file), Charset.forName("UTF-16")));
				//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(	Common.FILEPATH, true), Charset.forName("UTF-16")));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(data_file, true),Charset.forName("UTF-16")));
				String temp = "";
				while((temp = br.readLine()) != null){
					bw.write(temp);
					bw.write("\r\n");
				}
				bw.close();
				br.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * @param als
	 *            app列表
	 * @param shortcut_head
	 *            app种类标签
	 */
	public static void writeToTxt(ArrayList<String> als, String shortcut_head) {
		// ArrayList<String> readApps = readTxt(shortcut_head);
		String s = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(Common.FILEPATH)),
					Charset.forName("UTF-16")));
			while ((s = br.readLine()) != null) {
				if (!(s.indexOf(shortcut_head) != -1)) {
					als.add(s);
				}
			}

			int[] arr = new int[als.size()-1];
			for(int i = 0;i<als.size()-1;i++){
				for (int j = i+1;j<als.size();j++){
					if(als.get(i).equals(als.get(j))){
						als.remove(j);
					}
				}
			}
			br.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OutputStreamWriter write = null;
			BufferedWriter out = null;
			try {
				File file = new File(Common.FILEPATH);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				// new FileOutputStream(fileName, true) 第二个参数表示追加写入
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						Common.FILEPATH, true), Charset.forName("UTF-16")));// 一定要使用gbk格式
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < als.size(); i++) {

				out.write((String) als.get(i));
				out.write("\r\n");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *
	 * @param shortcut_head
	 *            传入app种类
	 * @return
	 */
	public static ArrayList<String> readTxt(String shortcut_head) {
		ArrayList<String> als = new ArrayList<String>();
		File file = new File(Common.FILEPATH);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), Charset.forName("UTF-16")));
			while ((s = br.readLine()) != null) {
				if (s.indexOf(shortcut_head) != -1) {
					// s = s.replaceAll(" ","");
					s = s.replaceAll(shortcut_head, "");
					als.add(s);
				}
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return als;
	}

	/**
	 * 查询显示某个种类的App
	 *
	 *
	 * @param mContext
	 * @return
	 */

	public static ArrayList<AppInfo> queryAppInfo(Context mContext,ArrayList<String> appPackageNames) {
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
		pm = mContext.getPackageManager();// 获取PackageManager对象
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER); // 通过查询，获取所有ResolveInfo对象
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
		// 调用系统排序，根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
		Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));
		for (ResolveInfo reInfo : resolveInfos) {
			String appLabel = (String) reInfo.loadLabel(pm);
			String appPackage = (String) reInfo.activityInfo.packageName;
			//Log.i("Util","----------"+appPackage);
			if(appPackageNames != null){
				for (String appPackageName : appPackageNames) {
					if (appPackage.equals(appPackageName.trim())) {
						apps.add(getAppInfo(reInfo));
					}
				}
			}else{
				apps.add(getAppInfo(reInfo));// 添加到ArrayList中
			}
		}
		return apps;
	}

	public static AppInfo getAppInfo(ResolveInfo reInfo){
		String activityName = reInfo.activityInfo.name;// 获得该应用程序的启动 Activity的name
		String pkgName = reInfo.activityInfo.packageName;// 获得应用程序包名
		String appLabel = (String) reInfo.loadLabel(pm);// 获得应用程序的 Label
		Drawable icon = reInfo.loadIcon(pm);// 获得应用程序图标
		Intent launchIntent = new Intent();//为应用程序的启动 Activity准备Intent
		launchIntent.setComponent(new ComponentName(pkgName, activityName)); // 创建一个AppInfo对象，并赋值
		AppInfo appInfo = new AppInfo();
		appInfo.setAppName(appLabel);
		appInfo.setPackageName(pkgName);
		appInfo.setAppIcon(icon);
		appInfo.setIntent(launchIntent);
		return appInfo;
	}
	//通过包名启动其他App的Activity
	public static void doStartApplicationWithPackageName(Context mContext,String packagename) {

		// 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
		PackageInfo packageinfo = null;
		try {
			packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageinfo == null) {
			return;
		}

		// 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageinfo.packageName);

		// 通过getPackageManager()的queryIntentActivities方法遍历
		List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo resolveinfo = resolveinfoList.iterator().next();
		if (resolveinfo != null) {
			// packagename = 参数packname
			String packageName = resolveinfo.activityInfo.packageName;
			// 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
			String className = resolveinfo.activityInfo.name;
			// LAUNCHER Intent
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			// 设置ComponentName参数1:packagename参数2:MainActivity路径
			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			mContext.startActivity(intent);
		}
	}




	// 查询全部
	public static ArrayList<AppInfo> queryAppInfo(Context mContext) {
		return queryAppInfo(mContext, null);
	}

	//  查询分类 暂停使用，原名queryAppInfo
	public static ArrayList<AppInfo> queryAppInfo1(Context mContext,
			ArrayList<String> appNames) {
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
		pm = mContext.getPackageManager();// 获取PackageManager对象
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		// 调用系统排序，根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));

		for (ApplicationInfo app : listAppcations) {
			String applabel = ((String) app.loadLabel(pm)).trim();
			if (appNames != null) {
				for (String appName : appNames) {
					if (applabel.equals(appName.trim())) {
						apps.add(getAppInfo1(app));
					}
				}
			} else {
				// if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)
				// {//判断是否是第三方软件
				// <=0:是 >0:否
				apps.add(getAppInfo1(app));// 添加到ArrayList中
				// }
			}
		}
		return apps;
	}
//   暂停使用，原名getAppInfo
	public static AppInfo getAppInfo1(ApplicationInfo app) {
		// 创建一个AppInfo对象，并赋值
		AppInfo appInfo = new AppInfo();
		appInfo.setAppName((String) app.loadLabel(pm));
		appInfo.setPackageName(app.packageName);
		appInfo.setAppIcon(app.loadIcon(pm));
		Intent launcherIntent = new Intent();
		launcherIntent = pm.getLaunchIntentForPackage(app.packageName);
		appInfo.setIntent(launcherIntent);
		return appInfo;
	}
}
