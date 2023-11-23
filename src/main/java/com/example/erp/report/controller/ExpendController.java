package com.example.erp.report.controller;

import com.example.erp.member.service.NotificationService;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.service.ProductService;
import com.example.erp.report.dto.DeliveryDto;

import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.dto.UploadFile;
import com.example.erp.report.service.ExpendService;
import com.example.erp.report.service.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ExpendController {

    private final ExpendService expendService;
    private final NotificationService notificationService;
    private final ProductService productService;
    private final FileStore fileStore;


    //리스트띄우기
    @GetMapping("/expend_list")
    public String listExpends(Model model) {
        List<ExpendDto> expends = expendService.getAllExpends();
        model.addAttribute("expends", expends);
        return "report/expend/expend_list";
    }

    //지출결의서추가
    @GetMapping("/expend_add")
    public String quoteAdd(Model model) {
        List<ProductEntity> filteredProducts = expendService.getFilteredProducts();
        System.out.println(filteredProducts);

        model.addAttribute("quoteDto", new QuoteDto());
        model.addAttribute("products", filteredProducts);

        return "report/quote/quote_add";
    }

    @PostMapping("/expend_add")
    @ResponseBody
    public String expendAdd(@ModelAttribute ExpendDto expendDto, HttpSession session) throws IOException {
        UploadFile attachFile = fileStore.storeFile(expendDto.getAttachFile());
        if (attachFile != null) {
            expendDto.setStoreFileName(attachFile.getStoreFileName());
            expendDto.setUploadFileName(attachFile.getUploadFileName());
        }

        expendDto.setLocation(0);//
        expendDto.setWriter(expendService.getMember((String) session.getAttribute("loginId")));
        notificationService.sendToClient(1L, expendDto.getExpendname() + " 지출결의서가 추가되었습니다."); //로그인된 모든 admin에게 알람.
        expendService.save(expendDto);
        return "redirect:/expend_list";
    }



    @GetMapping("/expend_memo/{id}")
    public String expendInfo(Model model, @PathVariable int id) {
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expend", expendDto);
//        System.out.println("자세히보기에 들어오는 데이터:" + expendDto);
        return "report/expend/expend_memo";
    }

    @GetMapping("/expend_edit/{id}")
    public String expendEdit(Model model, @PathVariable int id) {
        List<ProductEntity> products = expendService.getAllProducts();
        model.addAttribute("products", products);
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expendDto", expendDto);
        return "report/expend/expend_edit";
    }

    @PostMapping("/expend_edit_ok")
    @ResponseBody
    public String expendEditOk(@RequestParam(name = "id") int id, @ModelAttribute ExpendDto expendDto) throws IOException, NoSuchAlgorithmException {
        ExpendDto origindto = expendService.findById(id);
        expendDto.setWriter(origindto.getWriter());
        if (origindto.getStoreFileName() == null && expendDto.getAttachFile().isEmpty()) {
            System.out.println("빈파일입니다.");
        } else if (origindto.getStoreFileName() == null && !expendDto.getAttachFile().isEmpty()) {
            UploadFile attachFile = fileStore.storeFile(expendDto.getAttachFile());
            expendDto.setStoreFileName(attachFile.getStoreFileName());
            expendDto.setUploadFileName(attachFile.getUploadFileName());
        } else if (origindto.getStoreFileName() != null && expendDto.getUploadFileName().isEmpty()) {
            System.out.println("삭제된파일");
        } else if (!expendDto.getUploadFileName().isEmpty()) {
            System.out.println("파일수정없음");
        } else if (fileStore.compareFilesByHash(expendDto.getAttachFile(), origindto.getStoreFileName())) {
            System.out.println("같은파일입니다.");
        } else {
            fileStore.deleteFile(origindto.getStoreFileName());
            UploadFile attachFile = fileStore.storeFile(expendDto.getAttachFile());
            expendDto.setStoreFileName(attachFile.getStoreFileName());
            expendDto.setUploadFileName(attachFile.getUploadFileName());
        }

        notificationService.sendToClient(1L, expendDto.getExpendname() + " 견적서가 수정되었습니다.");
        expendService.update(id, expendDto);
        return "변경완료";
    }

    //결제완료시 결제완료.
    @GetMapping ("/expend_check_ok/{id}")
    public String check_ok(@PathVariable int id, HttpSession session,@ModelAttribute ExpendDto expendDto){

        //권한이 admin인사람만 하도록.
        expendDto.setCheckmember(expendService.getMember((String) session.getAttribute("loginId")));
        System.out.println("권한:" + expendDto.getCheckmember().getUserauthority());

        if (!"ADMIN".equals(expendDto.getCheckmember().getUserauthority())) {
            System.out.println("권한이 안됨");
            return "redirect:/expend_list";
        }
        System.out.println("권한이됨");
        expendService.check_ok(id,expendDto);
        return "redirect:/expend_list";
    }

    //배송현재상황
    @PostMapping("/delievery_expend")
    public ResponseEntity<String> receivePayment(@RequestBody DeliveryDto dto) {
        long companyId = dto.getCompany_id();
        int location = dto.getLocation();
//        System.out.println("이건 제대로 되려나: " + dto);
        ExpendDto expendDto = expendService.findById((int) companyId);

        if (expendDto != null) {
            expendDto.setLocation(location);
            if (expendDto.getLocation() == 2) { //배송완료를 눌렀을시
                expendDto.setEndat(LocalDate.now()); //배송완료시간기록
                productService.countupdate(expendDto.getProduct().getId(),expendDto.getQuantity());
                expendService.update((int) companyId, expendDto);
                return ResponseEntity.ok("배송 처리가 확인 되었습니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("배송 처리에 실패했습니다.");

        }
        return ResponseEntity.badRequest().body("배송 처리에 실패했습니다.");
    }

    @GetMapping("/attach/expend/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        ExpendDto expendDto = expendService.findById(Math.toIntExact(itemId));
        String storeFileName = expendDto.getStoreFileName();
        String uploadFileName = expendDto.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));


        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=" + encodedUploadFileName ;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


}
