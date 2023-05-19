package com.pj.alfresco.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

import com.pj.alfresco.Models.PjTraitementReclamations;
import com.pj.alfresco.Repositories.PjTraitementReclamationsRepo;

@RestController
@RequestMapping("/PjTraitementReclamations")
public class PjTraitementReclamationsController {
	@Autowired
	PjTraitementReclamationsRepo pjTraitementReqRepo;

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

	@GetMapping({ "/AllpjsTraitementRec/{idReclamation}" })
	public List<PjTraitementReclamations> AllPjTraitement(@PathVariable long idReclamation) {
		Session session = conf();
		List<PjTraitementReclamations> a = new ArrayList<>();
		List<PjTraitementReclamations> listePjs = this.pjTraitementReqRepo.findByIdReclamation(idReclamation);
		System.out.println(listePjs);
		for (int i = 0; i < listePjs.size(); i++) {
			Document hj = (Document) session.getObject(((PjTraitementReclamations) listePjs.get(i)).getIdAlfresco());
			PjTraitementReclamations objb = new PjTraitementReclamations();
			objb.setId(((PjTraitementReclamations) listePjs.get(i)).getId());
			objb.setName(((PjTraitementReclamations) listePjs.get(i)).getName());
			objb.setIdAlfresco(hj.getId());
			a.add(objb);
		}
		return a;
	}

	@GetMapping({ "/{idAlfresco}" })
	public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);
		InputStream input = null;
		OutputStream output = null;
		response.addHeader("Content-Disposition",
				"attachment; filename=" + this.pjTraitementReqRepo.findByIdPj(idAlfresco + ";1.0"));
		try {
			input = hj.getContentStream().getStream();
			ServletOutputStream servletOutputStream = response.getOutputStream();
			IOUtils.copy(input, (OutputStream) servletOutputStream);
			servletOutputStream.flush();
		} catch (IOException iOException) {
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
			PjTraitementReclamations pj = new PjTraitementReclamations();
			pj.setIdReclamation(id);
			pj.setName(file.getOriginalFilename());
			PjTraitementReclamations pjUpdate = (PjTraitementReclamations) this.pjTraitementReqRepo.save(pj);
			Map<String, Object> properties2 = new HashMap<>();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "" + pjUpdate.getId());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("ReclamationsTraitements")) {
					ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder) child).createDocument(properties2, (ContentStream) contentStreamImpl,
							VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.pjTraitementReqRepo.save(pjUpdate);
				}
			}
		}
		return new ResponseEntity("Successful", null, HttpStatus.OK);
	}
}
