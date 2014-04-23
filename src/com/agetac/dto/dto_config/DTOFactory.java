/*	
* Projet AGETAC	
* Jimmy Dano	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.dto.dto_config;


import com.agetac.dto.*;
import com.agetac.model.*;

/**
* Classe DTOFactory : Factory pour la cr�ation de nos DTO
* toute les DTO sont regroup�es ici, il suffit de les utiliser dans nos classes d�sormais
* 
* @version 2.0 30/01/2013
* @author Jimmy DANO - 13000159
*/
public class DTOFactory {

	/**
	 * M�thode qui cr�� un DTO Intervention
	 * @return DTO<Intervention>
	 */
	
	public static DTO<Intervention> getIntervention(){
		return new InterventionDTO();
	}
	
	public static DTO<Vehicule> getVehicule(){
		return new VehiculeDTO();
	}
	
	public static DTO<GroupeIntervention> getGroupeInter(){
		return new GroupeInterventionDTO();
	}
	
	public static DTO<Adresse> getAdresse(){
		return new AdresseDTO();
	}
	
	public static DTO<Agent> getAgent(){
		return new AgentDTO();
	}
	
	public static DTO<Caserne> getCaserne(){
		return new CaserneDTO();
	}
	
	public static DTO<Coordonnees> getCoordonnee(){
		return new CoordonneeDTO();
	}
	
	public static DTO<SecteurIntervention> getSpinnerChoixSecteur(){
		return new SecteurInterDTO();
	}
	
	public static DTO<TypeMessage> getTypeMessage(){
		return new TypeMessageDTO();
	}
	
	public static DTO<Message> getMessage(){
		return new MessageDTO();
	}
	
//	public static DTO<SAOIECL> getSaoiecl(){
//		return new SaoieclDTO();
//	}
	
}// class