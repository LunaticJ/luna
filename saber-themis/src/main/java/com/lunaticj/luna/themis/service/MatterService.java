package com.lunaticj.luna.themis.service;

import com.lunaticj.luna.themis.entity.MatterEntity;
import com.lunaticj.luna.themis.repository.MatterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * matter service
 *
 * @author kuro
 * @create 2021-01-23
 **/
@Service
public class MatterService {
    @Resource
    private MongoTemplate mongoTemplate;

    public List<MatterEntity> getAllList() {
        return mongoTemplate.findAll(MatterEntity.class);
    }
}
