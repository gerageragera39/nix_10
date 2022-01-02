package ua.com.alevel.view.dto.request;

public class CategoryRequestDto extends RequestDto {

    private String name;
    private boolean finances;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFinances() {
        return finances;
    }

    public void setFinances(boolean finances) {
        this.finances = finances;
    }
}
