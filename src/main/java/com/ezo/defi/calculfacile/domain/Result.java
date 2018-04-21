package com.ezo.defi.calculfacile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Result {

        @Id
        private UUID uuid = UUID.randomUUID();
        private long responseTime;
        private String freeText;
        private String ip;

        public UUID getUuid() {
                return uuid;
        }

        public void setUuid(UUID uuid) {
                this.uuid = uuid;
        }

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public long getResponseTime() {
                return responseTime;
        }

        public void setResponseTime(long responseTime) {
                this.responseTime = responseTime;
        }

        public String getFreeText() {
                return freeText;
        }

        public void setFreeText(String freeText) {
                this.freeText = freeText;
        }
}
