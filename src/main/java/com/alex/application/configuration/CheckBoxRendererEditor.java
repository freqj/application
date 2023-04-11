package com.alex.application.configuration;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CheckBoxRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JCheckBox checkBox = new JCheckBox();

    public CheckBoxRendererEditor() {
        checkBox.setHorizontalAlignment(JCheckBox.CENTER);
    }

    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        checkBox.setSelected((Boolean) value);
        return checkBox;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        checkBox.setSelected((Boolean) value);
        return checkBox;
    }
}