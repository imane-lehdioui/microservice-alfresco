//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.ConvnetionMarche;
import com.pj.alfresco.Repositories.ConvnertionMarcheRepo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/MarcheConvention"})
public class ConventionMarchesController {
    @Autowired
    ConvnertionMarcheRepo convnertionMarcheRepo;
    @Value("${alfresco.login}")
    private String login;
    @Value("${alfresco.password}")
    private String password;
    @Value("${alfresco.url}")
    private String url;

    public ConventionMarchesController() {
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

    @GetMapping({"/Allpjs/{idAo}"})
    public List<ConvnetionMarche> AllSeverite(@PathVariable long idAo) {
        Session session = this.conf();
        return this.convnertionMarcheRepo.findByIdConventionOrderByIdDesc(idAo);
    }

    @GetMapping({"/allFiles/{idAo}/{sModule}"})
    public List<ConvnetionMarche> allDocsByIdAndSousModule(@PathVariable long idAo, @PathVariable String sModule) {
        Session session = this.conf();
        return this.convnertionMarcheRepo.findByIdConventionAndSousModuleOrderByIdDesc(idAo, sModule);
    }

    @GetMapping({"/{idAlfresco}"})
    public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
        Session session = this.conf();
        Document hj = (Document)session.getObject(idAlfresco);
        InputStream input = null;
        OutputStream output = null;
        String id_alfresco = idAlfresco + ";1.0";
        response.addHeader("Content-Disposition", "attachment; filename=" + this.convnertionMarcheRepo.findByIdAlfresco(id_alfresco).getName());

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
            ConvnetionMarche pj = new ConvnetionMarche();
            pj.setIdConvention(id);
            pj.setName(file.getOriginalFilename());
            pj.setfSize(file.getSize());
            pj.setSousModule(sModule);
            pj.setDateFile(new Date());
            ConvnetionMarche pjUpdate = (ConvnetionMarche)this.convnertionMarcheRepo.save(pj);
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
                    this.convnertionMarcheRepo.save(pjUpdate);
                }
            }
        }

        return 1L;
    }
}
