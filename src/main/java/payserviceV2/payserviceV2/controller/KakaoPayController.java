package payserviceV2.payserviceV2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payserviceV2.payserviceV2.exception.BusinessLogicException;
import payserviceV2.payserviceV2.exception.ExceptionCode;
import payserviceV2.payserviceV2.patdto.KakaoApproveResponse;
import payserviceV2.payserviceV2.patdto.KakaoCancelResponse;
import payserviceV2.payserviceV2.patdto.KakaoReadyResponse;
import payserviceV2.payserviceV2.payservice.KakaoPayService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity readyToKakaoPay(@RequestBody KakaoPayRequest request) {
//        KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady();
//        return ResponseEntity.ok(kakaoReadyResponse); // KakaoReadyResponse를 ResponseEntity로 감싸서 반환
        // Log the request data for debugging purposes
        System.out.println("Received request: " + request);

        // Call the service layer to process the payment request
        KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady(request);

        // Return the response as ResponseEntity
        return ResponseEntity.ok(kakaoReadyResponse);
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel() {

        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public void fail() {

        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
    }

    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund() {

        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}