package com.wellness49.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
	public String appName = ""; //app 名称
	public String packageName = "";//app包名
	public Intent intent;//启动应用程序的Intent，一般是Action为Main和Category为launcher的Activity
	public Drawable appIcon = null;//app图标
	public boolean isSelect = false;
	
	public AppInfo(){}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * @return the intent
	 */
	public Intent getIntent() {
		return intent;
	}
	/**
	 * @param intent the intent to set
	 */
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	/**
	 * @return the appIcon
	 */
	public Drawable getAppIcon() {
		return appIcon;
	}
	/**
	 * @param appIcon the appIcon to set
	 */
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	/**
	 * @return the isSelect
	 */
	public boolean isSelect() {
		return isSelect;
	}
	/**
	 * @param isSelect the isSelect to set
	 */
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
	
}
