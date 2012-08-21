package org.resthub.sample.tournament.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.sample.tournament.model.Participant;
import org.resthub.sample.tournament.repository.ParticipantRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
