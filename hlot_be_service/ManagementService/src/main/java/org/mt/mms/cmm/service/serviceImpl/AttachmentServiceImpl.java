package org.mt.mms.cmm.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.contr.vo.ContrVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService{

    private final CommonMapper commonMapper;
    @Value("${hlot.file.upload.path}")
    private String filePath;
    @Override
    public AttachmentVO selectAttachmentInfo(String fileId) throws Exception {

        return commonMapper.selectAttachmentInfo(fileId);
    }


    @Override
    public AttachmentVO upload(MultipartFile file) throws Exception {
        String fileId = null;

        if(file != null){
            log.info("file : {}", file.getOriginalFilename());

            //1. 서버에 이미지파일을 저장, 이미지를 서버에 업로드
            // 1-1. 파일 저장 위치를 지정하여 파일객체를 포장
            String originFileName = file.getOriginalFilename();

            // 1-1-a 파일명이 중복되지않도록 변경

            Date date = new Date(); // 날짜 생성
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 데이터를 원하는 형태로 다양하게 출력

            String uploadDate = sdf.format(date); // 업로드 년월일

            String uploadFileName = UUID.randomUUID() + "_" + uploadDate;
            // 1-1-b 폴더 위치
            File saveFilePath = new File(filePath + File.separator + uploadFileName); // 파일이 저장될 위치
            File uploadPath = new File(filePath); // 파일 폴더가 없을 경우 대비

            if(!uploadPath.mkdirs()){
                log.info("uploadpath : {}" ,  uploadPath);
                // 1-2 파일을 해당 경로에 업로드
                file.transferTo(saveFilePath);

                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("fileId",uploadFileName);
                paramMap.put("filePath",uploadPath.getPath());
                paramMap.put("orignFileName",originFileName);
                paramMap.put("changeFileName",uploadFileName);
                paramMap.put("fileSize", String.valueOf(file.getSize()));
                paramMap.put("registUserName","tester");


                log.info("paramMap : {}", paramMap);
                fileId = uploadFileName;
                commonMapper.insertFile(paramMap);
            }
        }
        return selectAttachmentInfo(fileId);
    }

    @Override
    public ContrVO pdfUpload(MultipartFile file) {

        if(file != null){
            log.info("PDFFILE : {}", file.getOriginalFilename());


           try {
               // PDF 파일을 로드합니다.
               PDDocument document = Loader.loadPDF(file.getBytes());

               log.info("document : {}", document);

               // PDFTextStripper 클래스를 사용하여 텍스트를 추출합니다.
               PDFTextStripper pdfStripper = new PDFTextStripper();

               // UTF-8로 인코딩하여 텍스트를 추출합니다.
               pdfStripper.setAddMoreFormatting(true);
               pdfStripper.setStartPage(1); // 시작 페이지 설정
               pdfStripper.setEndPage(1); // 끝 페이지 설정
               String text = pdfStripper.getText(document);

               System.out.print(text);

               // 추출된 텍스트를 출력합니다.
               System.out.println("Extracted text:");
               byte[] tArr = text.getBytes(StandardCharsets.UTF_8);
               String pdfText = new String(tArr, StandardCharsets.UTF_8);


//               System.out.print(new String(tArr, StandardCharsets.UTF_8));


               // PDF 문서를 닫습니다.
               document.close();
           }catch (IOException e){
               e.printStackTrace();
           }
        }


        return null;
    }


}
