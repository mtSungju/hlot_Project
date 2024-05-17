package org.mt.mms.estimate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.Util;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.estimate.service.EstimateService;
import org.mt.mms.estimate.vo.EstimateVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class EstimateController {

    private final Util util;

    private final EstimateService estimateService;

    /* estimate 목록 조회 */
    @GetMapping("/estimates")
    public ResponseEntity<Result> all() throws Exception{
        return ResponseEntity.ok()
                .body(Result.resSuccess(estimateService.all()));
    }

    /* estimate 상세 조회 */
    @GetMapping("/estimate/{id}")
    public ResponseEntity<Result> one(@PathVariable String id) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(estimateService.one(id)));
    }

    /* estimate 등록 */
    @PostMapping(value="/estimate", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> newEstimate(@RequestPart EstimateVO data
            , @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {

        String userId = util.getLoginUserId();
        data.setRegistUserName(userId);

        return ResponseEntity.ok()
                .body(Result.resSuccess(estimateService.newEstimate(data, file)));
    }
}
