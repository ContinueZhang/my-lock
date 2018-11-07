package com.example.mylock.controller;

import com.example.mylock.entity.ICBCDetailEntity;
import com.example.mylock.entity.ICBCEntity;
import com.example.mylock.entity.UserEntity;
import com.example.mylock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author continue
 * @date 2018/11/7
 */
@Controller
@RequestMapping("/")
public class LockController {

    @Autowired
    private LockService lockService;

    @RequestMapping("/")
    public String view(Model model) {
        List<UserEntity> userList = lockService.selectUserAll();
        List<ICBCEntity> icbcList = lockService.selectIcbcAll();
        List<ICBCDetailEntity> icbcDetailList = lockService.selectIcbcDetailAll();
        model.addAttribute("userList", userList);
        model.addAttribute("icbcList", icbcList);
        model.addAttribute("icbcDetailList", icbcDetailList);
        return "home";
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public String consume(Model model,
                          @RequestParam("amount") double amount,
                          @RequestParam("icbcId") int icbcId,
                          @RequestParam("targetIcbcId") int targerIcbcId) {
        String message = "消费成功";
        try {
            lockService.consume(amount, icbcId, targerIcbcId);
        } catch (Exception e) {
            message = e.getMessage();
        }

        model.addAttribute("resultMessage", message);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(Model model) {

        List<ICBCEntity> icbcList = lockService.selectIcbcAll();
        model.addAttribute("icbcList", icbcList);
        return "form";
    }

    @GetMapping("/user")
    public String user() {

        return "addUser";
    }

    @GetMapping("/icbc")
    public String icbc(Model model) {

        List<UserEntity> userList = lockService.selectUserAll();
        model.addAttribute("userList", userList);
        return "addIcbc";
    }


    @PostMapping("/addUser")
    public String addUser(UserEntity user) {

        lockService.addUser(user);
        return "redirect:/";
    }

    @PostMapping("/addIcbc")
    public String addIcbc(ICBCEntity icbc) {

        lockService.addIcbc(icbc);
        return "redirect:/";
    }

}
