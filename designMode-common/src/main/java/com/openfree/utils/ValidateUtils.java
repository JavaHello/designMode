package com.openfree.utils;

import java.util.regex.Pattern;


/**
 * 验证工具类
 * Created by luokai on 17-7-13.
 */
public class ValidateUtils {
	
	/**
	 * 判断传入参数是否为字母与数字的组合字符串,而不是单一的字母或者数字
	 * @param pwd
	 * @return
	 */
	public static boolean isPassword(String pwd){
		if(ValidateUtils.isInteger(pwd)){//是否全部为数字
			return false;//全部为数字
		}else{
			if(ValidateUtils.isLettersOnly(pwd)){
				return false;//全部为字母
			}else{
				if(ValidateUtils.isAlphanumeric(pwd)){
					return true;//正常数据
				}else{
					return false;//包含了非法数据
				}
			}
		}
	}

	/**
	 * 判断字母、数字（适用于密码校验）.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false capital
	 */
	public static boolean isAlphanumeric(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 必须为字母.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return true or false .
	 */
	public static boolean isLettersOnly(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
