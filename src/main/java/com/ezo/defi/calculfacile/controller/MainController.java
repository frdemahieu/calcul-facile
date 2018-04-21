package com.ezo.defi.calculfacile.controller;

import com.ezo.defi.calculfacile.model.CalculationRequest;
import com.ezo.defi.calculfacile.model.CheckCalculation;
import com.ezo.defi.calculfacile.model.ResponseToCalculation;
import com.ezo.defi.calculfacile.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.HtmlEscapeTag;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api")
public class MainController {

        @Autowired
        CalculService calculService;

        @RequestMapping(method = RequestMethod.GET, value = "/results")
        public String getResult(){
                return HtmlUtils.htmlEscape(calculService.getAllResult());
        }


        @RequestMapping(method = RequestMethod.POST, value = "/level01")
        public CheckCalculation response(@RequestBody ResponseToCalculation responseToCalculation, HttpServletRequest request){

                return calculService.checkResponse(responseToCalculation,request.getRemoteAddr());
        }

        @RequestMapping(method = RequestMethod.GET, value = "/level01")
        public CalculationRequest question(HttpServletRequest request){
                return calculService.createACalculation(request.getRemoteAddr());
        }

}
