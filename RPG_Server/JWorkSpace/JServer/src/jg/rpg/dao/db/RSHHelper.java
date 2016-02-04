package jg.rpg.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import jg.rpg.entity.Cat;
import jg.rpg.entity.PlayerEntity;
/**
 * ���д���model���ORMӳ������
 * @author jiuguang
 */
public class RSHHelper {
	
	public static ResultSetHandler<PlayerEntity> getPlayerRSH(){
		ResultSetHandler<PlayerEntity> rsh = new ResultSetHandler<PlayerEntity>(){
			@Override
			public PlayerEntity handle(ResultSet rs) throws SQLException {
				return null;
			}
		};
		return rsh;
	}
	
	public static ResultSetHandler<Cat> getCatRSH(){
		ResultSetHandler<Cat> rsh = new ResultSetHandler<Cat>(){
			@Override
			public Cat handle(ResultSet rs) throws SQLException {
				if(!rs.next()){
					return null;
				}
				Cat cat = new Cat();
				cat.setId(rs.getString(1));
				cat.setBreed(rs.getString(2));
				cat.setName(rs.getString(3));
				return cat;
			}
		};
		return rsh;
	}
}
