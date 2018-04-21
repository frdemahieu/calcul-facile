package com.ezo.defi.calculfacile.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Calculation {

        @Id
        private UUID uuid = UUID.randomUUID();
        private Date dateTime;
        private String resolution;
        private long timeToSolve;
        private String ip;

        public UUID getUuid() {
                return uuid;
        }

        public void setUuid(UUID uuid) {
                this.uuid = uuid;
        }

        public Date getDateTime() {
                return dateTime;
        }

        public void setDateTime(Date dateTime) {
                this.dateTime = dateTime;
        }

        public String getResolution() {
                return resolution;
        }

        public void setResolution(String resolution) {
                this.resolution = resolution;
        }

        public long getTimeToSolve() {
                return timeToSolve;
        }

        public void setTimeToSolve(long timeToSolve) {
                this.timeToSolve = timeToSolve;
        }

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }
}
