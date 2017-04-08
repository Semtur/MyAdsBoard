package MyAdsBoard.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> rubricList;

    public Category(){}
    public Category(String name) {
        this.name = name;
    }
    public Category(String name, List<String> rubricList) {
        this.name = name;
        this.rubricList = rubricList;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getRubricList() {
        return rubricList;
    }
    public void updateRubrics(String[] rubrics) {
        for (String rubric : rubrics) {
            if (rubric.startsWith("-")) {
                String str = rubric.substring(1);
                rubricList.remove(str);
            } else if (!rubricList.contains(rubric))
                rubricList.add(rubric);
        }
    }
    public String rubricsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String rubric : rubricList)
            stringBuilder.append(rubric).append("; ");
        int lenSb = stringBuilder.length();
        if (lenSb > 0) {
            stringBuilder.delete(lenSb - 2, lenSb);
        }
        return stringBuilder.toString();
    }
}
