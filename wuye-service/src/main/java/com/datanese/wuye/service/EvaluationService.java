package com.datanese.wuye.service;

import com.alibaba.fastjson.JSON;
import com.datanese.wuye.dto.CommunityDTO;
import com.datanese.wuye.dto.EvaluationDTO;
import com.datanese.wuye.mapper.EvaluationMapper;
import com.datanese.wuye.po.EvaluationPO;
import com.datanese.wuye.po.CommunityPO;
import com.datanese.wuye.util.SnowflakeIdWorker;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bing.a.qian on 9/8/2017.
 */
@Service
public class EvaluationService {

    @Autowired
    EvaluationMapper evaluationMapper;


    public  List<EvaluationDTO> getAllGoodEvaluation(Integer communityId){
        List<EvaluationPO> poList=evaluationMapper.getAllGoodEvaluation(communityId);
        List<EvaluationDTO> dtoList=new LinkedList();
        for (EvaluationPO evaluationPO:poList)
        {
            String poString = JSON.toJSONString(evaluationPO);
            EvaluationDTO evaluationDTO = JSON.parseObject(poString, EvaluationDTO.class);
            List<String> result = Splitter.on(";").trimResults().splitToList(evaluationPO.getUrls());
            evaluationDTO.setImageURL(result.toArray(new String[result.size()]));

            dtoList.add(evaluationDTO);
        }
        return dtoList;
    }


    public  List<EvaluationDTO> getAllBadEvaluation(@PathVariable Integer communityId) {
        List<EvaluationPO> poList=evaluationMapper.getAllBadEvaluation(communityId);
        List<EvaluationDTO> dtoList=new LinkedList();
        for (EvaluationPO evaluationPO:poList)
        {
            String poString = JSON.toJSONString(evaluationPO);
            EvaluationDTO evaluationDTO = JSON.parseObject(poString, EvaluationDTO.class);
            List<String> result = Splitter.on(";").trimResults().splitToList(evaluationPO.getUrls());
            evaluationDTO.setImageURL(result.toArray(new String[result.size()]));
            dtoList.add(evaluationDTO);
        }
        return dtoList;

    }

    public void review(@RequestBody EvaluationDTO evaluationDTO) {
        String dtoString = JSON.toJSONString(evaluationDTO);
        EvaluationPO evaluationPO = JSON.parseObject(dtoString, EvaluationPO.class);
        if(evaluationDTO.getImageURL()!=null){
            String result = Joiner.on(";").join(evaluationDTO.getImageURL());
            evaluationPO.setUrls(result);
        }
        evaluationPO.setId(SnowflakeIdWorker.nextId());
        evaluationMapper.insertEvaluation(evaluationPO);
    }

    public List<CommunityPO> search(String keyword) {
        return null;
    }

    public List<EvaluationPO> getAllEvaluation(Integer residentialDistrictId) {

        return new ArrayList<>();
    }
}


