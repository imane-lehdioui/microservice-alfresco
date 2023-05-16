//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.PJCourrie;
import com.pj.alfresco.Repositories.PJCourriersRepository;
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
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/PJCourrierController"})
public class PJCourrierController {
    private static final Logger logger = LoggerFactory.getLogger(PjCourriersEntrantsController.class);
    @Autowired
    PJCourriersRepository pjCourriersEntrant;

    @Autowired
    PJCourriersRepository pjCourriersRepository;

    @Value("${alfresco.login}")
    private String login;
    @Value("${alfresco.password}")
    private String password;
    @Value("${alfresco.url}")
    private String url;

    public PJCourrierController() {
    }

    public Session conf() {
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap();
        parameter.put("org.apache.chemistry.opencmis.user", this.login);
        parameter.put("org.apache.chemistry.opencmis.password", this.password);
        parameter.put("org.apache.chemistry.opencmis.binding.atompub.url", this.url);
        parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.ATOMPUB.value());
        Session session = ((Repository)factory.getRepositories(parameter).get(0)).createSession();
        return session;
    }

    @DeleteMapping({"/{id}"})
    public Optional<PJCourrie> delete(@PathVariable long id) {
        Session session = this.conf();
        Optional<PJCourrie> r = this.pjCourriersEntrant.findById(id);
        String AlfId = ((PJCourrie)r.get()).getIdAlfresco();
        System.out.println(AlfId.substring(0, AlfId.length() - 4));
        Document hj = (Document)session.getObject(AlfId.substring(0, AlfId.length() - 4));
        hj.delete();
        this.pjCourriersEntrant.deleteById(id);
        logger.info("CourrierEntrant file deleted");
        return r;
    }

    @DeleteMapping({"/ByIdCourriersEntrants/{id}"})
    public void deleteByIdCourrierEntrant(@PathVariable long id) {
        Session session = this.conf();
        List<PJCourrie> pjList = this.pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(id);
        Iterator var5 = pjList.iterator();

        while(var5.hasNext()) {
            PJCourrie f = (PJCourrie)var5.next();
            String idPJ = f.getIdAlfresco();
            Document doc = (Document)session.getObject(idPJ.substring(0, idPJ.length() - 4));
            if (doc != null) {
                doc.delete();
                this.pjCourriersEntrant.deleteById(f.getId());
            }
        }

    }

    @GetMapping({"/Allpjs/{idCourrierEntrant}"})
    public List<PJCourrie> AllSeverite(@PathVariable long idCourrierEntrant) {
        System.out.println("in traitement");
        Session session = this.conf();
        logger.info("Get all files courrierEntrant");
        return this.pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(idCourrierEntrant);
    }

    @GetMapping({"/{idAlfresco}"})
    public void alfGed(@PathVariable String idAlfresco, HttpServletResponse response) {
        System.out.println(idAlfresco);
        Session session = this.conf();
        Document hj = (Document)session.getObject(idAlfresco);
        InputStream input = null;
        OutputStream output = null;
        String id_alfresco = idAlfresco + ";1.0";
        response.addHeader("Content-Disposition", "attachment; filename=" + this.pjCourriersRepository.findByIdAlfresco(id_alfresco).getName());

        try {
            input = hj.getContentStream().getStream();
            output = response.getOutputStream();
            IOUtils.copy(input, output);
            output.flush();
            logger.info("File returned");
        } catch (IOException var9) {
            logger.info("File not found!!");
        }

    }

    @RequestMapping(
            path = {"/multiplefile"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id, @RequestParam("user") String user, @RequestParam("type") String sModule) throws IOException {
        System.out.println("ID output" + id);
        Session session = this.conf();
        Folder root = session.getRootFolder();
        logger.info("Configuration session  :: courrier entrant");
        MultipartFile[] var8 = files;
        int var9 = files.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            MultipartFile file = var8[var10];
            File tempFile = File.createTempFile(file.getOriginalFilename(), (String)null);
            file.transferTo(tempFile);
            logger.info("File temp created  :: courrier entrant");
            PJCourrie pj = new PJCourrie();
            pj.setIdCourrierEntrant(id);
            pj.setName(file.getOriginalFilename());
            pj.setfSize(file.getSize());
            pj.setDateFile(new Date());
            pj.setUserName(user);
            pj.setMotif(sModule);
            PJCourrie pjUpdate = (PJCourrie)this.pjCourriersEntrant.save(pj);
            logger.info("Save files in DB  :: courrier entrant");
            Map<String, Object> properties2 = new HashMap();
            properties2.put("cmis:objectTypeId", "cmis:document");
            properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
            Iterator var16 = root.getChildren().iterator();

            while(var16.hasNext()) {
                CmisObject child = (CmisObject)var16.next();
                if (child.getName().equals("Courrier")) {
                    ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(), BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
                    Document newDoc = ((Folder)child).createDocument(properties2, contentStream, VersioningState.MAJOR);
                    pjUpdate.setIdAlfresco(newDoc.getId());
                    this.pjCourriersEntrant.save(pjUpdate);
                    logger.info("Files inserted :: courrier entrant");
                }
            }
        }

        return new ResponseEntity("Successful", (MultiValueMap)null, HttpStatus.OK);
    }
}
