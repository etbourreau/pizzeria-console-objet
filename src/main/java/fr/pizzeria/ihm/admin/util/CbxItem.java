package fr.pizzeria.ihm.admin.util;

public class CbxItem {
	private String value;
    private String label;

    /**Custom ComboBox Item
     * @param Generally object's ID
     * @param Object's label to show in combobox
     */
    public CbxItem(String value, String label) {
        this.setValue(value);
        this.setLabel(label);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return getLabel();
    }  
}
