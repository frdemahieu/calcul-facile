package com.ezo.defi.calculfacile.model;

import java.util.UUID;

public class ResponseToCalculation {

        private UUID id;
        private String solution;
        private String freeText;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getSolution() {
                return solution;
        }

        public void setSolution(String solution) {
                this.solution = solution;
        }

        public String getFreeText() {
                return freeText;
        }

        public void setFreeText(String freeText) {
                this.freeText = freeText;
        }
}
