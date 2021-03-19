package cucumber;

public enum PriceOrder {

        Дешевле("1"),
        Дороже("2");

        public String value;

        public String getValue() {
            return value;
        }

    PriceOrder(String value) { this.value = value;
        }

}
