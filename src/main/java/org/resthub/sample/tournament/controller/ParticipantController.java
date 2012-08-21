package org.resthub.sample.tournament.controller;

import org.resthub.sample.tournament.model.Participant;
import org.resthub.sample.tournament.repository.ParticipantRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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

    /**
     * resthub uses 0-indexed pages, but I want my service interface to use 1-indexed pages
     * @see org.resthub.web.controller.RestController#findPaginated
     */
    @Override
    public Page<Participant> findPaginated(@RequestParam(value = "page", required = true) Integer pageId,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return super.findAll( pageId - 1, size);
    }

}
