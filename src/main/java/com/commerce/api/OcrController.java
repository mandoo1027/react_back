package com.commerce.api;

import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/admin/service")
public class OcrController {



    @Value("${file.home-dir}") // 파일 경로
    private String homeDir;

    @Value("${file.tmp-file-upload-dir}") // 임시 저장소에 저장
    private String tmpfileUploadDir;

    @Value("${company.chk-url}") // 회사 검증 API URL
    private String companyChkUrl;

    @Value("${company.service-key}") // 회사 검증 API Key
    private String serviceKey;

    @Value("${ocr.url}") // OCR API URL
    private String ocrUrl;


    public static List<String> cleanText(List<String> textList) {
        List<String> cleanedText = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\w\\s:/-]+");

        for (String text : textList) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String cleaned = matcher.group().trim().replaceAll("\\s+", " ");
                if (!cleaned.isEmpty()) {
                    cleanedText.add(cleaned);
                }
            }
        }
        return cleanedText;
    }

    public static Map<String, String> normalizeOCRResult(List<String> textList) {
        Map<String, String> fields = new HashMap<>();
        fields.put("등록번호", "");
        fields.put("상호", "");
        fields.put("대표자명", "");
        fields.put("생년월일", "");
        fields.put("개업연월일", "");
        fields.put("사업장소재지", "");
        fields.put("업태", "");
        fields.put("종목", "");

        for (int i = 0; i < textList.size(); i++) {
            String text = textList.get(i);
            if (text.contains("등록번호")) {
                fields.put("등록번호", (i + 1 < textList.size()) ? textList.get(i + 1) : "");
            } else if (text.contains("명 :") || text.contains("영")) {
                fields.put("대표자명", (i + 1 < textList.size()) ? textList.get(i + 1) : "");
            } else if (text.contains("생 년 원 일")) {
                fields.put("생년월일", (i + 1 < textList.size() ? textList.get(i + 1) : "") + " " +
                        (i + 2 < textList.size() ? textList.get(i + 2) : ""));
            } else if (text.contains("개 업 연 원 일")) {
                fields.put("개업연월일", (i + 1 < textList.size() ? textList.get(i + 1) : "") + " " +
                        (i + 2 < textList.size() ? textList.get(i + 2) : ""));
            } else if (text.contains("사 업 장 소 재 지") || text.contains("사 업장소 재 지")) {
                fields.put("사업장소재지", (i + 1 < textList.size()) ? textList.get(i + 1) : "");
            } else if (text.contains("사 업 의 중류") || text.contains("사 업 의 종류")) {
                fields.put("업태", (i + 1 < textList.size()) ? textList.get(i + 1) : "");
            } else if (text.contains("도매 및 소매업")) {
                fields.put("종목", textList.subList(i + 1, Math.min(i + 7, textList.size())).toString());
            } else if (i == 5) {
                fields.put("상호", text);
            }
        }
        return fields;
    }


    public static void processOCRResponse(Object response) {
        // Object를 Map 형태로 변환
        if (!(response instanceof Map)) {
            System.out.println("잘못된 응답 형식");
            return;
        }

        Map<String, Object> responseMap = (Map<String, Object>) response;


        List<Map<String, Object>> ocrResults = (List<Map<String, Object>>) responseMap.get("ocr_results");
        if (ocrResults == null) {
            System.out.println("OCR 결과가 없음");
            return;
        }

        for (Map<String, Object> ocrResult : ocrResults) {
            List<String> textList = (List<String>) ocrResult.get("text");
            if (textList == null) continue;

            List<String> cleanedText = cleanText(textList);
            Map<String, String> normalizedOCR = normalizeOCRResult(cleanedText);

            System.out.println("OCR 결과 (" + ocrResult.get("filename") + "):");
            normalizedOCR.forEach((key, value) -> System.out.println(key + ": " + value));
            System.out.println("\n----------------------------------\n");
        }
    }



    @PostMapping("/getOcrData")
    public ResultVO uploadFile(List<MultipartFile> files ) throws UserException {
        ResultVO resultVO = new ResultVO();
        HttpClient client = HttpClients.createDefault();
        String url = ocrUrl;  // 외부 API URL
        HttpPost post = new HttpPost(url);

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 여러 파일을 처리
            for (MultipartFile file : files) {
                // 임시로 파일 저장
                String uploadDir = tmpfileUploadDir ;

                LocalDateTime now = LocalDateTime.now();
                String yyyymmdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                Path uploadPath = Paths.get(homeDir + File.separator+ uploadDir+File.separator+yyyymmdd+File.separator);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssnnnnnnnnn");
                String formattedDateTime = now.format(formatter);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);//파일 생성
                }

                // 원본 파일 확장자 추출
                String extension = "";
                int dotIndex = file.getOriginalFilename().lastIndexOf(".");
                if (dotIndex > 0) { // 확장자가 존재하는 경우
                    extension = file.getOriginalFilename().substring(dotIndex);
                }

                // 새로운 파일명 생성 (예: "file_20250205_153045.png")
                //return "file_" + formattedDateTime + extension;
                // 파일 저장
                 String filePath = uploadDir +File.separator +yyyymmdd+File.separator+ formattedDateTime + extension;
                 File newFile = new File(homeDir+filePath);
                 file.transferTo(newFile);

                // 파일을 요청의 'files' 파라미터로 추가
                builder.addBinaryBody("files", newFile, ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename());
                // 파일 전송 후 삭제
                //tempFile.delete();
            }

            // 요청 본문 설정
            HttpEntity entity = builder.build();
            post.setEntity(entity);
            // HTTP 요청 실행
            try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(post)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseString = EntityUtils.toString(response.getEntity());


                Gson gson = new Gson();
                Object jsonString = gson.fromJson(responseString,Object.class);
                processOCRResponse(jsonString);
                if (statusCode == 200) {resultVO.setSucessCode();
                    resultVO.setResultData(jsonString);  // 응답 처리
                } else {
                    resultVO.setResultData("Error: " + statusCode);
                }

            }

        } catch (IOException e) {
            throw new UserException("파일 업로드 중 오류가 발생했습니다.");
        }

        return resultVO;
    }

    @PostMapping("/checkCompanyStatus")
    Object checkCompanyStatus() throws UserException, IOException {
        ResultVO resultVO = new ResultVO();
        HttpClient client = HttpClients.createDefault();
        String urlstr = companyChkUrl +serviceKey; // 외부 API URL
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 요청 방식 및 헤더 설정
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        // JSON 데이터 생성
        // JSON 데이터 생성 (한 줄로!)
        String jsonInputString = new ObjectMapper().writeValueAsString(
                Map.of("b_no", Arrays.asList("5102901093", "7741001486"))
        );

        // 요청 본문에 JSON 데이터 쓰기
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
// 응답 데이터 읽기
        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        } else { // 오류 응답
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // JSON 응답 출력
        System.out.println("Response: " + response.toString());

        // JSON 변환
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode resultDataJson = objectMapper.readTree(response.toString()); // 다시 JSON으로 변환

        if (responseCode == 200) {
        resultVO.setSucessCode();
        resultVO.setResultData(resultDataJson);  // 응답 처리
    }

        return resultVO;
    }



}
