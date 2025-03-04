from flask import Flask, request, jsonify
import os
from paddleocr import PaddleOCR

app = Flask(__name__)

# PaddleOCR 초기화
ocr = PaddleOCR(use_angle_cls=True, lang='en')  # 사용할 언어와 옵션을 설정

@app.route('/ocr', methods=['POST'])
def ocr_images():
    files = request.files.getlist('files')  # 여러 파일을 받음
    results = []

    # 각 이미지 파일을 처리
    for file in files:
        image_path = os.path.join("/tmp", file.filename)
        file.save(image_path)  # 임시로 파일 저장
        result = ocr.ocr(image_path, cls=True)  # OCR 실행
        results.append(result)  # 결과 저장

    # 결과 반환
    return jsonify({"ocr_results": results})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)