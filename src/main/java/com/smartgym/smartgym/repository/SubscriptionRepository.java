package com.smartgym.smartgym.repository;

import com.smartgym.smartgym.entity.Subscription;
import com.smartgym.smartgym.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByMemberId(Long memberId);

    Optional<Subscription> findTopByMemberIdOrderByEndDateDesc(Long memberId);

    List<Subscription> findByStatus(SubscriptionStatus status);
}
