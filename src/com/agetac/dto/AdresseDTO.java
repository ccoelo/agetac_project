package com.agetac.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Adresse;
import com.agetac.model.Message;


/**
 * 
 * @author jimmy
 *
 */

public class AdresseDTO extends DTO<Adresse>{

	@Override
	public Adresse[] findAll(int limit) {
		return null;
	}

	@Override
	public Adresse[] find(String[] champs, int limit) {
		return null;
	}

	@Override
	public Adresse findById(int id) {
		Adresse a = null;
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE)
					.executeQuery(
							"SELECT * FROM adresse WHERE id = " + id);
			if (result.first()) {
				a = new Adresse(result.getInt("numVoie"),result.getString("nomvoie"),
						result.getString("ville"),result.getInt("codepostal"),
						result.getInt("etage"),result.getInt("id_coordgps"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public boolean create(Adresse obj) {
		return true;
	}

	@Override
	public Adresse update(Adresse obj) {
		return null;
	}

	@Override
	public void delete(Adresse obj) {
		try {
            this    .connect
                	.createStatement(
                         ResultSet.TYPE_SCROLL_INSENSITIVE, 
                         ResultSet.CONCUR_UPDATABLE
                    ).executeUpdate(
                         "DELETE FROM Adresse WHERE id = " + obj.getId()
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
