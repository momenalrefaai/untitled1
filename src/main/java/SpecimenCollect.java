import Types.*;
import parent.Specimen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SpecimenCollect {

    static List<Specimen> specimenArrayList = new ArrayList<>();
    static Integer counter = 0;

    public static void main(String[] args) {
        connect();
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
            System.out.println("7.search on parent.Specimen by species");

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

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/JTP.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
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
        String type = getSpeciesByAbb();
        System.out.println("Please Enter Wight in kg");
        Double wight = sc.nextDouble();
        System.out.println("Please Enter Length in cm");
        Double length = sc.nextDouble();
        System.out.println("Please Enter Number of working flippers (2, 1 or 0)");
        Integer numbers = sc.nextInt();
        System.out.println("Please Enter location (for example, Santubong, Sematan or Telaga Air)");
        String location = sc.next();
        Specimen specimen;
        switch (type){
            case "Leatherback":
                specimen = new Leatherback(wight, length, numbers, location);
                break;

            case "Loggerhead":
                specimen = new Loggerhead(wight, length, numbers, location);
                break;

            case "Green":
                specimen = new Green(wight, length, numbers, location);
                break;

            case "Flatback":
                specimen = new Flatback(wight, length, numbers, location);
                break;

            case "Hawksbil":
                specimen = new Hawksbil(wight, length, numbers, location);
                break;

            case "KempsRidley":
                specimen = new KempsRidley(wight, length, numbers, location);
                break;

            case "OlivRidley":
                specimen = new OlivRidley(wight, length, numbers, location);
                break;

            default:
                specimen = new Specimen("no type",wight, length, numbers, location);
        }
        return specimen;
    }


    public static void toRecord(Specimen s1, Specimen s2) {


        s1.setLength(s2.getLength());
        s1.setType(s2.getType());
        s1.setLocation(s2.getLocation());
        s1.setWeight(s2.getWeight());
        s1.setLocalDateTime(s2.getLocalDateTime());
        s1.setNumberOfWorkingFlippers(s2.getNumberOfWorkingFlippers());

    }


    public static void viewAll() {
        System.out.printf("%10s %20s ", "ID", "SPECIES \n");
        specimenArrayList.forEach(s ->
                System.out.format("%10s %20s \n ", s.getId(), s.getType())
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
        specimenArrayList.stream().filter(s->s.getType().equals(getSpeciesByAbb())).collect(Collectors.toList()).forEach(s ->
                System.out.format("%10s %20s \n ", s.getId(), s.getType())
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
                System.out.format("%10s %20s \n ", s.getId(), s.getType())
        );


    }


    public static String getSpeciesByAbb(){

        System.out.println("Please Choose Species Type");
        System.out.println("1.Leatherback ");
        System.out.println("2.Loggerhead ");
        System.out.println("3.Green");
        System.out.println("4.Flatback");
        System.out.println("5.Hawksbil");
        System.out.println("6.KempsRidley");
        System.out.println("7.OlivRidley");
        Scanner sc = new Scanner(System.in);
        String abb = sc.next();

        return abb;


    }





}
