package org.mt.mms.cmm.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.Util;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.cmm.service.CommonService;
import org.mt.mms.cmm.vo.CmmCodeVO;
import org.mt.mms.company.vo.CompanyVO;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.mt.mms.estimate.vo.EstimateVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;

    private final Util util;


    @Override
    public List<TopContrVO> topContrNms() throws Exception {
        return commonMapper.selectTopContrNms();
    }

    @Override
    public List<CompanyVO> compNms() throws Exception {
        return commonMapper.selectCompNms();
    }

    /**
     * @param b 사업유형
     * @param table 테이블구분
     * @return seq ID
     * @throws Exception
     */
    @Override
    public String selectHlotSeq(String b, String table) throws Exception {
        HashMap<String, String> param = new HashMap<>();
        param.put("a", util.getUserAuth()); // 00: 관리자 | 01: 사업1 | 02: 사업2
        param.put("b", b);
        param.put("table", table);
        return commonMapper.selectHlotSeq(param);
    }

    @Override
    public List<CmmCodeVO> getCode(String codeNm) throws Exception {
        return commonMapper.selectCode(codeNm);
    }

    @Override
    public TopContrVO topContrNm(String topContrId) {
        return commonMapper.topContrNm(topContrId);
    }

    @Override
    public CompanyVO compNm(String compId) {
        return commonMapper.compNm(compId);
    }

    @Override
    public List<CmmCodeVO> getCodeGroup() {
        return commonMapper.getCodeGroup();
    }

    @Override
    public int newCmmCode(CmmCodeVO data) {

        // 코드그룹명 추출
        String codeGroupNm = commonMapper.getcodeGroupNm(data.getCodeGroup());
        log.info("codeGroupNm : {}", codeGroupNm);
        data.setCodeGroupNm(codeGroupNm);

        return commonMapper.newCmmCode(data);
    }

    @Override
    public int deleteCmmCode(String code) {
        return commonMapper.deleteCmmCode(code);
    }

    @Override
    public List<TopContrVO> selectLatestTopContrInfo(String userName) {

        return commonMapper.selectLatestTopContr(userName);
    }

    @Override
    public List<EstimateExVO> selectLatestEstimateInfo(String userName) {
        return commonMapper.selectLatestEstimate((userName));
    }
}
