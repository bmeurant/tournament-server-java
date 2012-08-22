package org.resthub.sample.tournament.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.resthub.sample.tournament.model.Participant;
import org.resthub.sample.tournament.repository.ParticipantRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.resthub.web.exception.InternalServerErrorException;
import org.resthub.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping(value = "/api/participant")
public class ParticipantController extends RepositoryBasedRestController<Participant, Long, ParticipantRepository> {

    @Inject
    @Named("participantRepository")
    @Override
    public void setRepository(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long getIdFromResource(Participant resource) {
        return resource.getId();
    }

    @Value("${photosPath}")
    private String photoPath;

    @RequestMapping(value = "{id}/photo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void storePhoto(@PathVariable("id") Long participantId, @RequestParam MultipartFile file) {

        Assert.notNull(participantId, "id cannot be null");

        Participant participant = this.repository.findOne(participantId);
        if (participant == null) {
            throw new NotFoundException();
        }

        // remove photo if already exists
        File oldFile = participant.getPictureFile();
        if (null != oldFile) {
            oldFile.delete();
        }

        String type = file.getContentType().split("/")[1];
        String fileNameToCreate = photoPath + "participant/" + participantId + "." + type;

        File newFile = new File(fileNameToCreate);
        try {
            FileUtils.writeByteArrayToFile(newFile, file.getBytes());
        } catch (IOException e) {
            logger.error("Cannot read upload file", e);
            throw new InternalServerErrorException("Cannot read upload file", e);
        }
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.GET)
    public void getPhoto(@PathVariable("id") Long participantId, HttpServletResponse resp) {

        getPicture(participantId, resp, false);
    }

    @RequestMapping(value = "{id}/min-photo", method = RequestMethod.GET)
    public void getMinPhoto(@PathVariable("id") Long participantId, HttpServletResponse resp) {

        getPicture(participantId, resp, true);
    }

    private void getPicture(Long participantId, HttpServletResponse resp, Boolean isMin) {
        Assert.notNull(participantId, "id cannot be null");

        Participant participant = this.repository.findOne(participantId);
        if (participant == null) {
            throw new NotFoundException();
        }

        File file = participant.getPictureFile();
        if (file == null) {
            throw new NotFoundException();
        }

        resp.reset();
        byte[] content;
        InputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(file);
            content = IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            logger.error("Cannot read file", e);
            throw new InternalServerErrorException("Cannot read file", e);
        }

        String fileName = file.getName();
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        resp.setContentType("image/" + type);
        resp.setHeader("Accept-Ranges", "bytes");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentLength(content.length);

        if (isMin == Boolean.TRUE) {
            resp.setHeader("Cache-Control", "public, max-age=3600");
        }

        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(in, resp.getOutputStream());
            resp.flushBuffer();
        } catch (IOException e) {
            throw new InternalServerErrorException("cannot read image file", e);
        }
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable("id") Long participantId) {
        Assert.notNull(participantId, "id cannot be null");

        Participant participant = this.repository.findOne(participantId);
        if (participant == null) {
            throw new NotFoundException();
        }

        // remove photo if already exists
        File oldFile = participant.getPictureFile();
        if (null != oldFile) {
            oldFile.delete();
        }
    }

}
