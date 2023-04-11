package com.alex.application;

import com.alex.application.model.Employee;
import com.alex.application.model.Task;
import com.alex.application.configuration.CheckBoxRendererEditor;
import com.alex.application.configuration.CustomTableCellRenderer;
import com.alex.application.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;

@org.springframework.stereotype.Component
public class Application extends JFrame {
	private static final Logger logger = LogManager.getLogger(Application.class);
	private JComboBox<String> comboBox;
	private EmployeeService service;
	private JComboBox<String> monthComboBox;
	private JTable monthDaysTable;
	private JTable taskTable;
	public Application(EmployeeService service){
		this.service = service;
		init();
	}

	private JPanel planTabInit() {
		logger.info("Initializing plan tab...");
		JPanel planTab = new JPanel(new BorderLayout());
		JPanel topPlanTabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
		monthComboBox = new JComboBox<String>();
		monthComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
				"Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" }));
		monthComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Month selected: " + monthComboBox.getSelectedItem());
				updatePlanTable();
			}
		});
		topPlanTabPanel.add(new JLabel("Месяц:"));
		topPlanTabPanel.add(monthComboBox);
		planTab.add(topPlanTabPanel, BorderLayout.NORTH);
		monthDaysTable = new JTable(new Object[][] { {} }, new Object[] {});
		updatePlanTable();
		JScrollPane scrollPane = new JScrollPane(monthDaysTable);
		planTab.add(scrollPane, BorderLayout.CENTER);
		logger.info("Done");
		return planTab;
	}

	private JPanel taskTabInit() {
		logger.info("Initializing task tab...");
		JPanel taskTab = new JPanel(new BorderLayout());
		taskTable = new JTable(new Object[][] { {} }, new Object[] {});
		updateTaskTable();
		JScrollPane taskScrollPane = new JScrollPane(taskTable);
		taskTab.add(taskScrollPane);
		logger.info("Done");
		return taskTab;
	}

	private JPanel topPanelInit() {
		logger.info("Initializing top panel");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
		comboBox = new JComboBox<String>();
		for (Employee e :service.getEmployeeList()){
			comboBox.addItem(e.getName());
		}
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePlanTable();
				updateTaskTable();
			}
		});
		panel.add(new JLabel("Сотрудник:"));
		panel.add(comboBox);
		logger.info("Done");
		return panel;
	}

	private void updateTaskTable(){
		logger.info("Updating task table...");
		Employee employee = service.getEmployeeByName((String) comboBox.getSelectedItem());
		List<Task> list = employee.getTaskList();
		Object[] columnNames = new Object[5];
		Object[][] data = new Object[list.size()][5];
		columnNames[0] = new String("Выполнена");
		columnNames[1] = new String("Задача");
		columnNames[2] = new String("Дата начала");
		columnNames[3] = new String("Дата окончания");
		columnNames[4] = new String("Дата выполнения");
		for(int i = 0; i < list.size(); i++)
		{
			Task task = list.get(i);
			data[i][0] = task.isCompleted();
			data[i][1] = task.getName();
			data[i][2] = task.getStartingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			data[i][3] = task.getDeadlineDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			data[i][4] = task.getCompletionDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		}
		taskTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
		List<TableColumn> columns = new ArrayList<>();
		TableColumnModel columnModel = taskTable.getColumnModel();
		columns.addAll(Arrays.asList(columnModel.getColumn(1),
				columnModel.getColumn(2),
				columnModel.getColumn(3),
				columnModel.getColumn(4)));
		for(TableColumn tc: columns)
		{
			tc.setPreferredWidth(2 * (taskTable.getColumnModel().getColumnMargin() + taskTable.getColumnModel().getColumn(1).getWidth()));
		}
		TableColumn booleanColumn = taskTable.getColumnModel().getColumn(0);
		booleanColumn.setCellRenderer(new CheckBoxRendererEditor());
		booleanColumn.setCellEditor(new CheckBoxRendererEditor());
		logger.info("Done");
	}
	private void updatePlanTable() {
		logger.info("Updating plan table...");
		int selectedMonthIndex = monthComboBox.getSelectedIndex();
		int year = 2023;
		int numDays = YearMonth.of(year, selectedMonthIndex + 1).lengthOfMonth();
		Employee employee = service.getEmployeeByName((String) comboBox.getSelectedItem());
		List<Task> list = employee.getTaskList();
		List<Task> thisMonthTasks = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
			Task task = list.get(i);
			if (task.getStartingDate().getMonthValue() == monthComboBox.getSelectedIndex()+1 ||
					task.getDeadlineDate().getMonthValue() == monthComboBox.getSelectedIndex() + 1 ||
			task.getStartingDate().getMonthValue() < monthComboBox.getSelectedIndex() + 1
					&& task.getDeadlineDate().getMonthValue() > monthComboBox.getSelectedIndex() + 1)
			{
				thisMonthTasks.add(task);
			}
		}
		Object[] columnNames = new Object[numDays+1];
		Object[][] data = new Object[thisMonthTasks.size()][numDays+1];
		if(!thisMonthTasks.isEmpty())
		{
			for(int i = 0; i < thisMonthTasks.size(); i++)
			{
					data[i][0] = thisMonthTasks.get(i).getName();
			}
		}
		columnNames[0] = "Task";
		for (int i = 1; i <= numDays; i++) {
			columnNames[i] = Integer.toString(i);
		}
		monthDaysTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
		for (int i = 1; i <= numDays; i++)
		{
			TableColumn column = monthDaysTable.getColumnModel().getColumn(i);
			column.setCellRenderer(new CustomTableCellRenderer(monthComboBox.getSelectedIndex()+1, thisMonthTasks));

		}
		TableColumnModel columnModel = monthDaysTable.getColumnModel();
		TableColumn firstColumn = columnModel.getColumn(0);
		firstColumn.setPreferredWidth(6 * (monthDaysTable.getColumnModel().getColumnMargin() + monthDaysTable.getColumnModel().getColumn(1).getWidth()));
		logger.info("Done");
	}
	private void init(){
		setTitle("My Browser");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = topPanelInit();
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel taskTab = taskTabInit();
		JPanel planTab = planTabInit();
		tabbedPane.addTab("Задачи", taskTab);
		tabbedPane.addTab("План", planTab);
		add(panel, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
		setVisible(true);
	}
}
