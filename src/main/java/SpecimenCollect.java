import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SpecimenCollect {


    static class Specimen {

        private Integer id;
        private Species species;

        private Double weight;
        private Double length;
        private Integer numberOfWorkingFlippers;

        private LocalDateTime localDateTime;
        private String location;

        public Specimen() {
        }

        public Specimen( Species species, Double weight, Double length, Integer numberOfWorkingFlippers, String location) {

            this.species = species;
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

        public Species getSpecies() {
            return species;
        }

        public void setSpecies(Species species) {
            this.species = species;
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
            return "Specimen{" +
                    "id=" + id +
                    ", species=" + species +
                    ", weight=" + weight +
                    ", length=" + length +
                    ", numberOfWorkingFlippers=" + numberOfWorkingFlippers +
                    ", localDateTime=" + localDateTime +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    enum Species {
        Leatherback("1"), Loggerhead("2"), Green("2"), Flatback("3"), Hawksbill("4"), Kemp_ridley("5"), Olive_ridley("6");

        private final String abbreviation;

        Species(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public static Species findByAbbr(final String abbr) {
            return Arrays.stream(values()).filter(value -> value.abbreviation.equals(abbr)).findFirst().orElse(null);
        }

    }

    static List<Specimen> specimenArrayList = new ArrayList<>();
    static Integer counter = 0;

    public static void main(String[] args) {
        while (true) {
            boolean isWrongAnswer = false;

            System.out.println("Text-based System");
            System.out.println("Please choose action from menu");
            System.out.println("1.enter a new record ");
            System.out.println("2.view an existing record ");
            System.out.println("3.edit an existing record");
            System.out.println("4.delete an existing record ");
            System.out.println("5.view all records ");
            System.out.println("6.generate report bt date and location ");
            System.out.println("7.search on Specimen by species");

            Scanner sc = new Scanner(System.in);

            do {


                switch (sc.nextInt()) {

                    case 1:
                        addNewRecord();
                        break;
                    case 2:
                        viewRecord();
                        break;

                    case 3:
                        editRecord();
                        break;

                    case 4:
                        delete();
                        break;

                    case 5:
                        viewAll();
                        break;
                    case 6:
                        searchBySpecies();
                        break;
                    case 7:
                        generateSummaryReport();
                        break;

                    default:
                        System.out.println("choose from 1 to 7");
                        isWrongAnswer = true;


                }

            } while (isWrongAnswer);

        }

    }


    public static Integer getId() {
        return ++counter;
    }


    public static Specimen addNewRecord() {

        Specimen specimen = instantiateRecord();
        specimen.setId(getId());
        specimenArrayList.add(specimen);
        return specimen;
    }

    public static void viewRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter specimen id");
        Integer id = sc.nextInt();
        Specimen specimen = specimenArrayList.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        System.out.println(specimen);

    }

    public static void editRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter specimen id");
        Integer id = sc.nextInt();
        Optional<Specimen> specimen = specimenArrayList.stream().filter(s -> s.getId().equals(id)).findFirst();
        if (specimen.isPresent()) {

            Specimen newEditedRecordDTO = instantiateRecord();
            toRecord(specimen.get(), newEditedRecordDTO);

        }


    }

    public static Specimen instantiateRecord() {

        Scanner sc = new Scanner(System.in);
        Species species = getSpeciesByAbb();
        System.out.println("Please Enter Wight in kg");
        Double wight = sc.nextDouble();
        System.out.println("Please Enter Length in cm");
        Double length = sc.nextDouble();
        System.out.println("Please Enter Number of working flippers (2, 1 or 0)");
        Integer numbers = sc.nextInt();
        System.out.println("Please Enter location (for example, Santubong, Sematan or Telaga Air)");
        String location = sc.next();

        Specimen specimen = new Specimen( species, wight, length, numbers, location);
        return specimen;
    }


    public static void toRecord(Specimen s1, Specimen s2) {


        s1.setLength(s2.getLength());
        s1.setSpecies(s2.getSpecies());
        s1.setLocation(s2.getLocation());
        s1.setWeight(s2.getWeight());
        s1.setLocalDateTime(s2.getLocalDateTime());
        s1.setNumberOfWorkingFlippers(s2.getNumberOfWorkingFlippers());

    }


    public static void viewAll() {
        System.out.printf("%10s %20s ", "ID", "SPECIES \n");
        specimenArrayList.forEach(s ->
                System.out.format("%10s %20s \n ", s.getId(), s.getSpecies())
        );


    }

    public static void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter specimen id");
        Integer id = sc.nextInt();
        Optional<Specimen> specimen = specimenArrayList.stream().filter(s -> s.getId().equals(id)).findFirst();
        specimen.ifPresent(value -> specimenArrayList.remove(value));


    }


    public static void searchBySpecies() {

        System.out.printf("%10s %20s ", "ID", "SPECIES \n");
        specimenArrayList.stream().filter(s->s.getSpecies().equals(getSpeciesByAbb())).collect(Collectors.toList()).forEach(s ->
                System.out.format("%10s %20s \n ", s.getId(), s.getSpecies())
        );


    }


    public static void generateSummaryReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter location (for example, Santubong, Sematan or Telaga Air)");
        String location = sc.next();
        System.out.println("Please Enter First date");
        String date1 = sc.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        System.out.println("Please Enter Second date");
        String date2 = sc.next();
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);




        System.out.printf("%10s %20s ", "ID", "SPECIES \n");
        specimenArrayList.stream().filter(s->s.getLocation().equals(location)&&s.localDateTime.isAfter(dateTime1)
                &&s.localDateTime.isBefore(dateTime2)).collect(Collectors.toList()).forEach(s ->
                System.out.format("%10s %20s \n ", s.getId(), s.getSpecies())
        );


    }


    public static Species getSpeciesByAbb(){

        System.out.println("Please Choose Species Type");
        System.out.println("1.Leatherback ");
        System.out.println("2.Loggerhead ");
        System.out.println("3.Green");
        System.out.println("4.Flatback");
        System.out.println("5.Hawksbil");
        System.out.println("6.Kemp’s ridley");
        System.out.println("7.Hawksbil");
        System.out.println("7.Olive ridley");
        Scanner sc = new Scanner(System.in);
        String abb = sc.next();

        return Species.findByAbbr(abb);


    }





}
