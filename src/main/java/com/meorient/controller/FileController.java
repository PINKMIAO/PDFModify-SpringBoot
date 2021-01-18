package com.meorient.controller;

import com.meorient.pojo.Company;
import com.meorient.service.CompanyService;
import com.meorient.service.CompanyServiceImpl;
import com.meorient.utils.PdfBoxTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    CompanyServiceImpl companyService;

    private InputStream in;
    private String uploadFileName;
    Map<String, List<Company>> dataInfo;

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String fileUploadAndDeal(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        //获取文件名  file.getOriginalFilename()
        uploadFileName = file.getOriginalFilename();
        System.out.println("要上传的文件名:"+ uploadFileName);
        //如果文件名为空或不是pdf文件，直接返回首页
        if (!StringUtils.isNotBlank(uploadFileName)
                || !".pdf".equals(uploadFileName.substring(this.uploadFileName.length()-4, this.uploadFileName.length()))) {
            model.addAttribute("msg", "未上传文件或文件类型错误，请选择对应文件");
            return "index";
        }

        in = file.getInputStream();
        uploadFileName = uploadFileName.substring(0, uploadFileName.length() - 4);

        modifyPDF(in, uploadFileName);
        in.close();
        model.addAttribute("msg", "上传处理成功");

        return "index";
    }

    /**
     * 获取公司信息及处理
     * @param in
     */
    public void modifyPDF(InputStream in, String uploadFileName) {

        PdfBoxTest pdfBoxTest = null;
        try {
            pdfBoxTest = new PdfBoxTest(uploadFileName);
            getCompanyInfo();

            pdfBoxTest.getCoordinate(in, dataInfo);

//            download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download() throws IOException{
        //从我们的上传文件夹中去取
        // "D:" + File.separator + "home" + File.separator + "crm" + File.separator + "pdfmodify";
        String downloadFilePath="D:\\temp";
        //新建一个文件
        File file = new File(downloadFilePath + File.separator + uploadFileName +"(水印).pdf");
        //http头信息
        HttpHeaders headers = new HttpHeaders();
        //设置编码
        String downloadFileName = new String((uploadFileName +"(水印).pdf").getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);


        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);

    }

    /**
     * 获取客户信息
     * @return
     * @throws Exception
     */
    public void getCompanyInfo(){
        dataInfo = new HashMap<>();
        dataInfo = companyService.getAllMsg();
    }
}
