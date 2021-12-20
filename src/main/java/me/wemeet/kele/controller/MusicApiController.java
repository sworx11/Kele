package me.wemeet.kele.controller;

import me.wemeet.kele.common.helper.HttpClientHelper;
import me.wemeet.kele.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/music/api")
public class MusicApiController {
    @Autowired
    private ISourceService sourceService;

    @GetMapping("/tx")
    public String getTxMusicUrl(String data) {
        String cookie = sourceService.getCookieById("tx");
        if (Objects.equals(cookie, "")) {
            return "";

        }
        cookie = new String(Base64.getDecoder().decode(cookie));

        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=" + data;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("cookie", cookie);

        return HttpClientHelper.doGet(url, headers);
    }
}
