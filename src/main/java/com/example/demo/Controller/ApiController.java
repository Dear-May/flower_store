package com.example.demo.Controller;

import com.example.demo.Entity.GoodEntity;
import com.example.demo.Mapper.GoodsMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.util.UploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api")
@Controller
public class ApiController {

    @Autowired
    private UploadImage uploadImage;
    @Autowired
    UserMapper userMapper;
    @Qualifier("goodsMapper")
    @Autowired
    private GoodsMapper goodsMapper;

    @RequestMapping(value = "/searchGoods",method = RequestMethod.POST)
    @ResponseBody
    public void searchGoods(@RequestBody Map<String, Object> keyword, HttpServletResponse response) throws IOException {
        String keywordStr = (String) keyword.get("keyword");
        List<GoodEntity> goods = goodsMapper.selectGoodByName(keywordStr);
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(goods.toString());
    }

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
