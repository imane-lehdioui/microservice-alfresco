package com.pj.alfresco.Controllers;

//import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import com.pj.alfresco.Models.ObjectBytes;
import com.pj.alfresco.Models.PjCourriersEntrants;
//import com.pj.alfresco.Models.PjUsers;
import com.pj.alfresco.Repositories.PjCourriersEntrantsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author R.SABRI Date de creation:10/02/20
 *
 */

@RestController
@RequestMapping("/PjCourriersEntrants")
public class PjCourriersEntrantsController {
	private static final Logger logger  = LoggerFactory.getLogger(PjCourriersEntrantsController.class);

	@Autowired
	PjCourriersEntrantsRepo pjCourriersEntrant;

	@Value("${alfresco.login}")
	private String login;

	@Value("${alfresco.password}")
	private String password;

	@Value("${alfresco.url}")
	private String url;

	public Session conf() {
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, login);
		parameter.put(SessionParameter.PASSWORD, password);

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, url);

		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// create session
		Session session = factory.getRepositories(parameter).get(0).createSession();
		return session;
	}

	@DeleteMapping("/{id}")
	public Optional<PjCourriersEntrants> delete(@PathVariable long id) {

		Session session = conf();
		Optional<PjCourriersEntrants> r = pjCourriersEntrant.findById(id);
		String AlfId = r.get().getIdAlfresco();
		System.out.println(AlfId.substring(0, AlfId.length() - 4));
		Document hj = (Document) session.getObject(AlfId.substring(0, AlfId.length() - 4));
		// hj.deleteContentStream();
		hj.delete();
		pjCourriersEntrant.deleteById(id);
		logger.info("CourrierEntrant file deleted");
		return r;
	}

	@DeleteMapping("/ByIdCourriersEntrants/{id}")
	public void deleteByIdCourrierEntrant(@PathVariable long id) {
		Session session = conf();
		List<PjCourriersEntrants> pjList = pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(id);
		for (PjCourriersEntrants f : pjList) {
			String idPJ = f.getIdAlfresco();
			Document doc = (Document) session.getObject(idPJ.substring(0, idPJ.length() - 4));
			if (doc != null) {
				doc.delete();
				pjCourriersEntrant.deleteById(f.getId());
			}
		}

	}

	@GetMapping("/Allpjs/{idCourrierEntrant}")
	public List<PjCourriersEntrants> AllSeverite(@PathVariable long idCourrierEntrant) {
		System.out.println("in traitement");
		Session session = conf();
		logger.info("Get all files courrierEntrant");
		return pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(idCourrierEntrant);
	}

	@GetMapping("/{idAlfresco}")
	public void alfGed(@PathVariable String idAlfresco, HttpServletResponse response) {
		System.out.println(idAlfresco);
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);

		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		response.addHeader("Content-Disposition",
				"attachment; filename=" + pjCourriersEntrant.findByIdAlfresco(id_alfresco).getName());
		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();

			IOUtils.copy(input, output);

			output.flush();
			logger.info("File returned");
		} catch (IOException e) {
			logger.info("File not found!!");
		}
	}
	@RequestMapping(path = "/multiplefile", method = RequestMethod.POST)
	public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id)
			throws IOException {

		System.out.println("ID output" + id);
		Session session = conf();
		Folder root = session.getRootFolder();

		logger.info("Configuration session  :: courrier entrant");

		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			logger.info("File temp created  :: courrier entrant");
			PjCourriersEntrants pj = new PjCourriersEntrants();
			pj.setIdCourrierEntrant(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setDateFile(new Date());
			PjCourriersEntrants pjUpdate = pjCourriersEntrant.save(pj);
			logger.info("Save files in DB  :: courrier entrant");

			Map<String, Object> properties2 = new HashMap<String, Object>();
			properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			//properties2.put(PropertyIds.NAME, "" + pjUpdate.getId()+"-"+ pjUpdate.getName());
			properties2.put(PropertyIds.NAME, "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("CourrierEntrant")) {

					ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					org.apache.chemistry.opencmis.client.api.Document newDoc = ((Folder) child)
							.createDocument(properties2, contentStream, VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());

					pjCourriersEntrant.save(pjUpdate);
					logger.info("Files inserted :: courrier entrant");
				}
			}
		}

		return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
	}

}
