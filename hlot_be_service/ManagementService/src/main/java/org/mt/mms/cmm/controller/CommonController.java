package org.mt.mms.cmm.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.service.CommonService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.cmm.vo.CmmCodeVO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j // log용도
@RequestMapping(value = "/common")
@RequiredArgsConstructor // autowired 대체자
public class CommonController {

    private final AttachmentService attachmentService;

    private final CommonService commonService;

    @Value("${hlot.file.upload.path}")
    private String filePath;



    @PostMapping(value = "/upload" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> upload(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception{

        log.info("file-info : {}", file.getOriginalFilename());

        return ResponseEntity.ok()
                .body(Result.resSuccess(attachmentService.upload(file)));
    }



    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId ) throws  Exception{
        log.info("======download========");
        log.info("fileId ==>" + fileId);

        AttachmentVO result = attachmentService.selectAttachmentInfo(fileId);

        log.info("result : {}", result);

//        String filePath = result.getFilePath();
        String uploadFileName = result.getOrignFileName();

        Path p = Paths.get(filePath + File.separator + result.getChangeFileName()); // 경로 정의하기

        byte[] data = Files.readAllBytes(p); // Files.readAllBytes => 파일 내용을 한번에 바이트 배열로 읽는데 사용

        ByteArrayResource resource = new ByteArrayResource(data);

        log.info("urlResourse : {}", resource);

        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        log.info("encodeUploadFileName : {}", encodeUploadFileName);


        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(uploadFileName, "utf-8") + "\"")
                .body(resource);
    }


    @PostMapping(value = "/pdfUpload" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> pdfUpload(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception{

        log.info("PDF-FILE-INF : {}", file.getOriginalFilename());



        return ResponseEntity.ok()
                .body(Result.resSuccess(attachmentService.pdfUpload(file)));
    }

    /* 원계약 ID,명 전체조회 */
    @GetMapping(value="/topContrNms")
    public ResponseEntity<Result> topContrNms() throws Exception {
        log.info("@@@@@@@@@@@@@@@@");
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.topContrNms()));
    }

    /* 업체 ID,명 전체조회*/
    @GetMapping(value="/compNms")
    public ResponseEntity<Result> compNms() throws Exception {

        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.compNms()));
    }

    /* 공통코드 조회 */
    @GetMapping(value={"/code/{codeNm}", "/code"})
    public ResponseEntity<Result> code(@PathVariable(required = false) String codeNm) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.getCode(codeNm)));
    }


    @GetMapping(value="/topContrNm/{topContrId}")
    public ResponseEntity<Result> topContrNm(@PathVariable String topContrId) throws Exception {
        log.info("topContrId : {}", topContrId);
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.topContrNm(topContrId)));
    }

    /* 업체 ID,명 전체조회*/
    @GetMapping(value="/compNm/{compId}")
    public ResponseEntity<Result> compNm(@PathVariable String compId) throws Exception {
        log.info("compId : {}", compId);
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.compNm(compId)));
    }

    @GetMapping(value="/codeGroup")
    public ResponseEntity<Result> getCodeGroup(){
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.getCodeGroup()));
    }

    @PostMapping(value="/newCmmCode")
    public ResponseEntity<Result> newCmmCode(@RequestBody CmmCodeVO data){
        log.info("공통코드 저장 : {}", data);

        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.newCmmCode(data),true));
    }

    @DeleteMapping(value="/delCmmCode")
    public ResponseEntity<Result> deleteCmmCode(@RequestBody CmmCodeVO data){
        log.info("코드 : {}", data.getCode());
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.deleteCmmCode(data.getCode()),true));
    }

    @GetMapping(value="/selectLatestTopContrInfo/{userName}")
    public ResponseEntity<Result> selectLatestInfo(@PathVariable String userName){
        log.info("userName :{}", userName);
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.selectLatestTopContrInfo(userName)));
    }

    @GetMapping(value="/selectLatestEstimateInfo/{userName}")
    public ResponseEntity<Result> selectLatestEstimateInfo(@PathVariable String userName){
        log.info("userName :{}", userName);
        return ResponseEntity.ok()
                .body(Result.resSuccess(commonService.selectLatestEstimateInfo(userName)));
    }
}
