package com.am.simpleaddressbook.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/info")
public class InfoController {

    private final ResourceLoader resourceLoader;

    public InfoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/show")
    public String showMyInfo() throws IOException {
        return "about-me";
    }

    @GetMapping("/showImage")
    public void showImage(HttpServletResponse response) throws IOException {
        Resource imageResource = resourceLoader.getResource("classpath:static/images/myphoto.JPG");
        InputStream inputStream= imageResource.getInputStream();

        response.setContentType("image/jpeg");

        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/download")
    @ResponseBody
    public void downloadCv(HttpServletResponse response){
        Resource cvFile= resourceLoader.getResource("classpath:static/pdf/Aleksandar Mitic.pdf");


//        Next 3 lines are very important, without they the download can't start
//        I found this code on youtube :https://www.youtube.com/watch?v=QUT9vd-YhQ0&ab_channel=ChargeAhead
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment; filename="+cvFile.getFilename());
        response.setHeader("Content-Transfer-Encoding","binary");

        try{
            BufferedOutputStream bout= new BufferedOutputStream(response.getOutputStream());
            InputStream in= cvFile.getInputStream();

            int len;
            byte[] data= new byte[1024];
            while ((len= in.read(data))> 0)
                bout.write(data, 0, len);


            bout.close();
            in.close();
            response.flushBuffer();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
