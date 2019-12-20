package cn.edu.nju.example.demo.service;

import cn.edu.nju.example.HttpService;
import cn.edu.nju.nioserver.http.HttpHeaderNames;
import cn.edu.nju.nioserver.http.HttpRequest;
import cn.edu.nju.nioserver.http.HttpResponse;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;

public class HttpMimeService implements HttpService {
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String path = request.uri();
        String fileName = "src/main/resources/" + path;
        String type = path.substring(1);
        String pre = type.split("/")[0];
        String post = type.split("\\.")[type.split("\\.").length - 1];
      /*
        尝试将文件转为二进制进行传输
         String biImage = "";
        try {
            BufferedImage bi = ImageIO.read(new File(fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            biImage = encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("You have get url with " + path + " and ")
                .append("the mime type of file is " + pre + "/" + post);
//            .append(biImage);
        //后面此处需要考虑如何设计，无法默认长度（可以是chunk的方式）
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, "" + responseBuilder.toString().getBytes(StandardCharsets.UTF_8).length);
        response.content().setContent(responseBuilder.toString());

    }
}
