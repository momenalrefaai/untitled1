package parent;

import java.time.LocalDateTime;

public class Specimen {

        private Integer id;
        private String type;

        private Double weight;
        private Double length;
        private Integer numberOfWorkingFlippers;

        public LocalDateTime localDateTime;
        private String location;

        public Specimen() {
    }

        public Specimen(String type, Double weight, Double length, Integer numberOfWorkingFlippers, String location) {

        this.type = type;
        this.weight = weight;
        this.length = length;
        this.numberOfWorkingFlippers = numberOfWorkingFlippers;
        this.localDateTime = LocalDateTime.now();
        this.location = location;
    }

        public Integer getId() {
        return id;
    }

        public void setId(Integer id) {
        this.id = id;
    }

        public String getType() {
        return type;
    }

        public void setType(String type) {
        this.type = type;
    }

        public Double getWeight() {
        return weight;
    }

        public void setWeight(Double weight) {
        this.weight = weight;
    }

        public Double getLength() {
        return length;
    }

        public void setLength(Double length) {
        this.length = length;
    }

        public Integer getNumberOfWorkingFlippers() {
        return numberOfWorkingFlippers;
    }

        public void setNumberOfWorkingFlippers(Integer numberOfWorkingFlippers) {
        this.numberOfWorkingFlippers = numberOfWorkingFlippers;
    }

        public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

        public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

        public String getLocation() {
        return location;
    }

        public void setLocation(String location) {
        this.location = location;
    }


        @Override
        public String toString() {
        return "parent.Specimen{" +
                "id=" + id +
                ", type=" + type +
                ", weight=" + weight +
                ", length=" + length +
                ", numberOfWorkingFlippers=" + numberOfWorkingFlippers +
                ", localDateTime=" + localDateTime +
                ", location='" + location + '\'' +
                '}';
    }
}
