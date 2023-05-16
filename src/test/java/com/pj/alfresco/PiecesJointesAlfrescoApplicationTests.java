package com.pj.alfresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PiecesJointesAlfrescoApplicationTests {

	/*
	@Value("${alfresco.login}")
	private String login;

	@Value("${alfresco.password}")
	private String password;

	@Value("${alfresco.url}")
	private String url;

	public Session conf() {
		SessionFactoryImpl sessionFactoryImpl = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<>();
		parameter.put("org.apache.chemistry.opencmis.user", this.login);
		parameter.put("org.apache.chemistry.opencmis.password",this.password);
		parameter.put("org.apache.chemistry.opencmis.binding.atompub.url", this.url);
		parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.ATOMPUB.value());
		Session session = ((Repository) sessionFactoryImpl.getRepositories(parameter).get(0)).createSession();
		return session;
	}

	
	private List<String> getListFolder(){
		List<String> list =  new ArrayList<String>();
		list.add("Publicitaire");
		list.add("ProjetUrbanisme");
		list.add("ProjetPartenariat");
		list.add("Locaux");
		list.add("InterventionRapide");
		list.add("Impression");
		list.add("Immobilisation");
		list.add("Hebergement");
		list.add("Convention");
		list.add("Association");
		list.add("AD_Projet");
		list.add("loyerApptActe");
		list.add("appartementPhotos");
		list.add("AD_DomainePublic");
		list.add("habitationDoc");
		list.add("habitationPhotos");
		list.add("AD_LigneLoyerDomaine");
		list.add("AD_LigneLoyerToilette");
		list.add("AD_LoyerDomaine");
		list.add("AD_LoyerToilette");
		list.add("magasinActe");
		list.add("magasinPhotos");
		list.add("AD_PhaseProjet");
		list.add("AD_toilette");
		list.add("AD_Prestataire");
		list.add("Reservations");
		list.add("ReclamationsTraitements");
		list.add("Reclamations");
		list.add("Autorisations");
		list.add("Activite");
		list.add("Partagé");
		return list;
	}
	*/
	
	@Test
	void contextLoads() {
		/*
		Session session = conf();
		Folder root = session.getRootFolder();
		Map<String, Object> properties = new HashMap<>();
		List<String> aspects = new ArrayList<>();
        aspects.add("P:cm:titled");
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		for(String fld : getListFolder()) {
			properties.put(PropertyIds.NAME, fld);
			properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS, aspects);
			properties.put("cm:title", fld);
	        properties.put("cm:description", fld);
			Folder newFolder =root.createFolder(properties);
			System.out.println("Folder : " + newFolder.getName());
		}
		*/
	}

}
