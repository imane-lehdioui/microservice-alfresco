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
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.alfresco.Models.PjPartagesUsers;
import com.pj.alfresco.Models.PjUsers;
import com.pj.alfresco.Repositories.PjPartagesUsersRepo;
import com.pj.alfresco.Repositories.PjUsersRepo;

@RestController
@RequestMapping("/PjUsers")
public class PjUsersController {
	@Autowired
	PjUsersRepo pjUsersRepo;

	@Autowired
	PjPartagesUsersRepo pjpartageUsersRepo;

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

	@GetMapping({ "/CreerDocUser/{idUser}" })
	public void DocByUser(@PathVariable long idUser) {
		Session session = conf();

		Folder root = session.getRootFolder();
		Map<String, Object> properties = new HashMap<>();
		properties.put("cmis:objectTypeId", "cmis:folder");
		properties.put("cmis:name", String.valueOf(idUser));
		for (CmisObject child : root.getChildren()) {
			if (child.getName().equals("Users"))
				((Folder) child).createFolder(properties);
		}
	}

	// method to create alfresco folder
	@GetMapping({ "/CreateFolder/{folderName}" })
	public Folder createFolder(@PathVariable String folderName) {
		Session session = conf();
		Folder root = session.getRootFolder();
		Map<String, Object> properties = new HashMap<>();
		properties.put("cmis:objectTypeId", "cmis:folder");
		properties.put("cmis:name", folderName);
		Folder newFolder =root.createFolder(properties);
		System.out.println("Folder, %s" + newFolder);
		return newFolder;
	}

	@GetMapping({ "/Allpjs/{idUser}" })
	public List<PjUsers> AllByUser(@PathVariable long idUser) {
		List<PjUsers> listePjs = this.pjUsersRepo.findByIdUser(idUser);
		return listePjs;
	}

	@GetMapping({ "/AllpjsPartages/{idUser}" })
	public Iterable<PjPartagesUsers> AllPjPartagesByUser(@PathVariable long idUser) {
		Iterable<PjPartagesUsers> listePjs = this.pjpartageUsersRepo.findByIdUser(idUser);
		return listePjs;
	}

	@GetMapping({ "/{idAlfresco}" })
	public void downloadPjByIdUser(@PathVariable String idAlfresco, HttpServletResponse response) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);
		InputStream input = null;
		OutputStream output = null;
		response.addHeader("Content-Disposition",
				"attachment; filename=" + this.pjUsersRepo.findByIdPj(idAlfresco + ";1.0"));
		try {
			input = hj.getContentStream().getStream();
			ServletOutputStream servletOutputStream = response.getOutputStream();
			IOUtils.copy(input, (OutputStream) servletOutputStream);
			servletOutputStream.flush();
		} catch (IOException iOException) {
			iOException.getStackTrace();
		}
	}

	@GetMapping({ "partage/{idAlfresco}" })
	public void downloadPartagePjByIdUser(@PathVariable String idAlfresco, HttpServletResponse response) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);
		InputStream input = null;
		OutputStream output = null;
		response.addHeader("Content-Disposition",
				"attachment; filename=" + this.pjpartageUsersRepo.findByIdPj(idAlfresco + ";1.0"));
		try {
			input = hj.getContentStream().getStream();
			ServletOutputStream servletOutputStream = response.getOutputStream();
			IOUtils.copy(input, (OutputStream) servletOutputStream);
			servletOutputStream.flush();
		} catch (IOException iOException) {
			iOException.getStackTrace();
		}
	}

	@DeleteMapping({ "/{id}" })
	public Optional<PjUsers> delete(@PathVariable long id) {
		Session session = conf();
		Optional<PjUsers> r = this.pjUsersRepo.findById(Long.valueOf(id));
		String AlfId = ((PjUsers) r.get()).getIdAlfresco();
		Document hj = (Document) session.getObject(AlfId.substring(0, AlfId.length() - 4));
		hj.delete();
		this.pjUsersRepo.deleteById(Long.valueOf(id));
		return r;
	}

	@DeleteMapping({ "deletePartage/{id}" })
	public Optional<PjPartagesUsers> deletePartage(@PathVariable long id) {
		Session session = conf();
		Optional<PjPartagesUsers> r = this.pjpartageUsersRepo.findById(Long.valueOf(id));
		String AlfId = ((PjPartagesUsers) r.get()).getIdAlfresco();
		Document hj = (Document) session.getObject(AlfId.substring(0, AlfId.length() - 4));
		hj.delete();
		this.pjpartageUsersRepo.deleteById(Long.valueOf(id));
		return r;
	}

	@RequestMapping(path = { "/transfereDoc" }, method = { RequestMethod.POST })
	public void transfereDoc(@RequestParam("id") String idAlfresco, @RequestParam("nameDoc") String nameDoc,
			@RequestParam("iduser") long iduser, @RequestParam("source") long iduserSource) {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);
		PjPartagesUsers pj = new PjPartagesUsers();
		pj.setIdUser(iduser);
		pj.setName(nameDoc);
		pj.setIdUserSourcePartage(iduserSource);
		pj.setDateFile(new Date());
		PjPartagesUsers pjUpdate = (PjPartagesUsers) this.pjpartageUsersRepo.save(pj);
		String name = hj.getName();
		Folder root = session.getRootFolder();
		for (CmisObject child : root.getChildren()) {
			if (child.getName().equals("Users")) {
				Document hjCopy = hj.copy((ObjectId) child);
				hjCopy.rename("" + pjUpdate.getId() + "partage");
				for (CmisObject c : ((Folder) child).getChildren()) {
					if (c.getName().equals(String.valueOf(iduser))) {
						Document hjCopy1 = hjCopy.copy((ObjectId) c);
						hjCopy.delete();
						pjUpdate.setIdAlfresco(hjCopy1.getId());
						this.pjpartageUsersRepo.save(pjUpdate);
					}
				}
			}
		}
	}

	@RequestMapping(path = { "/multiplefileupload" }, method = { RequestMethod.POST })
	public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id)
			throws IOException {
		Session session = conf();
		Folder root = session.getRootFolder();
		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			PjUsers pj = new PjUsers();
			pj.setIdUser(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setDateFile(new Date());
			PjUsers pjUpdate = (PjUsers) this.pjUsersRepo.save(pj);
			System.out.println("File inserted !");

			Map<String, Object> properties2 = new HashMap<>();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("Users"))
					for (CmisObject c : ((Folder) child).getChildren()) {
						if (c.getName().equals(String.valueOf(id))) {
							ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
									BigInteger.valueOf(file.getSize()), file.getContentType(),
									new FileInputStream(tempFile));
							Document newDoc = ((Folder) c).createDocument(properties2,
									(ContentStream) contentStreamImpl, VersioningState.MAJOR);
							pjUpdate.setIdAlfresco(newDoc.getId());
							this.pjUsersRepo.save(pjUpdate);
						}
					}
			}
		}
		return 1L;
	}
}
