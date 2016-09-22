package com.wafersystems.workflow.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @description:jdbc操作类
 * @author 皇兄
 */
public class BaseJdbcDao extends JdbcDaoSupport {
	
	public JdbcTemplate geJDBCTemplate(){
		 return this.getJdbcTemplate();
	}

	public List executeSqlForList(String sql){
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public Map executeForMap(String sql) {
		return getJdbcTemplate().queryForMap(sql);
	}

   public Map executeForMap(String sql,Object [] objectvalue) {
		return getJdbcTemplate().queryForMap(sql,objectvalue);
	}
	
    public int update(String sql){
    	try{
    		return getJdbcTemplate().update(sql);
    	}catch (Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
    
	public int update(String sql,Object[] objectvalue){
		try{
			return getJdbcTemplate().update(sql,objectvalue);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
