package com.ezo.defi.calculfacile.service.implementation;

import com.ezo.defi.calculfacile.domain.Calculation;
import com.ezo.defi.calculfacile.domain.Result;
import com.ezo.defi.calculfacile.model.CalculationRequest;
import com.ezo.defi.calculfacile.model.CheckCalculation;
import com.ezo.defi.calculfacile.model.ResponseToCalculation;
import com.ezo.defi.calculfacile.model.ResultOfCalculation;
import com.ezo.defi.calculfacile.repository.CalculRepository;
import com.ezo.defi.calculfacile.repository.ResultRepository;
import com.ezo.defi.calculfacile.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class DefaultCalculService implements CalculService {


        @Autowired
        private CalculRepository calculRepository;

        @Autowired
        private ResultRepository resultRepository;

        @Override
        public CalculationRequest createACalculation(String remoteAddr){

                CalculationRequest calculationRequest = new CalculationRequest();
                StringBuilder expressionToResolve = new StringBuilder();

                boolean isNumber = true;
                for(int i = 0; i < 13;i++){
                        expressionToResolve.append((isNumber) ? getDecimal() : getNumerator());
                        isNumber = !isNumber;
                }

                calculationRequest.setExpressionToResolve(expressionToResolve.toString());
                calculationRequest.setSolution(resolveExpression(expressionToResolve.toString()));

               Calculation calculation = getCalculationFromCalculationRequest(calculationRequest,remoteAddr);

                calculRepository.save(calculation);

                return calculationRequest;

        }

        @Override
        public CheckCalculation checkResponse(ResponseToCalculation responseToCalculation, String remoteAddr) {

                Calculation calculation = calculRepository.findByUuid(responseToCalculation.getId());

                if(calculation == null || calculation.getTimeToSolve() != 0)
                       throw new RuntimeException("Le calcul a déjà été fait");

                CheckCalculation checkCalculation = new CheckCalculation();

                boolean areResponseTheSame = getDoubleFormatted(Double.valueOf(responseToCalculation.getSolution())).equals(calculation.getResolution());
                checkCalculation.setResultOfCalculation(areResponseTheSame ? ResultOfCalculation.SUCCESS : ResultOfCalculation.FAIL);

                if(checkCalculation.getResultOfCalculation() == ResultOfCalculation.FAIL)
                        throw new RuntimeException("La réponse est fausse.");

                long timeResolution = new Date().getTime() - calculation.getDateTime().getTime() ;
                checkCalculation.setTimeToResolve(timeResolution + "ms");
                calculation.setTimeToSolve(timeResolution);

                Result result = resultRepository.findResultByIp(remoteAddr);

                if(result == null){
                        result = new Result();
                }

                if(result.getResponseTime() == 0 || result.getResponseTime() > timeResolution){
                        result.setFreeText(responseToCalculation.getFreeText());
                        result.setResponseTime(timeResolution);
                        result.setIp(remoteAddr);
                }

                resultRepository.save(result);
                calculRepository.save(calculation);
                calculRepository.deleteAll(calculRepository.findByIp(remoteAddr));

                return checkCalculation;
        }

        @Override
        public String getAllResult() {

                List<Result> results = resultRepository.findAllByOrderByResponseTimeAsc()
                                .stream().filter(x -> !StringUtils.isEmpty(x.getFreeText())).collect(Collectors.toList());

                StringBuilder responseText = new StringBuilder();
                for(Result it : results){
                       responseText
                               .append( it.getFreeText())
                               .append( ": ")
                               .append(it.getResponseTime())
                               .append("\n");
                }
                return responseText.toString();
        }

        private Calculation getCalculationFromCalculationRequest(CalculationRequest calculationRequest,String remoteAddr) {
                Calculation calculation = new Calculation();
                calculation.setUuid(calculationRequest.getId());
                calculation.setDateTime(new Date());
                calculation.setResolution(calculationRequest.getSolution());
                calculation.setIp(remoteAddr);
                return calculation;
        }

        private String getNumerator(){
                int randomNum = ThreadLocalRandom.current().nextInt(0, 5);

                switch (randomNum){
                        case 0: return "/";
                        case 1: return "*";
                        case 2: return "+";
                        default: return "-";
                }

        }

        private String getDecimal(){
                double randomNum = ThreadLocalRandom.current().nextDouble(0, 1000);
                return  getDoubleFormatted(randomNum);
        }

        private String getDoubleFormatted(double numberNonFormatted){
                return  new DecimalFormat("#.######").format(numberNonFormatted);
        }

        private String resolveExpression(String expression){
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");

                try{
                        Double solution = Double.valueOf(engine.eval(expression).toString());
                        return getDoubleFormatted(solution);
                }catch(Exception e){
                        throw new RuntimeException("Impossible de créer un calcul valide : " + expression);
                }
        }

}
