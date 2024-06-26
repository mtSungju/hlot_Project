package org.mt.mms.topContr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopContrVO {

    private String topContrId;
    private String topContrNm;
    private String topContrDiv;
    private String clientComp;
    private String clientCompId;
    private String topContrDate;
    private String topContrStDate;
    private String topContrEndDate;
    private String contrStDate;
    private String contrEndDate;
    private String prodNm;
    private String contrAmount;
    private String totalServBokAmount; // 총용역부기금액
    private String amout1;
    private String amout2;
    private String amout3;
    private String amout4;
    private String amout5;
    private String deliveryDeadline;
    private String demandInst;
    private String demandInstId;
    private String topContrFileId;
    private String fileId;
    private String filePath;
    private String orignFileName;
    private String changeFileName;
    private String registUserName;  // 등록자
    private String registDate;
}
