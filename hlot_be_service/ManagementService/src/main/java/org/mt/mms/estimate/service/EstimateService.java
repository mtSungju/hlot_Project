package org.mt.mms.estimate.service;

import org.mt.mms.cmm.dto.Result;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.mt.mms.estimate.vo.EstimateVO;
import org.mt.mms.prod.vo.ProdVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public interface EstimateService {

    List<EstimateExVO> all(HashMap params) throws Exception;

    EstimateExVO one(String id) throws Exception;

    int newEstimate(EstimateVO estimateVO, MultipartFile file) throws Exception;

    boolean possibleConfirm(String topContrId, String compId, String estimateDiv, String estimateLowDiv) throws Exception;

    int confirmEstimate(String id) throws Exception;

    int deleteEstimate(String id) throws Exception;

    int contrEstimate(String id) throws Exception;
}
