package com.ezo.defi.calculfacile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class CalculationRequest {

        private UUID id = UUID.randomUUID();
        private String expressionToResolve;
        private String solution;

        public UUID getId() {
                return id;
        }

        public String getExpressionToResolve() {
                return expressionToResolve;
        }

        public void setExpressionToResolve(String expressionToResolve) {
                this.expressionToResolve = expressionToResolve;
        }

        @JsonIgnore
        public String getSolution() {
                return solution;
        }

        public void setSolution(String solution) {
                this.solution = solution;
        }
}
