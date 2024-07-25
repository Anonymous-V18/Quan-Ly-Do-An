package com.hcv.repository;

import com.hcv.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedbackRepository extends JpaRepository<FeedbackEntity, String> {

    FeedbackEntity findOneById(String id);
}
