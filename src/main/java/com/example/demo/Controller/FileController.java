package com.example.demo.Controller;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.util.UploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api")
@Controller
public class FileController {

    @Autowired
    private UploadImage uploadImage;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public Map<String, Object> uploadAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("status", "fail");
            response.put("message", "上传失败，请选择一个文件");
            return response;
        }
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            uploadImage.upload(file.getBytes(), fileName);

            String fileUrl = "https://yueyue0313.dynv6.net:8008/" + fileName;
            boolean result = userMapper.updateUserImgUrl(username, fileUrl);
            if (result) { System.out.println("更新成功"); }
            else { System.out.println("更新失败"); }

            response.put("status", "success");
            response.put("avatarUrl", fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "fail");
            response.put("message", "上传失败");
        }

        return response;
    }
}
