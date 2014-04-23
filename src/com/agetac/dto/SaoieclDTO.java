package com.agetac.dto;
///**
// * 
// */
//package com.agetac.dto;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.agetac.model.*;
//
//import com.agetac.dto.dto_config.DTO;
//
///**
// * @author jimmy
// *
// */
//public class SaoieclDTO extends DTO<SAOIECL>{
//
//	@Override
//	public SAOIECL[] findAll(int limit) {
//		return null;
//	}
//
//	@Override
//	public SAOIECL[] find(String[] champs, int limit) {
//		return null;
//	}
//
//	@Override
//	public SAOIECL findById(int id) {
//		SAOIECL m = null;
//		try {
//			ResultSet result = this.connect
//					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//							ResultSet.CONCUR_UPDATABLE)
//					.executeQuery(
//							"SELECT * FROM message,typemessage WHERE message.id = " + id
//							+ " AND libelletypemessage = SAOIELC ");
//			if (result.first()) {
//				m = new SAOIECL(id,result.getInt("id_intervention"),result.getDate("dateenvoi")
//						,result.getInt("id_persemetteur"),result.getInt("id_typemessage"),
//						result.getString("textemessage"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return m;
//	}
//
//	@Override
//	public SAOIECL create(SAOIECL obj) {
//		try {
//			ResultSet result = this.connect.createStatement(
//					ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE).executeQuery(
//					"SELECT NEXTVAL('soiecl_id') as id");
//			if (result.first()) {
//				int id = result.getInt("id");
//				PreparedStatement prepare = this.connect
//						.prepareStatement("INSERT INTO SAOIECL (x,xx, xxx, xxxx)"
//								+ "VALUES(?, ?, ?, ?)");
//				prepare.setInt(1, id);
//				prepare.executeUpdate();
//				obj = this.findById(id);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}
//
//	@Override
//	public SAOIECL update(SAOIECL obj) {
//		return null;
//	}
//
//	@Override
//	public void delete(SAOIECL obj) {
//		try {
//            this    .connect
//                	.createStatement(
//                         ResultSet.TYPE_SCROLL_INSENSITIVE, 
//                         ResultSet.CONCUR_UPDATABLE
//                    ).executeUpdate(
//                         "DELETE FROM SAOIECL WHERE id = " + obj.getId()
//                    );
//		
//	    } catch (SQLException e) {
//	            e.printStackTrace();
//	    }
//	}
//
//}
