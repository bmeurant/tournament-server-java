package org.resthub.sample.tournament;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.common.util.PostInitialize;

import org.resthub.sample.tournament.model.Participant;
import org.resthub.sample.tournament.repository.ParticipantRepository;

@Named("participantInitializer")
public class ParticipantInitializer {

    @Inject
    @Named("participantRepository")
    private ParticipantRepository participantRepository;

    @PostInitialize
    public void init() {
        participantRepository.save(new Participant("Baptiste", "Meurant", "test@test.fr"));
        participantRepository.save(new Participant("Loïc", "Frering", "test@test.fr"));
        participantRepository.save(new Participant("Sébastien", "Deleuze", "test@test.fr"));
        participantRepository.save(new Participant("Nicolas", "Carlier", "test@test.fr"));
        participantRepository.save(new Participant("aa", "aa", "test@test.fr"));
        participantRepository.save(new Participant("bb", "bb", "test@test.fr"));
        participantRepository.save(new Participant("cc", "cc", "test@test.fr"));
        participantRepository.save(new Participant("dd", "dd", "test@test.fr"));
        participantRepository.save(new Participant("ee", "ee", "test@test.fr"));
        participantRepository.save(new Participant("ff", "ff", "test@test.fr"));
        participantRepository.save(new Participant("gg", "gg", "test@test.fr"));
        participantRepository.save(new Participant("hh", "hh", "test@test.fr"));
        participantRepository.save(new Participant("ii", "ii", "test@test.fr"));
        participantRepository.save(new Participant("jj", "jj", "test@test.fr"));
        participantRepository.save(new Participant("kk", "kk", "test@test.fr"));
        participantRepository.save(new Participant("ll", "ll"));
        participantRepository.save(new Participant("mm", "mm"));
        participantRepository.save(new Participant("nn", "nn"));
        participantRepository.save(new Participant("oo", "oo"));
        participantRepository.save(new Participant("pp", "pp"));
    }
}
