//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Controllers;


import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.pj.alfresco.Models.PjMagasin;
import com.pj.alfresco.Repositories.PjMagasinRepo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/PjMagasin"})
public class PjMagasinController {
	@Autowired
	PjMagasinRepo pjMagasinRepo;
	@Value("${alfresco.login}")
	private String login;
	@Value("${alfresco.password}")
	private String password;
	@Value("${alfresco.url}")
	private String url;

	public PjMagasinController() {
	}

	public Session conf() {
		SessionFactoryImpl sessionFactoryImpl = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap();
		parameter.put("org.apache.chemistry.opencmis.user", this.login);
		parameter.put("org.apache.chemistry.opencmis.password", this.password);
		parameter.put("org.apache.chemistry.opencmis.binding.atompub.url", this.url);
		parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.ATOMPUB.value());
		Session session = ((Repository)sessionFactoryImpl.getRepositories(parameter).get(0)).createSession();
		return session;
	}

	@GetMapping({"/Allpjs/{idMagasin}"})
	public List<PjMagasin> AllSeverite(@PathVariable long idMagasin) {
		Session session = this.conf();
		return this.pjMagasinRepo.findByIdMagasinOrderByIdDesc(idMagasin);
	}

	@GetMapping({"/allFiles/{idMagasin}/{sModule}"})
	public List<PjMagasin> allDocsByIdAndSousModule(@PathVariable long idMagasin, @PathVariable String sModule) {
		Session session = this.conf();
		return this.pjMagasinRepo.findByIdMagasinAndSousModuleOrderByIdDesc(idMagasin, sModule);
	}

	@GetMapping({"/{idAlfresco}"})
	public void rec(@PathVariable String idAlfresco, HttpServletResponse response) throws UnsupportedEncodingException {
		Session session = this.conf();
		Document hj = (Document)session.getObject(idAlfresco);
		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition",
				"attachment; filename=" + URLEncoder.encode(this.pjMagasinRepo.findByIdAlfresco(id_alfresco).getName(), "UTF-8"));
//        response.addHeader("Content-Disposition", "attachment; filename=" + this.convnertionMarcheRepo.findByIdAlfresco(id_alfresco).getName());
		System.out.println("aa"+URLEncoder.encode(this.pjMagasinRepo.findByIdAlfresco(id_alfresco).getName(), "UTF-8"));

		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
		} catch (IOException var9) {
			var9.getStackTrace();
		}

	}

	@RequestMapping(
			path = {"/multiplefileupload"},
			method = {RequestMethod.POST}
	)
	public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id, @RequestParam("sModule") String sModule) throws IOException {
		Session session = this.conf();
		Folder root = session.getRootFolder();
		MultipartFile[] var7 = files;
		int var8 = files.length;

		for(int var9 = 0; var9 < var8; ++var9) {
			MultipartFile file = var7[var9];
			File tempFile = File.createTempFile(file.getOriginalFilename(), (String)null);
			file.transferTo(tempFile);
			PjMagasin pj = new PjMagasin();
			pj.setIdMagasin(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setSousModule(sModule);
			pj.setDateFile(new Date());
			PjMagasin pjUpdate = (PjMagasin) this.pjMagasinRepo.save(pj);
			System.out.println("File inserted !");
			Map<String, Object> properties2 = new HashMap();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			Iterator var15 = root.getChildren().iterator();

			while(var15.hasNext()) {
				CmisObject child = (CmisObject)var15.next();
				if (child.getName().equals("Ao")) {
					ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(), BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder)child).createDocument(properties2, contentStream, VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.pjMagasinRepo.save(pjUpdate);
				}
			}
		}

		return 1L;
	}
}
