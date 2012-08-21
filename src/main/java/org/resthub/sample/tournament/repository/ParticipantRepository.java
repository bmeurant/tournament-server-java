package org.resthub.sample.tournament.repository;

import org.resthub.sample.tournament.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
