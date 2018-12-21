package sample.Server;

import sample.Employees.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseThread implements Runnable {

    private Socket socket;
    private ArrayList<Employee> list = new ArrayList<Employee>();
    ServerController controller;

    public ParseThread(Socket socket, ServerController controller) {

        this.socket = socket;
        this.controller = controller;
    }

    public ArrayList<Employee> getList() {
        return list;
    }

    public void create(String line) {

        System.out.println("Работает FactoryThread");

        int index = 0;
        Scanner scanner = new Scanner(line);

        scanner.useDelimiter(";");

        Employee employee = null;
        EmployeeType type = null;

        while (scanner.hasNext()) {
            String data = scanner.next();

            switch (index) {

                case 0:

                    if (data.equals("staffer")) {
                        employee = new Staffer();
                        type = EmployeeType.staffer;


                    } else {
                        employee = new Worker();
                        type = EmployeeType.worker;
                    }

                    employee.setType(type);
                    break;

                case 1:

                    employee.setFamily(data);
                    break;

                case 2:

                    try {

                        employee.setAge(Integer.parseInt(data));

                    } catch (NumberFormatException e) {

                        employee.setAge(-1);
                        //log.info(e.getMessage());
                    }

                    break;

                case 3:

                    try {
                        employee.setExperience(Double.parseDouble(data));

                    } catch (NumberFormatException e) {

                        employee.setExperience(-1.0);
                        //log.info(e.getMessage());
                    }
                    break;

                case 4:

                    switch (type) {

                        case worker:

                            ((Worker) employee).setSalary(Double.parseDouble(data));
                            break;

                        case staffer:

                            ((Staffer) employee).setPrize(Integer.parseInt(data));
                            break;
                    }
                    break;
            }
            index++;
        }
        list.add(employee);
        System.out.println("\n Запись " + line + " добавлена в список. \n");

    }


    @Override
    public void run() {

        try {

            Scanner scanner = new Scanner(socket.getInputStream());

            while (scanner.hasNextLine()) {

                String str = scanner.nextLine();
                System.out.println("Получена строка: " + str);

                create(str);
            }

            controller.showTable(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}