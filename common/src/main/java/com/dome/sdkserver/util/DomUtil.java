package com.dome.sdkserver.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomUtil {
	protected final static Logger log = LoggerFactory.getLogger(DomUtil.class);
	
	/**
	 * 
	 * @param xml 
	 * @param nodePath xml节点路径
	 */
	public static List<String> dealToItem(String xml,String nodePath) throws Exception{
        Document document = null;
        List<String> nodeXmlList = new ArrayList<String>();
        try {
        	document = DocumentHelper.parseText(xml);  
        } catch (Exception e) {
        	log.error("文件转换DOM对象失败:" + e.getMessage() + "~~ xml:" + xml);
            throw new Exception("文件转换DOM对象失败");
        }
        List nodeList = document.selectNodes(nodePath);
        for(Object node : nodeList){
        	Element ele = (Element)node;
        	nodeXmlList.add(ele.asXML());
        }
        return nodeXmlList;
	}
	
	/**
	 * 
	 * @param xml 
	 * @param nodePath xml节点路径
	 * @param attr 得到节点属性
	 */
	public static List<String> dealToItemAttr(String xml,String nodePath,String attr) throws Exception{
        Document document = null;
        try {
        	document = DocumentHelper.parseText(xml);  
        } catch (Exception e) {
        	log.error("文件转换DOM对象失败:" + e.getMessage() + "~~ xml:" + xml, e);
            throw new Exception("文件转换DOM对象失败");
        }
        List nodeList = document.selectNodes(nodePath);
        List<String> attrList = new ArrayList<String>();
        for(Object node : nodeList){
        	Element ele = (Element)node;
        	attrList.add(ele.attributeValue(attr));
        }
        return attrList;
	}
	
	/**
	 * 
	 * @param xml 
	 * @param nodePath xml节点路径
	 * @param attr 得到节点属性
	 */
	public static List<String> dealToItemText(String xml,String nodePath) throws Exception{
        Document document = null;
        try {
        	document = DocumentHelper.parseText(xml);  
        } catch (Exception e) {
        	log.error("文件转换DOM对象失败:" + e.getMessage() + "~~ xml:" + xml);
            throw new Exception("文件转换DOM对象失败");
        }
        List nodeList = document.selectNodes(nodePath);
        List<String> attrList = new ArrayList<String>();
        for(Object node : nodeList){
        	Element ele = (Element)node;
        	attrList.add(ele.getText());
        }
        return attrList;
	}
}
