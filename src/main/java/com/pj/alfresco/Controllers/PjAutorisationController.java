package com.pj.alfresco.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.alfresco.Models.pjAutorisations;
import com.pj.alfresco.Models.pjTraitementAutorisations;
import com.pj.alfresco.Repositories.PjAutorisationRepo;
import com.pj.alfresco.Repositories.PjTraitementAutorisationRepo;

@RestController
@RequestMapping({ "/PjAutorisations" })
public class PjAutorisationController {
	@Autowired
	PjAutorisationRepo pjAutRepo;

	@Autowired
	PjTraitementAutorisationRepo pjAutTraitementRepo;

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
		parameter.put("org.apache.chemistry.opencmis.password", this.password);
		parameter.put("org.apache.chemistry.opencmis.binding.atompub.url", this.url);
		parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.ATOMPUB.value());
		Session session = ((Repository) sessionFactoryImpl.getRepositories(parameter).get(0)).createSession();
		return session;
	}

	@GetMapping({ "/Allpjs/{idAutorisation}" })
	public List<pjAutorisations> AllSeverite(@PathVariable long idAutorisation) {
		Session session = conf();
		/*
		 * List<pjAutorisations> a = new ArrayList<>(); List<pjAutorisations> listePjs =
		 * this.pjAutRepo.findByIdAautorisation(idAutorisation); for (int i = 0; i <
		 * listePjs.size(); i++) { Document hj = (Document)
		 * session.getObject(((pjAutorisations) listePjs.get(i)).getIdAlfresco());
		 * pjAutorisations objb = new pjAutorisations(); objb.setId(((pjAutorisations)
		 * listePjs.get(i)).getId()); objb.setName(((pjAutorisations)
		 * listePjs.get(i)).getName()); objb.setIdAlfresco(hj.getId()); a.add(objb); }
		 * return a;
		 */
		return pjAutRepo.findByIdAutorisationOrderByIdDesc(idAutorisation);
	}

	@GetMapping({ "/AllpjsTraitement/{idAutorisation}" })
	public List<pjTraitementAutorisations> Allpjstraitement(@PathVariable long idAutorisation) {
		Session session = conf();
		/*
		 * List<pjTraitementAutorisations> a = new ArrayList<>();
		 * List<pjTraitementAutorisations> listePjs =
		 * this.pjAutTraitementRepo.findByIdAautorisation(idAutorisation); for (int i =
		 * 0; i < listePjs.size(); i++) { Document hj = (Document)
		 * session.getObject(((pjTraitementAutorisations)
		 * listePjs.get(i)).getIdAlfresco()); pjTraitementAutorisations objb = new
		 * pjTraitementAutorisations(); objb.setId(((pjTraitementAutorisations)
		 * listePjs.get(i)).getId()); objb.setName(((pjTraitementAutorisations)
		 * listePjs.get(i)).getName()); objb.setIdAlfresco(hj.getId()); a.add(objb); }
		 * return a;
		 */
		return pjAutTraitementRepo.findByIdAutorisationOrderByIdDesc(idAutorisation);
	}

	@GetMapping({ "/{idAlfresco}" })
	public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
		System.out.println(idAlfresco);
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);

		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		response.addHeader("Content-Disposition",
				"attachment; filename=" + this.pjAutRepo.findByIdAlfresco(id_alfresco).getName());
		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
		} catch (IOException iOException) {
			iOException.getStackTrace();
		}
	}

	@GetMapping({ "/traitement/{idAlfresco}" })
	public void rec1(@PathVariable String idAlfresco, HttpServletResponse response) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);

		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		response.addHeader("Content-Disposition",
				"attachment; filename=" + this.pjAutTraitementRepo.findByIdAlfresco(id_alfresco).getName());
		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
		} catch (IOException iOException) {
			iOException.getStackTrace();
		}
	}

	@RequestMapping(path = { "/multiplefile" }, method = { RequestMethod.POST })
	public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id)
			throws IOException {
		Session session = conf();
		Folder root = session.getRootFolder();
		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			pjAutorisations pj = new pjAutorisations();
			pj.setIdAutorisation(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setDateFile(new Date());
			pjAutorisations pjUpdate = (pjAutorisations) this.pjAutRepo.save(pj);
			System.out.println("File inserted !");

			Map<String, Object> properties2 = new HashMap<>();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("Autorisations")) {
					ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder) child).createDocument(properties2, (ContentStream) contentStreamImpl,
							VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.pjAutRepo.save(pjUpdate);
				}
			}
		}
		return new ResponseEntity("Successful", null, HttpStatus.OK);
	}

	@RequestMapping(path = { "/Traitementmultiplefile" }, method = { RequestMethod.POST })
	public ResponseEntity<String> processFile1(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id)
			throws IOException {
		Session session = conf();
		Folder root = session.getRootFolder();
		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			pjTraitementAutorisations pj = new pjTraitementAutorisations();
			pj.setIdAutorisation(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setDateFile(new Date());
			pjTraitementAutorisations pjUpdate = (pjTraitementAutorisations) this.pjAutTraitementRepo.save(pj);
			System.out.println("File inserted !");

			Map<String, Object> properties3 = new HashMap<>();
			properties3.put("cmis:objectTypeId", "cmis:document");
			properties3.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("AutorisationsTraitement")) {
					ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder) child).createDocument(properties3, (ContentStream) contentStreamImpl,
							VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.pjAutTraitementRepo.save(pjUpdate);
				}
			}
		}
		return new ResponseEntity("Successful", null, HttpStatus.OK);
	}
}
