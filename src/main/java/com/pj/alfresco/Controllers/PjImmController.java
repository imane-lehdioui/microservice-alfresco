package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.*;
import com.pj.alfresco.Repositories.PJImmRepository;
import com.pj.alfresco.Repositories.PjImmRepo;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping({ "/PjImm" })
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
    public List<ImmPJ> AllSeverite(@PathVariable long idAo) {
        Session session = conf();
        return pjImmRepository.findByIdImmOrderByIdDesc(idAo);
    }
  @GetMapping({ "/delete/{idAo}" })
  public String  delete(@PathVariable long idAo) {
     pjImmRepository.delete(idAo);
     return "delete";
  }


    @GetMapping({ "/allFiles/{idAo}/{sModule}" })
    public List<ImmPJ> allDocsByIdAndSousModule(@PathVariable long idIm, @PathVariable String sModule) {
        Session session = conf();
        return pjImmRepository.findByIdImmAndSousModuleOrderByIdDesc(idIm, sModule);
    }

    @GetMapping({ "/{idAlfresco}" })
    public void rec(@PathVariable String idAlfresco, HttpServletResponse response) {
        System.out.println(idAlfresco);
        Session session = conf();
        Document hj = (Document) session.getObject(idAlfresco);

        InputStream input = null;
        OutputStream output = null;
        String id_alfresco = idAlfresco + ";1.0";
        response.addHeader("Content-Disposition",
                "attachment; filename=" + pjImmRepository.findByIdAlfresco(id_alfresco).getName());
        try {
            input = hj.getContentStream().getStream();
            output = response.getOutputStream();
            IOUtils.copy(input, output);
            output.flush();
        } catch (IOException iOException) {
            iOException.getStackTrace();
        }
    }

    @GetMapping({ "/Alltype" })
    public Iterable<PjImm> AllByUser() {
        return this.pjImmRepo.findAll();
    }

    @RequestMapping(path = { "/multiplefileupload" }, method = { RequestMethod.PUT })
    public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id,
                            @RequestParam("type") long idtypePjSent, @RequestParam("sModule") String sModule, @RequestParam("idAlfresco") String idAlfresco) throws IOException {
        Session session = conf();
        Folder root = session.getRootFolder();
        Optional<PjImm> pjImm=pjImmRepo.findById(idtypePjSent);
      if(idAlfresco.equals("undefined")){
        for (MultipartFile file : files) {
          File tempFile = File.createTempFile(file.getOriginalFilename(), null);
          file.transferTo(tempFile);
          ImmPJ pj = new ImmPJ();
          pj.setIdImm(id);
          pj.setName(file.getOriginalFilename());
          pj.setfSize(file.getSize());
          pj.setDateFile(new Date());
          if(pjImm.isPresent()) {
            pj.setType(pjImm.get());
          }
          ImmPJ pjUpdate = pjImmRepository.save(pj);

          Map<String, Object> properties2 = new HashMap<String, Object>();
          properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
          properties2.put(PropertyIds.NAME, "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
          for (CmisObject child : root.getChildren()) {
            if (child.getName().equals("IMM")) {
              ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
                BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
              org.apache.chemistry.opencmis.client.api.Document newDoc = ((Folder) child)
                .createDocument(properties2, contentStream, VersioningState.MAJOR);
              pjUpdate.setIdAlfresco(newDoc.getId());
              pjImmRepository.save(pjUpdate);
            }
          }
      }


        }
        return 1L;
    }

  @RequestMapping(path = { "/multiplefileupload" }, method = { RequestMethod.POST })
  public long processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id,
                          @RequestParam("type") long idtypePjSent, @RequestParam("sModule") String sModule) throws IOException {
    Session session = conf();
    Folder root = session.getRootFolder();
    Optional<PjImm> pjImm=pjImmRepo.findById(idtypePjSent);
      for (MultipartFile file : files) {
        File tempFile = File.createTempFile(file.getOriginalFilename(), null);
        file.transferTo(tempFile);
        ImmPJ pj = new ImmPJ();
        pj.setIdImm(id);
        pj.setName(file.getOriginalFilename());
        pj.setfSize(file.getSize());
        pj.setDateFile(new Date());
        if(pjImm.isPresent()) {
          pj.setType(pjImm.get());
        }
        ImmPJ pjUpdate = pjImmRepository.save(pj);

        Map<String, Object> properties2 = new HashMap<String, Object>();
        properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties2.put(PropertyIds.NAME, "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
        for (CmisObject child : root.getChildren()) {
          if (child.getName().equals("IMM")) {
            ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
              BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
            org.apache.chemistry.opencmis.client.api.Document newDoc = ((Folder) child)
              .createDocument(properties2, contentStream, VersioningState.MAJOR);
            pjUpdate.setIdAlfresco(newDoc.getId());
            pjImmRepository.save(pjUpdate);
          }
        }


    }
    return 1L;
  }
}

