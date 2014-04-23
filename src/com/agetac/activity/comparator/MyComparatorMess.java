/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.comparator;

import java.util.Comparator;

import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.model.Agent;
import com.agetac.model.Message;


/**
 * @author jimmy
 *
 */
public class MyComparatorMess implements Comparator<Message>{

	private int type;

	public MyComparatorMess(int type) {
	    this.type = type;
	}
	
	public int compare(Message lhs, Message rhs) {
		int res = 0;
//		//DTO<Agent> agents = DTOFactory.getAgent();
//		if (type == 0) {
//            res  = (rhs.getD()).compareTo(lhs.getD());
//        }
//        else if (type == 1) {
//        	//res = (agents.findById(rhs.getAuteur()).getNom()).compareTo(agents.findById(lhs.getAuteur()).getNom());
//        } else {
//        	res = (lhs.getType()).compareToIgnoreCase(rhs.getType());
//        }
        return res;
	}
}
