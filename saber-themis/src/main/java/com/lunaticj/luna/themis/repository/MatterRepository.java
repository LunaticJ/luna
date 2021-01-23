package com.lunaticj.luna.themis.repository;

import com.lunaticj.luna.themis.entity.MatterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * matter repository
 *
 * @author kuro
 * @create 2021-01-23
 **/
@Repository
public interface MatterRepository extends MongoRepository<MatterEntity, String> {
}
