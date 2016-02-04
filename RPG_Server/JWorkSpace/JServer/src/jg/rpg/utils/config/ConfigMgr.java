package jg.rpg.utils.config;

import java.awt.Component;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.bean.BeanDocumentFactory;
import org.dom4j.io.SAXReader;

import jg.rpg.entity.DBEntityInfo;
import jg.rpg.entity.NetEntityInfo;
import jg.rpg.exceptions.InitException;

public class ConfigMgr {

	private DBEntityInfo strogeDbInfo;
	private NetEntityInfo mainNetInfo;
	
	private static boolean isInit;
	private static ConfigMgr inst;
	private ConfigMgr(){
	}
	public static ConfigMgr getInstance(){
		synchronized (ConfigMgr.class) {
			if(inst == null){
				inst = new ConfigMgr();
			}
		}
		return inst;
	}
	public DBEntityInfo getStrogeDbInfo() {
		return strogeDbInfo;
	}
 

	public void init() throws Exception {
		String path = System.getProperty("user.dir")+
				File.separator+"config"+File.separator+"config.xml";
		Document document = parse(path);
		if(document != null){
			process(document);
		}else{
			throw new InitException("ConfigMgr init error!!!");
		}
	}
	
	private Document parse(String url) throws Exception {
        SAXReader reader = new SAXReader(BeanDocumentFactory.getInstance());
        return reader.read(url);
    }
	
    private void process(Document document) throws Exception {
    	Element root = document.getRootElement();
    	readDBInfo(root);
    	readGameConfig(root);
    }
	    
	/**
	 * ��ȡDB��Ϣ
	 * @param root
	 */
	private void readDBInfo(Element root){
    	Element eDB = root.element("db");
    	Element eSDB = eDB.element("StorageDB");
    	strogeDbInfo = new DBEntityInfo();
    	strogeDbInfo.setDriver(eSDB.elementTextTrim("driver"));
    	strogeDbInfo.setUser(eSDB.elementTextTrim("user"));
    	strogeDbInfo.setPwd(eSDB.elementTextTrim("passworld"));
    	strogeDbInfo.setUrl(eSDB.elementTextTrim("url"));
    }
	
    private void readGameConfig(Element root) {
    	Element eGC = root.element("gameConfig");
    	GameConfig.DefEncoding = eGC.elementTextTrim("encoding");
    	GameConfig.MsgHeadLen = Integer.parseInt(eGC.elementTextTrim("msgHeadLen"));
	}
    
    

}
