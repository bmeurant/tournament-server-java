package org.resthub.sample.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.resthub.sample.tournament.model.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {

}
