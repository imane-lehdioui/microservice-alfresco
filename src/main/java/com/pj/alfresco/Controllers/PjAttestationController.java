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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.alfresco.Models.PjAttestation;
import com.pj.alfresco.Repositories.PjAttestationRepo;

@RestController
@RequestMapping("/PjAttestations")
public class PjAttestationController {

	@Autowired
	private PjAttestationRepo pjAttestationRepo;

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

	@GetMapping("/index/{idAttestation}")
	public List<PjAttestation> AllPjAttestation(@PathVariable long idAttestation) {
		System.out.println("in traitement");

		return pjAttestationRepo.findAllByIdAttestation(idAttestation);
	}

	@GetMapping("/show/{idAttestation}")
	public PjAttestation getPjAttestationById(@PathVariable long idAttestation) {
		System.out.println("in traitement");

		return pjAttestationRepo.findByIdAttestation(idAttestation);
	}

	@GetMapping("/{idAlfresco}")
	public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);

		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		String fileName = pjAttestationRepo.findByIdAlfresco(id_alfresco);
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();

			IOUtils.copy(input, output);

			output.flush();

		} catch (IOException e) {
			// log it
		}
	}

	@PostMapping(path = "/multiplefile")
	public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id)
			throws IOException {
		Session session = conf();
		Folder root = session.getRootFolder();
		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			PjAttestation pj = new PjAttestation();
			pj.setIdAttestation(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setDateFile(new Date());
			PjAttestation pjUpdate = pjAttestationRepo.save(pj);
			Map<String, Object> properties2 = new HashMap<String, Object>();
			properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties2.put(PropertyIds.NAME, "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("AD_attestation")) {
					ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					org.apache.chemistry.opencmis.client.api.Document newDoc = ((Folder) child)
							.createDocument(properties2, contentStream, VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());

					pjAttestationRepo.save(pjUpdate);
				}
			}
		}

		return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
	}

}
