package com.am.simpleaddressbook.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/info")
public class InfoController {

    private final ResourceLoader resourceLoader;

    public InfoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/show")
    public String showMyInfo(){
        return "about-me";
    }

    @GetMapping("/showImage")
    public void showImage(HttpServletResponse response) throws IOException {
        Resource imageResource = resourceLoader.getResource("classpath:static/images/myphoto.JPG");
        InputStream inputStream= imageResource.getInputStream();

        response.setContentType("image/jpeg");

        IOUtils.copy(inputStream, response.getOutputStream());
    }
}
