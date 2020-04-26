package com.weaver.inte.activity.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tk.mybatis.mapper.util.StringUtil;

/**
 * @description sql模板解析工具类
 * @author lzy
 * @date:2020年4月20日 下午5:29:52
 * @version v1.0
 */
public class SqlTemplete {
	private final static Pattern PTN_IFNULL = Pattern.compile("<if[\\s]*test[\\s]*=[\\s]*\"([^\"]*)\"[\\s]*>[\\s]*(.*)[\\s]*</if>");
	private final static Pattern PTN_GENERAL = Pattern.compile("\\$\\{([^}]*)\\}");
	private final static Pattern PTN_LOGICAL = Pattern.compile("(\\$\\{[^}]*\\})[\\s]*(==|>=|<=|>|<|\\^=|\\$=|~~|!=)([^\"]*)");
	
	public static String parse(String sql,Map map) throws Exception{
		sql = sql.replaceAll("--.*", "");
		sql = replaceIf(sql,map);
		sql = replaceGeneral(sql,map);
		return sql;
	}
	
	/***
	 * if逻辑表达式替换
	 * @throws Exception 
	 */
	private static String replaceIf(String sql,Map map) throws Exception{
		Matcher mac = PTN_IFNULL.matcher(sql);
		while(mac.find()){
			String testExp = mac.group(1).trim();
			String valueExp = mac.group(2).trim();
			// 如果语替换的句中表达式存在有等值判断的表达式，则解析后再替换
			if(PTN_LOGICAL.matcher(testExp).find()){
				if(logicalDecision(testExp,map)){
					if(PTN_GENERAL.matcher(valueExp).find()){
						valueExp = replaceGeneral(valueExp, map).toString();
					}
					mac.reset(mac.replaceFirst(" " + valueExp));
				    continue;
				}
				mac.reset(mac.replaceFirst(""));
			}else if(testExp.matches(PTN_GENERAL.pattern())){
				// 处理语句中test=${}的语法公式
				String[] taskExpArr = regex(PTN_GENERAL, testExp, 1);
				String mapKey = taskExpArr[1];
				String mapVal = StringUtils.ifNull(map.get(mapKey)).trim();
				if(!map.containsKey(mapKey) || StringUtils.isNull(mapVal)){
					mac.reset(mac.replaceFirst(""));
				    continue;
				}
				if(PTN_GENERAL.matcher(valueExp).find()){
					valueExp = replaceGeneral(valueExp, map).toString();
				}
				mac.reset(mac.replaceFirst(" " + valueExp));
			}else{
				throw new Exception("暂时不支持公式：[" + testExp+"]");
			}
		}
		return mac.appendTail(new StringBuffer()).toString();
	}
	
	/**
	 * 非if逻辑表达式替换逻辑
	 */
	private static String replaceGeneral(String sql,Map map){
		Matcher mac = PTN_GENERAL.matcher(sql);
		while(mac.find()){
			String mapKey = mac.group(1);
			String mapVal = valByType(map, mapKey);
			if(map.containsKey(mapKey) && StringUtils.isNotNull(mapVal)){
				 mac.reset(mac.replaceFirst(mapVal));
			}else{
			    mac.reset(mac.replaceFirst(""));
			}
		}
		return mac.appendTail(new StringBuffer()).toString();
	}
	
	/***
	 * 逻辑判断
	 * @throws Exception 
	 */
	private static boolean logicalDecision(String content,Map map) throws Exception{
		String[] lab = regex(PTN_LOGICAL, content, 3);
		String left =  lab[1];
		String op =  lab[2];
		String right =  lab[3];

		if(StringUtils.isNotNull(left) && PTN_GENERAL.matcher(left).find()){
			String[] taskExpArr = regex(PTN_GENERAL, left, 1);
			String leftKey = taskExpArr[1];
			left = StringUtils.ifNull(map.get(leftKey)).trim();
		}
		
		if(StringUtils.isNotNull(right) && PTN_GENERAL.matcher(right).find()){
			String[] taskExpArr = regex(PTN_GENERAL, right, 1);
			String leftKey = taskExpArr[1];
			right = StringUtils.ifNull(map.get(leftKey)).trim();
		}
		
		if(StringUtils.isNull(left) || StringUtils.isNull(right) || StringUtils.isNull(op)){
			return false;
		}
		
		switch (op) {
			case "==":
				return left.contentEquals(right);
			case ">":
				return Integer.parseInt(left) > Integer.parseInt(right);
			case "<":
				return Integer.parseInt(left) < Integer.parseInt(right);
			case ">=":
				return Integer.parseInt(left) >= Integer.parseInt(right);
			case "<=":
				return Integer.parseInt(left) <= Integer.parseInt(right);
			case "^=":
				return left.startsWith(right);
			case "$=":
				return left.endsWith(right);
			case "~~":
				return left.contains(right);
			case "!=":
				return !left.contentEquals(right);
			default:
				throw new Exception("暂时不支持运算：[" + op + "]");
		}
	}
	
	private static String[] regex(Pattern ptn,String content,int group){
		Matcher mac = ptn.matcher(content);
		String[] rst = new String[group + 1];
		if(mac.find()){
			rst[0] = mac.group(0); 
			for (int i = 1; i <= group; i++) {
				rst[i] = mac.group(i).trim();
			}
		}
		return rst;
	}

	/***
	 * 按照值的类型赋值，目前支持字符串类型、List 类型
	 */
	private static String valByType(Map map,String key){
		Object val = map.get(key);
		if(val instanceof List){
			StringBuilder sb = new StringBuilder();
			List lst = (List) val;
			Object o = null;
			sb.append("(");
			for (int i = 0; i < lst.size(); i++) {
				sb.append(i == 0 ? "" : ",");
				o = lst.get(i);
				if(o instanceof Number){
					sb.append(StringUtils.ifNull(o).trim());
				}else{
					sb.append("'"+StringUtils.ifNull(o).trim()+"'");
				}
			}
			sb.append(")");
			return sb.toString();
		}else{
			return StringUtils.ifNull(val).trim();
		}
	}
}
