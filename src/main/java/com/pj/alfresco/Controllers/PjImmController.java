//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.ImmPJ;
import com.pj.alfresco.Models.PjImm;
import com.pj.alfresco.Repositories.PJImmRepository;
import com.pj.alfresco.Repositories.PjImmRepo;
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
@RequestMapping({"/PjImm"})
public class PjImmController {
    @Autowired
    PJImmRepository pjImmRepository;
    @Autowired
    PjImmRepo pjImmRepo;
    @Value("${alfresco.login}")
    private String login;
    @Value("${alfresco.password}")
    private String password;
    @Value("${alfresco.url}")
    private String url;

    public PjImmController() {
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
    public List<ImmPJ> AllSeverite(@PathVariable long idAo) {
        Session session = this.conf();
        return this.pjImmRepository.findByIdImmOrderByIdDesc(idAo);
    }

    @GetMapping({"/delete/{idAo}"})
    public String delete(@PathVariable long idAo) {
        this.pjImmRepository.delete(idAo);
        return "delete";
    }

    @GetMapping({"/allFiles/{idAo}/{sModule}"})
    public List<ImmPJ> allDocsByIdAndSousModule(@PathVariable long idIm, @PathVariable String sModule) {
        Session session = this.conf();
        return this.pjImmRepository.findByIdImmAndSousModuleOrderByIdDesc(idIm, sModule);
    }

    @GetMapping({"/{idAlfresco}"})
    public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
        System.out.println(idAlfresco);
        Session session = this.conf();
        Document hj = (Document)session.getObject(idAlfresco);
        InputStream input = null;
        OutputStream output = null;
        String id_alfresco = idAlfresco + ";1.0";
        response.addHeader("Content-Disposition", "attachment; filename=" + this.pjImmRepository.findByIdAlfresco(id_alfresco).getName());

        try {
            input = hj.getContentStream().getStream();
            output = response.getOutputStream();
            IOUtils.copy(input, output);
            output.flush();
        } catch (IOException var9) {
            var9.getStackTrace();
        }

    }

    @GetMapping({"/Alltype"})
    public Iterable<PjImm> AllByUser() {
        return this.pjImmRepo.findAll();
    }

    @RequestMapping(
            path = {"/multiplefileupload"},
            method = {RequestMethod.PUT}
    )
    public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id, @RequestParam("type") long idtypePjSent, @RequestParam("sModule") String sModule, @RequestParam("idAlfresco") String idAlfresco) throws IOException {
        Session session = this.conf();
        Folder root = session.getRootFolder();
        Optional<PjImm> pjImm = this.pjImmRepo.findById(idtypePjSent);
        if (idAlfresco.equals("undefined")) {
            MultipartFile[] var11 = files;
            int var12 = files.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                MultipartFile file = var11[var13];
                File tempFile = File.createTempFile(file.getOriginalFilename(), (String)null);
                file.transferTo(tempFile);
                ImmPJ pj = new ImmPJ();
                pj.setIdImm(id);
                pj.setName(file.getOriginalFilename());
                pj.setfSize(file.getSize());
                pj.setDateFile(new Date());
                if (pjImm.isPresent()) {
                    pj.setType((PjImm)pjImm.get());
                }

                ImmPJ pjUpdate = (ImmPJ)this.pjImmRepository.save(pj);
                Map<String, Object> properties2 = new HashMap();
                properties2.put("cmis:objectTypeId", "cmis:document");
                properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
                Iterator var19 = root.getChildren().iterator();

                while(var19.hasNext()) {
                    CmisObject child = (CmisObject)var19.next();
                    if (child.getName().equals("IMM")) {
                        ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(), BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
                        Document newDoc = ((Folder)child).createDocument(properties2, contentStream, VersioningState.MAJOR);
                        pjUpdate.setIdAlfresco(newDoc.getId());
                        this.pjImmRepository.save(pjUpdate);
                    }
                }
            }
        }

        return 1L;
    }

    @RequestMapping(
            path = {"/multiplefileupload"},
            method = {RequestMethod.POST}
    )
    public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id, @RequestParam("type") long idtypePjSent, @RequestParam("sModule") String sModule) throws IOException {
        Session session = this.conf();
        Folder root = session.getRootFolder();
        Optional<PjImm> pjImm = this.pjImmRepo.findById(idtypePjSent);
        MultipartFile[] var10 = files;
        int var11 = files.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            MultipartFile file = var10[var12];
            File tempFile = File.createTempFile(file.getOriginalFilename(), (String)null);
            file.transferTo(tempFile);
            ImmPJ pj = new ImmPJ();
            pj.setIdImm(id);
            pj.setName(file.getOriginalFilename());
            pj.setfSize(file.getSize());
            pj.setDateFile(new Date());
            if (pjImm.isPresent()) {
                pj.setType((PjImm)pjImm.get());
            }

            ImmPJ pjUpdate = (ImmPJ)this.pjImmRepository.save(pj);
            Map<String, Object> properties2 = new HashMap();
            properties2.put("cmis:objectTypeId", "cmis:document");
            properties2.put("cmis:name", "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
            Iterator var18 = root.getChildren().iterator();

            while(var18.hasNext()) {
                CmisObject child = (CmisObject)var18.next();
                if (child.getName().equals("IMM")) {
                    ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(), BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
                    Document newDoc = ((Folder)child).createDocument(properties2, contentStream, VersioningState.MAJOR);
                    pjUpdate.setIdAlfresco(newDoc.getId());
                    this.pjImmRepository.save(pjUpdate);
                }
            }
        }

        return 1L;
    }
}
