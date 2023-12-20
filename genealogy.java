
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

interface Person {
    String getDetails();
    boolean isMarried();
    List<Person> getChildren();
}

/*
 * cmt.
 */
class Individual implements Person {
    private String name;
    private LocalDate birthDate;
    private String gender;
    private boolean married;
    private List<Person> children;

    public Individual(String name, String birthDate, String gender, boolean married) {
        this.name = name;
        this.birthDate = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.gender = gender;
        this.married = married;
        this.children = new ArrayList<>();
    }

    @Override
    public String getDetails() {
        return String.format("Name: %s, Birth Date: %s, Gender: %s, Married: %s",
                name, birthDate, gender, married);
    }

    @Override
    public boolean isMarried() {
        return married;
    }

    @Override
    public List<Person> getChildren() {
        return children;
    }

    public void addChild(Person child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return getDetails();
    }

    public static void findUnmarried(Individual individual) {
        if (!individual.isMarried()) {
            System.out.println(individual.getDetails());
        }
    }

    public static void findTwoChildCouples(Family family) {
        List<Person> members = family.getMembers();
        if (members.size() == 2 && members.get(0) instanceof Individual && members.get(1) instanceof Individual) {
            System.out.println("Couple with two children:\n" + members.get(0) + "\n" + members.get(1));
        }
    }
}

/*
 * cmt.
 */
class Family implements Person {
    private List<Person> memberList = new ArrayList<>();

    public void addMember(Person person) {
        memberList.add(person);
    }

    public List<Person> getMembers() {
        return memberList;
    }

    @Override
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        for (Person member : memberList) {
            details.append(member.getDetails()).append("\n");
        }
        return details.toString();
    }

    @Override
    public boolean isMarried() {
        return memberList.size() > 1;
    }

    @Override
    public List<Person> getChildren() {
        List<Person> children = new ArrayList<>();
        for (Person member : memberList) {
            children.addAll(member.getChildren());
        }
        return children;
    }

    @Override
    public String toString() {
        return getDetails();
    }

    public static void findLatestGeneration(Family family, int generation) {
        List<Person> members = family.getMembers();
        if (!members.isEmpty()) {
            System.out.println("Generation " + generation + ":\n" + family);
            for (Person member : members) {
                findLatestGeneration((Family) member, generation + 1);
            }
        }
    }
}

/*
 * cmt.
 */
public class Genealogy {
    public static void main(String[] args) {
        Individual john = new Individual("John", "01/01/1950", "Male", true);
        Individual mary = new Individual("Mary", "02/02/1955", "Female", true);
        Individual alice = new Individual("Alice", "03/03/1975", "Female", true);
        Individual bob = new Individual("Bob", "04/04/1978", "Male", true);
        Individual carol = new Individual("Carol", "05/05/1980", "Female", true);

        Family johnFamily = new Family();
        johnFamily.addMember(john);
        johnFamily.addMember(mary);

        Family aliceFamily = new Family();
        aliceFamily.addMember(alice);
        aliceFamily.addMember(bob);

        Family carolFamily = new Family();
        carolFamily.addMember(carol);

        johnFamily.addMember(aliceFamily);
        aliceFamily.addMember(carolFamily);

        System.out.println("Genealogy Tree:\n" + johnFamily.getDetails());

        System.out.println("\nUnmarried individuals:");
        Individual.findUnmarried(john);

        System.out.println("\nCouples with two children:");
        Family.findTwoChildCouples(johnFamily);

        System.out.println("\nLatest generations:");
        Family.findLatestGeneration(johnFamily, 1);
    }
}
