package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.ConvnetionMarche;
import com.pj.alfresco.Repositories.ConvnertionMarcheRepo;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({ "/MarcheConvention" })
public class ConventionMarchesController {


	@Autowired
	ConvnertionMarcheRepo convnertionMarcheRepo;

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

	@GetMapping({ "/Allpjs/{idAo}" })
	public List<ConvnetionMarche> AllSeverite(@PathVariable long idAo) {
		Session session = conf();
		/*
		 * List<PjAo> a = new ArrayList<>(); List<PjAo> listePjs =
		 * this.pjAoRepo.findByIdAo(idAo); for (int i = 0; i < listePjs.size(); i++) {
		 * Document hj = (Document) session.getObject(((PjAo)
		 * listePjs.get(i)).getIdAlfresco()); PjAo objb = new PjAo(); objb.setId(((PjAo)
		 * listePjs.get(i)).getId()); objb.setName(((PjAo) listePjs.get(i)).getName());
		 * objb.setType(((PjAo) listePjs.get(i)).getType());
		 * objb.setIdAlfresco(hj.getId()); a.add(objb); }
		 */
		return convnertionMarcheRepo.findByIdConventionOrderByIdDesc(idAo);
	}

	@GetMapping({ "/allFiles/{idAo}/{sModule}" })
	public List<ConvnetionMarche> allDocsByIdAndSousModule(@PathVariable long idAo, @PathVariable String sModule) {
		Session session = conf();
		return convnertionMarcheRepo.findByIdConventionAndSousModuleOrderByIdDesc(idAo, sModule);
	}

	@GetMapping({ "/{idAlfresco}" })
	public void rec(@PathVariable String idAlfresco, HttpServletResponse response) throws UnsupportedEncodingException {
		Session session = conf();
		Document hj = (Document) session.getObject(idAlfresco);

		InputStream input = null;
		OutputStream output = null;
		String id_alfresco = idAlfresco + ";1.0";
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition",
				"attachment; filename=" + URLEncoder.encode(convnertionMarcheRepo.findByIdAlfresco(id_alfresco).getName(), "UTF-8"));
//		response.addHeader("Content-Disposition",
//				"attachment; filename=" + convnertionMarcheRepo.findByIdAlfresco(id_alfresco).getName());
		try {
			input = hj.getContentStream().getStream();
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
		} catch (IOException iOException) {
			iOException.getStackTrace();
		}
	}



	@RequestMapping(path = { "/multiplefileupload" }, method = { RequestMethod.POST })
	public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id,
							 @RequestParam("sModule") String sModule) throws IOException {
		Session session = conf();
		Folder root = session.getRootFolder();
		for (MultipartFile file : files) {
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			ConvnetionMarche pj = new ConvnetionMarche();
			pj.setIdConvention(id);
			pj.setName(file.getOriginalFilename());
			pj.setfSize(file.getSize());
			pj.setSousModule(sModule);
			pj.setDateFile(new Date());
			ConvnetionMarche pjUpdate = (ConvnetionMarche) this.convnertionMarcheRepo.save(pj);
			System.out.println("File inserted !");

			Map<String, Object> properties2 = new HashMap<>();
			properties2.put("cmis:objectTypeId", "cmis:document");
			properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
			for (CmisObject child : root.getChildren()) {
				if (child.getName().equals("Ao")) {

						/*	ContentStreamImpl contentStreamImpl = new ContentStreamImpl("" + pjUpdate.getId(),
									BigInteger.valueOf(file.getSize()), file.getContentType(),
									new FileInputStream(tempFile));
							Document newDoc = ((Folder) c).createDocument(properties2,
									(ContentStream) contentStreamImpl, VersioningState.MAJOR);*/
					ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
							BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
					Document newDoc = ((Folder) child)
							.createDocument(properties2, contentStream, VersioningState.MAJOR);
					pjUpdate.setIdAlfresco(newDoc.getId());
					pjUpdate.setIdAlfresco(newDoc.getId());
					this.convnertionMarcheRepo.save(pjUpdate);

				}
			}
		}
		return 1L;
	}
}

