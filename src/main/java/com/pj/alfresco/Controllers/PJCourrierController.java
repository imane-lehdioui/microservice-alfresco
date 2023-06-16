package com.pj.alfresco.Controllers;

import com.pj.alfresco.Models.PJCourrie;
import com.pj.alfresco.Repositories.PJCourriersRepository;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
@RestController
@RequestMapping("/PJCourrierController")
public class PJCourrierController {
  private static final Logger logger  = LoggerFactory.getLogger(PjCourriersEntrantsController.class);

  @Autowired
  PJCourriersRepository pjCourriersEntrant;

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

  @GetMapping(path = "/IsTaiter/{id}")
  public ResponseEntity<Boolean> isTraiter(@PathVariable Long id){
    Boolean result = pjCourriersEntrant.existsByIdCourrierEntrant(id);
    return ResponseEntity.ok().body(result);
  }
  @DeleteMapping("/{id}")
  public Optional<PJCourrie> delete(@PathVariable long id) {

    Session session = conf();
    Optional<PJCourrie> r = pjCourriersEntrant.findById(id);
    if(r.get()!=null && r.get().getIdAlfresco() != null){
      String AlfId = r.get().getIdAlfresco();
      System.out.println(AlfId.substring(0, AlfId.length() - 4));
      Document hj = (Document) session.getObject(AlfId.substring(0, AlfId.length() - 4));
      // hj.deleteContentStream();
      hj.delete();
    }
    pjCourriersEntrant.deleteById(id);
    logger.info("CourrierEntrant file deleted");
    return r;
  }

  @DeleteMapping("/ByIdCourriersEntrants/{id}")
  public void deleteByIdCourrierEntrant(@PathVariable long id) {
    Session session = conf();
    List<PJCourrie> pjList = pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(id);
    for (PJCourrie f : pjList) {
      String idPJ = f.getIdAlfresco();
      Document doc = (Document) session.getObject(idPJ.substring(0, idPJ.length() - 4));
      if (doc != null) {
        doc.delete();
        pjCourriersEntrant.deleteById(f.getId());
      }
    }

  }

  @GetMapping("/Allpjs/{idCourrierEntrant}")
  public List<PJCourrie> AllSeverite(@PathVariable long idCourrierEntrant) {
    System.out.println("in traitement");
    Session session = conf();
    logger.info("Get all files courrierEntrant");
    return pjCourriersEntrant.findByIdCourrierEntrantOrderByIdDesc(idCourrierEntrant);
  }

  @GetMapping("/{idAlfresco}")
  public void alfGed(@PathVariable String idAlfresco, HttpServletResponse response) {
    System.out.println(idAlfresco);
    Session session = conf();
    Document hj = (Document) session.getObject(idAlfresco);

    InputStream input = null;
    OutputStream output = null;
    String id_alfresco = idAlfresco + ";1.0";
    response.addHeader("Content-Disposition",
      "attachment; filename=" + pjCourriersEntrant.findByIdAlfresco(id_alfresco).getName());
    try {
      input = hj.getContentStream().getStream();
      output = response.getOutputStream();

      IOUtils.copy(input, output);

      output.flush();
      logger.info("File returned");
    } catch (IOException e) {
      logger.info("File not found!!");
    }
  }

  @RequestMapping(path = "/multiplefile", method = RequestMethod.POST)
  public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile[] files, @RequestParam("id") long id,
                                            @RequestParam("user") String user, @RequestParam("type") String sModule)
    throws IOException {

    System.out.println("ID output" + id);
    Session session = conf();
    Folder root = session.getRootFolder();

    logger.info("Configuration session  :: courrier entrant");
    if(files.length == 0){
      PJCourrie pj = new PJCourrie();
      pj.setIdCourrierEntrant(id);
      pj.setDateFile(new Date());
      pj.setUserName(user);
      pj.setMotif(sModule);
      pj.setDateTraitement(LocalDate.now());
      PJCourrie pjUpdate = pjCourriersEntrant.save(pj);
      System.out.println("pjUpdate= "+ pjUpdate);
    }else {
      for (MultipartFile file : files) {
        File tempFile = File.createTempFile(file.getOriginalFilename(), null);
        file.transferTo(tempFile);
        logger.info("File temp created  :: courrier entrant");
        PJCourrie pj = new PJCourrie();
        pj.setIdCourrierEntrant(id);
        pj.setName(file.getOriginalFilename());
        pj.setfSize(file.getSize());
        pj.setDateFile(new Date());
        pj.setUserName(user);
        pj.setMotif(sModule);
        PJCourrie pjUpdate = pjCourriersEntrant.save(pj);

        logger.info("Save files in DB  :: courrier entrant");

        Map<String, Object> properties2 = new HashMap<String, Object>();
        properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        //properties2.put(PropertyIds.NAME, "" + pjUpdate.getId()+"-"+ pjUpdate.getName());
        properties2.put(PropertyIds.NAME, "[" + pjUpdate.getId() + "] -" + id + "-" + pjUpdate.getName());
        for (CmisObject child : root.getChildren()) {
          if (child.getName().equals("Courrier")) {

            ContentStream contentStream = new ContentStreamImpl("" + pjUpdate.getId(),
                    BigInteger.valueOf(file.getSize()), file.getContentType(), new FileInputStream(tempFile));
            org.apache.chemistry.opencmis.client.api.Document newDoc = ((Folder) child)
                    .createDocument(properties2, contentStream, VersioningState.MAJOR);
            pjUpdate.setIdAlfresco(newDoc.getId());

            pjCourriersEntrant.save(pjUpdate);
            logger.info("Files inserted :: courrier entrant");
          }
        }
      }
    }


    return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
  }

}
