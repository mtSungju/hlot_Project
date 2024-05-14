package org.mt.mms.contr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.contr.service.ContrService;
import org.mt.mms.contr.vo.ContrVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor // autowired
@RequestMapping("/api")
public class ContrController {

    private final ContrService contrService;
    /**
     * 계약 전체 조회
     * */
    @GetMapping("/contrs")
    public ResponseEntity<Result> all() throws Exception{

        return ResponseEntity.ok()
                .body(Result.resSuccess(contrService.all()));
    }



}
