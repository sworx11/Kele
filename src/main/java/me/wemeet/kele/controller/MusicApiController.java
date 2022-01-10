package me.wemeet.kele.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.wemeet.kele.common.helper.HttpClientHelper;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/music/api")
public class MusicApiController {

    private ISourceService sourceService;

    @Autowired
    public void setSourceService(ISourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping("/tx/{id}")
    public void redirectToTxMusic(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "quality", defaultValue = "128k") String quality,
            HttpServletResponse response
    ) throws IOException {

        String cookie = sourceService.getCookieById("tx");
        if (Objects.equals(cookie, "")) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
            return;
        }
        cookie = new String(Base64.getDecoder().decode(cookie));

        JSONObject config = JSON.parseObject("{\"128k\": {\"s\": \"M500\",\"e\": \".mp3\",\"bitrate\": \"128kbps\"},\"320k\": {\"s\": \"M800\",\"e\": \".mp3\",\"bitrate\": \"320kbps\"},\"flac\": {\"s\": \"F000\",\"e\": \".flac\",\"bitrate\": \"FLAC\"}}");
        JSONObject q = config.getJSONObject(quality);

        String filename = q.getString("s") + id + id + q.getString("e");

        String[] filenames = new String[1];
        filenames[0] = filename;
        String[] ids = new String[1];
        ids[0] = id;
        int[] songtype = new int[1];

        JSONObject reqData = new JSONObject();
        JSONObject param = new JSONObject();
        JSONObject req_0 = new JSONObject();
        JSONObject comm = new JSONObject();
        param.put("filename", filenames);
        param.put("guid", "10000");
        param.put("songmid", ids);
        param.put("songtype", songtype);
        param.put("uin", "0");
        param.put("loginflag", 1);
        param.put("platform", "20");
        req_0.put("module", "vkey.GetVkeyServer");
        req_0.put("method", "CgiGetVkey");
        req_0.put("param", param);
        comm.put("uin", "0");
        comm.put("format", "json");
        comm.put("ct", 24);
        comm.put("cv", 0);
        reqData.put("req_0", req_0);
        reqData.put("loginUin", "0");
        reqData.put("comm", comm);

        String data = reqData.toJSONString();

        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=" + data;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("cookie", cookie);

        String jsonStr = HttpClientHelper.doGet(url, headers);
        if (jsonStr == null) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int code = jsonObject.getInteger("code");
        if (code == 0) {
            JSONObject d = jsonObject.getJSONObject("req_0").getJSONObject("data");
            String host = d.getJSONArray("sip").getString(0);
            String info = d.getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
            response.sendRedirect(host + info);
        } else {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
        }
    }

    @GetMapping("/wy/{id}")
    public void redirectToWyMusic(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "quality", defaultValue = "128k") String quality,
            HttpServletResponse response
    ) throws IOException {
        String url = "http://music.163.com/song/media/outer/url?id=" + id;
        response.sendRedirect(url);
    }

    @GetMapping("mg/{id}")
    public void redirectToMgMusic(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "quality", defaultValue = "128k") String quality,
            HttpServletResponse response
    ) throws IOException {
        Map<String, String > q = new HashMap<>(){{
            put("128k","PQ");
            put("320k", "HQ");
            put("flac", "SQ");
            put("flac32bit", "ZQ");
        }};

        quality = q.get(quality);
        String url = "https://app.c.nf.migu.cn/MIGUM2.0/strategy/listen-url/v2.2?netType=01&resourceType=E&songId=" + id + "&toneFlag=" + quality;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("channel", "0146951");
        headers.add("uid", "1234");
        String jsonStr =  HttpClientHelper.doGet(url, headers);
        if (jsonStr == null) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String code = jsonObject.getString("code");
        if ("000000".equals(code)) {
            String purl = jsonObject.getJSONObject("data").getString("url");
            if (purl.startsWith("//")) {
                purl = "https:" + purl;
            }
            purl = purl.replaceAll("\\+", "%2B");
            response.sendRedirect(purl);
        } else {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
        }
    }

    @GetMapping("kg/{id}/{hash}")
    public void redirectToKgMusic(
            @PathVariable(name = "id") String id,
            @PathVariable(name = "hash") String hash,
            @RequestParam(name = "quality", defaultValue = "128k") String quality,
            HttpServletResponse response
    ) throws Exception {
        String url = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&hash=" + hash + "&dfid=dfid&mid=mid&platid=4&album_id=" + id;
        String jsonStr =  HttpClientHelper.doGet(url);
        if (jsonStr == null) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int code = jsonObject.getInteger("err_code");
        if (code == 0) {
            String purl = jsonObject.getJSONObject("data").getString("play_url");
            response.sendRedirect(purl);
        } else {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
        }
    }

    @GetMapping("kw/{id}")
    public void redirectToKwMusic(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "quality", defaultValue = "128k") String quality,
            HttpServletResponse response
    ) throws Exception {
        String url = "http://www.kuwo.cn/api/v1/www/music/playUrl?mid=" + id + "&type=music&httpsStatus=1";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0");
        headers.add("Referer", "http://kuwo.cn/");
        String jsonStr = HttpClientHelper.doGet(url, headers);
        if (jsonStr == null) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int code = jsonObject.getInteger("code");
        if (code == 200) {
            String purl = jsonObject.getJSONObject("data").getString("url");
            response.sendRedirect(purl);
        } else {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code);
        }
    }

}
