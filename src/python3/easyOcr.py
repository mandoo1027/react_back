from flask import Flask, request, jsonify
import os
import logging
import easyocr

app = Flask(__name__)

# EasyOCR 초기화
reader = easyocr.Reader(["ko", "en"])  # 한글 + 영어 지원

# 로그 설정
logging.basicConfig(level=logging.DEBUG)  # DEBUG 수준 로그 출력
logger = logging.getLogger(__name__)

@app.route('/ocr', methods=['POST'])
def ocr_images():

    files = request.files.getlist('files')  # 여러 파일 받기
    results = []
    logger.info("Received files: %d", len(files))  # 받은 파일 수 로그 출력

    # 각 이미지 파일을 처리
    for file in files:
        image_path = os.path.join("/tmp", file.filename)
        logger.info("Processing file: %s", file.filename)  # 처리 중인 파일명 로그 출력
        file.save(image_path)  # 임시로 파일 저장

        try:
            # EasyOCR 실행
            result = reader.readtext(image_path, detail=0)  # detail=0: 텍스트만 추출
            print(result)
            results.append({"filename": file.filename, "text": result})  # 결과 저장
            logger.info("OCR successful for file: %s", file.filename)  # OCR 성공 로그
        except Exception as e:
            logger.error("Error processing file %s: %s", file.filename, str(e))  # 오류 로그
            results.append({"filename": file.filename, "error": str(e)})

    # 결과 반환
    logger.info("Returning OCR results for %d files", len(results))  # 결과 반환 로그 출력
    return jsonify({"ocr_results": results})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=False)