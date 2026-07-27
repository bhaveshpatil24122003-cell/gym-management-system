package com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gym.entity.MemberEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByRollNo(String rollNo);
    List<MemberEntity> findAllByOrderBySequenceNoAsc();
}