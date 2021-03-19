package cucumber;

public enum CategoryItem {
    оргтехника("99"),
    ноутбуки("98"),
    фототехника("105");

    public String value;

    public String getValue() {
        return value;
    }

   CategoryItem(String value) { this.value = value;
    }
}
