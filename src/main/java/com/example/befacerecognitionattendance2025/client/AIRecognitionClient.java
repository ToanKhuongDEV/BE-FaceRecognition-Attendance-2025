package com.example.befacerecognitionattendance2025.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class AIRecognitionClient {

    private final WebClient webClient;

    public AIRecognitionClient() {
        String baseUrl = "http://localhost:8000/api";
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Gửi ảnh khuôn mặt đến API để trích xuất template (tensor).
     */
    public String extractFaceTemplate(MultipartFile image) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            }).contentType(MediaType.IMAGE_JPEG);

            return webClient.post()
                    .uri("/extract-face")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Lỗi khi gọi Flash API extract-face: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Không thể trích xuất khuôn mặt từ ảnh");
        } catch (Exception e) {
            log.error("Lỗi không xác định khi trích xuất khuôn mặt", e);
            throw new RuntimeException("Lỗi hệ thống khi trích xuất khuôn mặt");
        }
    }

    /**
     * Gửi template khuôn mặt đến API để so sánh và xác định nhân viên.
     */
    public String identifyEmployee(String faceTemplate) {
        try {
            return webClient.post()
                    .uri("/compare-face")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{\"template\": \"" + faceTemplate + "\"}")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Lỗi khi gọi Flash API compare-face: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Không thể xác định khuôn mặt trong hệ thống");
        } catch (Exception e) {
            log.error("Lỗi không xác định khi xác minh khuôn mặt", e);
            throw new RuntimeException("Lỗi hệ thống khi xác minh khuôn mặt");
        }
    }
}
