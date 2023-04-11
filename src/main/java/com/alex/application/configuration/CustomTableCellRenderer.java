package com.alex.application.configuration;

import com.alex.application.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private int selectedMonth;
    private List<Task> taskList;
    public CustomTableCellRenderer(int selectedMonth, List<Task> taskList) {
        this.selectedMonth =selectedMonth;
        this.taskList = taskList;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Task task = taskList.get(row);
        LocalDate dateNow = LocalDate.of(2023, selectedMonth, column);
        // Check if the cell is in the first column
        if (dateNow.isBefore(task.getDeadlineDate()) && dateNow.isAfter(task.getStartingDate()) ||
        dateNow.isEqual(task.getStartingDate()) || dateNow.isEqual(task.getDeadlineDate())) {
            cellComponent.setBackground(Color.LIGHT_GRAY);
        }else {
            cellComponent.setBackground(Color.WHITE);
        }

        return cellComponent;
    }
}
