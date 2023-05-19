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
import java.util.Optional;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.alfresco.Models.PjReclamations;
import com.pj.alfresco.Repositories.PjReclamationRepo;

@RestController
@RequestMapping("/PjReclamations")
public class PjReclamationsController {
	@Autowired
	PjReclamationRepo pjRecRepo;

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

	@DeleteMapping({ "/{id}" })
	public Optional<PjReclamations> delete(@PathVariable long id) {
		SessionFactoryImpl sessionFactoryImpl = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<>();
		parameter.put("org.apache.chemistry.opencmis.user", "admin");
		parameter.put("org.apache.chemistry.opencmis.password", "admin");
		parameter.put("org.apache.chemistry.opencmis.binding.atompub.url",
				"http://127.0.0.1:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
		parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.ATOMPUB.value());
		Session session = ((Repository) sessionFactoryImpl.getRepositories(parameter).get(0)).createSession();
		Optional<PjReclamations> r = this.pjRecRepo.findById(Long.valueOf(id));
		String AlfId = ((PjReclamations) r.get()).getIdAlfresco();
		System.out.println(AlfId.substring(0, AlfId.length() - 4));
		Document hj = (Document) session.getObject(AlfId.substring(0, AlfId.length() - 4));
		hj.delete();
		this.pjRecRepo.deleteById(Long.valueOf(id));
		return r;
	}

	@DeleteMapping({ "/ByIdReclamation/{id}" })
	public void deleteByIdReclamation(@PathVariable long id) {
		this.pjRecRepo.deleteByIdReclamation(id);
	}

	@GetMapping({ "/Allpjs/{idReclamation}" })
	public List<PjReclamations> AllSeverite(@PathVariable long idReclamation) {
		Session session = conf();
		List<PjReclamations> a = new ArrayList<>();
		List<PjReclamations> listePjs = this.pjRecRepo.findByIdReclamation(idReclamation);
		for (int i = 0; i < listePjs.size(); i++) {
			Document hj = (Document) session.getObject(((PjReclamations) listePjs.get(i)).getIdAlfresco());
			PjReclamations objb = new PjReclamations();
			objb.setId(((PjReclamations) listePjs.get(i)).getId());
			objb.setName(((PjReclamations) listePjs.get(i)).getName());
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
				"attachment; filename=" + this.pjRecRepo.findByIdPj(idAlfresco + ";1.0"));
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
			PjReclamations pj = new PjReclamations();
			pj.setIdReclamation(id);
			pj.setName(file.getOriginalFilename());
			PjReclamations pjUpdate = (PjReclamations) this.pjRecRepo.save(pj);
			Map<String, Object> properties2 = new HashMap<>();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "" + pjUpdate.getId());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("Reclamations")) {
					ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder) child).createDocument(properties2, (ContentStream) contentStreamImpl,
							VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.pjRecRepo.save(pjUpdate);
				}
			}
		}
		return new ResponseEntity("Successful", null, HttpStatus.OK);
	}
}
