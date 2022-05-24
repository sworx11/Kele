package me.wemeet.kele.controller;

import io.trbl.blurhash.BlurHash;
import me.wemeet.kele.common.response.KeleResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/blur")
public class BlurHashController {
    @GetMapping("")
    public KeleResponseEntity<String> blurHash(String url) {
        if (url.startsWith("https")) {
            url = url.replace("https", "http");
        }

        try {
            URL _url = new URL(url);
            BufferedImage image = ImageIO.read(_url);
            String hash = BlurHash.encode(image, 2, 3);
            return KeleResponseEntity.<String>builder().ok(hash).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return KeleResponseEntity.<String>builder().ok("JGF5]+Yk@-5c@[or").build();
    }
}
