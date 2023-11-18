package com.example.erp.report.controller;

import com.example.erp.member.service.NotificationService;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.service.ProductService;
import com.example.erp.report.dto.DeliveryDto;

import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.service.ExpendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ExpendController {

    private final ExpendService expendService;
    private final NotificationService notificationService;
    private final ProductService productService;

    //리스트띄우기
    @GetMapping("/expend_list")
    public String listExpends(Model model) {
        List<ExpendDto> expends = expendService.getAllExpends();
        model.addAttribute("expends", expends);
        return "report/expend/expend_list";
    }

    //지출결의서추가
    @GetMapping("/expend_add")
    public String expendAdd(Model model) {
        List<ProductEntity> products = expendService.getAllProducts();
        System.out.println(products);
        model.addAttribute("expendDto", new ExpendDto());
        model.addAttribute("products", products);
        return "report/expend/expend_add";
    }

    @PostMapping("/expend_add")
    public String expendAdd(@ModelAttribute ExpendDto expendDto, HttpSession session) {
        expendDto.setLocation(0);//
        expendDto.setWriter(expendService.getMember((String) session.getAttribute("loginId")));
        notificationService.sendToClient(1L, expendDto.getId() + "번 지출결의서가 추가되었습니다."); //로그인된 모든 admin에게 알람.
        expendService.save(expendDto);
        return "redirect:/expend_list";
    }



    @GetMapping("/expend_memo/{id}")
    public String quoteInfo(Model model, @PathVariable int id) {
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
    public String expendEditOk(@RequestParam(name = "id") int id, @ModelAttribute ExpendDto expendDto) {
        notificationService.sendToClient(1L, expendDto.getId() + "번 지출결의서가 수정되었습니다."); //로그인된 모든 admin에게 알람.
        expendService.update(id, expendDto);
        return "redirect:/expend_list";
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
            if (expendDto.getLocation() == 1 && expendDto.getProduct().getCount()<expendDto.getQuantity()) { //배송해야하는데 재고량보다 작을시
                return ResponseEntity.badRequest().body("배송할 제품의 재고량이 주문량보다 적습니다.");
            }
            if (expendDto.getLocation() == 2) { //배송완료를 눌렀을시
                expendDto.setEndat(LocalDate.now()); //배송완료시간기록
                expendService.update((int) companyId, expendDto);
                return ResponseEntity.ok("배송 처리가 확인 되었습니다.");
            }
            //배송출발을 눌렀을시
            productService.countupdate(expendDto.getProduct().getId(),-expendDto.getQuantity());
            expendService.update((int) companyId, expendDto);
            return ResponseEntity.ok("배송 처리가 확인 되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("배송 처리에 실패했습니다.");

        }
    }


}
