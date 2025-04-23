package com.purity.springsseserver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
public class SseController {

    @CrossOrigin( origins = "http://localhost:5173") // CORS 문제를 해결하기 위해 추가
    @GetMapping(value = "/sse" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc() throws Exception {
        SseEmitter emitter = new SseEmitter();

        System.out.println("SSE SERVER START!");
        // 새로운 Thread로 실행
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    emitter.send(SseEmitter.event().data("Hello " + System.currentTimeMillis()));
                    Thread.sleep(1000); // 1초 대기
                }
                emitter.complete();
            } catch (IIOException | InterruptedException e) {
                emitter.completeWithError(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return emitter;
    }
}
