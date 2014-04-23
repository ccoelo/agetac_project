package com.agetac.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Caserne;


/**
 * 
 * @author jimmy
 *
 */
public class CaserneDTO extends DTO<Caserne>{

	@Override
	public Caserne[] findAll(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caserne[] find(String[] champs, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caserne findById(int id) {
		Caserne c = new Caserne();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE)
					.executeQuery(
							"SELECT * FROM Caserne WHERE id = " + id);
			if (result.first()) {
				c = new Caserne(/*id /*
											 * , result.getString(
											 * "nom du champ à récup")
											 */);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public boolean create(Caserne obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Caserne update(Caserne obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Caserne obj) {
		try {
            this    .connect
                	.createStatement(
                         ResultSet.TYPE_SCROLL_INSENSITIVE, 
                         ResultSet.CONCUR_UPDATABLE
                    ).executeUpdate(
                         "DELETE FROM Caserne WHERE id = " + obj.getId()
                    );
		
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}

	/* (non-Javadoc)
	 * @see com.agetac.dto.dto_config.DTO#find(int, boolean)
	 */
	@Override
	public int find(int i, boolean b) {
		// TODO Auto-generated method stub
		return 0;
	}
}
