package edu.umb.uaspbo.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.umb.uaspbo.MainApp;
import edu.umb.uaspbo.dao.*;
import edu.umb.uaspbo.dto.OrderDTO;
import edu.umb.uaspbo.dto.OrderDetailsDTO;
import edu.umb.uaspbo.entity.*;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DashboardController {

    private final OrderDAO orderDAO;
    private final ClientDAO clientDAO;
    private final SupplierDAO supplierDAO;
    private final MechanicDAO mechanicDAO;
    private final ServiceDAO serviceDAO;
    private final UserDAO userDAO;

    public DashboardController() {
        orderDAO = new OrderDAO();
        clientDAO = new ClientDAO();
        supplierDAO = new SupplierDAO();
        mechanicDAO = new MechanicDAO();
        serviceDAO = new ServiceDAO();
        userDAO = new UserDAO();
    }

    private OrderDTO selectedOrder;
    private Client selectedClient;
    private Supplier selectedSupplier;
    private Mechanic selectedMechanic;
    private Service selectedService;
    private User selectedUser;

    /*
    Sidebar Menu
     */

    @FXML
    private FontAwesomeIconView btnClient;

    @FXML
    private FontAwesomeIconView btnHome;

    @FXML
    private FontAwesomeIconView btnLogout;

    @FXML
    private FontAwesomeIconView btnMechanic;

    @FXML
    private FontAwesomeIconView btnOrder;

    @FXML
    private FontAwesomeIconView btnService;

    @FXML
    private FontAwesomeIconView btnSupplier;

    @FXML
    private FontAwesomeIconView btnUser;

    /*
    Content Pane
     */

    @FXML
    private AnchorPane pnClient;

    @FXML
    private AnchorPane pnHome;

    @FXML
    private AnchorPane pnMechanic;

    @FXML
    private AnchorPane pnOrder;

    @FXML
    private AnchorPane pnService;

    @FXML
    private AnchorPane pnSupplier;

    @FXML
    private AnchorPane pnUser;

    /*
    Home Page ( Summary Report )
     */

    @FXML
    private TableView<OrderDetailsDTO> tblReport;

    @FXML
    private TableColumn<OrderDetailsDTO, Long> colId;

    @FXML
    private TableColumn<OrderDetailsDTO, String> colOrderDate;

    @FXML
    private TableColumn<OrderDetailsDTO, String> colClientName;

    @FXML
    private TableColumn<OrderDetailsDTO, String> colClientPhone;

    @FXML
    private TableColumn<OrderDetailsDTO, Double> colPrice;

    @FXML
    private TableColumn<OrderDetailsDTO, Integer> colQty;

    @FXML
    private TableColumn<OrderDetailsDTO, String> colServiceName;

    @FXML
    private TableColumn<OrderDetailsDTO, Double> colSubTotal;

    @FXML
    private Button btnPrint;

    /*
    Order Page
     */

    @FXML
    private TableView<OrderDTO> tblOrder;

    @FXML
    private TableColumn<OrderDTO, Long> colOId;

    @FXML
    private TableColumn<OrderDTO, String> colOOrderDate;

    @FXML
    private TableColumn<OrderDTO, String> colOStatus;

    @FXML
    private TableColumn<OrderDTO, String> colOTotalAmount;

    @FXML
    private TableColumn<OrderDTO, String> colOClient;

    @FXML
    private TextField txOId;

    @FXML
    private TextField txOTotalAmount;

    @FXML
    private DatePicker dpOOrderDate;

    @FXML
    private MenuButton mbOClient;

    @FXML
    private MenuButton mbOStatus;

    @FXML
    private Button btnODelete;

    @FXML
    private Button btnONew;

    @FXML
    private Button btnOSave;

    @FXML
    private Button btnOUpdate;

    /*
    Client Page
     */

    @FXML
    private TableView<Client> tblClient;

    @FXML
    private TableColumn<Client, String> colCAddress;

    @FXML
    private TableColumn<Client, String> colCEmail;

    @FXML
    private TableColumn<Client, Long> colCId;

    @FXML
    private TableColumn<Client, String> colCName;

    @FXML
    private TableColumn<Client, String> colCPhone;

    /*
    Supplier Page
     */

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<Client, String> colSAddress;

    @FXML
    private TableColumn<Client, String> colSEmail;

    @FXML
    private TableColumn<Client, Long> colSId;

    @FXML
    private TableColumn<Client, String> colSName;

    @FXML
    private TableColumn<Client, String> colSPhone;

    /*
    Mechanic Page
     */

    @FXML
    private TableView<Mechanic> tblMechanic;

    @FXML
    private TableColumn<Client, String> colMSpecialization;

    @FXML
    private TableColumn<Client, String> colMPhone;

    @FXML
    private TableColumn<Client, Long> colMId;

    @FXML
    private TableColumn<Client, String> colMName;

    /*
    Service Page
     */

    @FXML
    private TableView<Service> tblService;

    @FXML
    private TableColumn<BigDecimal, String> colSePrice;

    @FXML
    private TableColumn<Service, String> colSeDescription;

    @FXML
    private TableColumn<Service, Long> colSeId;

    @FXML
    private TableColumn<Service, String> colSeName;

    /*
    User Page
     */

    @FXML
    private TableView<User> tblUser;

    @FXML
    private TableColumn<User, String> colURole;

    @FXML
    private TableColumn<User, Long> colUId;

    @FXML
    private TableColumn<User, String> colUUsername;

    @FXML
    private void initialize() {
        // Table Report
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colClientPhone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        // Table Order
        colOId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colOClient.setCellValueFactory(new PropertyValueFactory<>("client"));

        tblOrder.setOnMouseClicked(event -> {
            OrderDTO order = tblOrder.getSelectionModel().getSelectedItem();
            if (order != null) {
                selectedOrder = order;
            }
        });

        ObservableList<MenuItem> clientNames = FXCollections.observableArrayList(clientDAO.getAllClientNames()).stream()
                .map(name -> {
                    MenuItem item = new MenuItem(name);
                    item.setOnAction(event -> {
                        mbOClient.setText(name);
                    });
                    return item;
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));

        mbOClient.getItems().addAll(clientNames);

        ObservableList<MenuItem> statuses = FXCollections.observableArrayList("Pending", "Confirmed", "Completed", "Canceled").stream()
                .map(status -> {
                    MenuItem item = new MenuItem(status);
                    item.setOnAction(event -> {
                        mbOStatus.setText(status);
                    });
                    return item;
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));

        mbOStatus.getItems().addAll(statuses);

        // Table Client
        colCId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colCName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Table Supplier
        colSId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Table Mechanic
        colMId.setCellValueFactory(new PropertyValueFactory<>("mechanicId"));
        colMName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        // Table Service
        colSeId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colSeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Table User
        colUId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colURole.setCellValueFactory(new PropertyValueFactory<>("role"));

        refreshTable(1);
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnPrint) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Map<String, Object> parameters = new HashMap<>();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(MainApp.class.getResourceAsStream("report/order-report.jasper"), parameters, DBUtil.getConnection());
                    JasperViewer viewer = new JasperViewer(jasperPrint, false);
                    viewer.setVisible(true);
                    return null;
                }
            };

            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(task);
            service.shutdown();
        } else if (event.getSource() == btnONew) {
            enableForm(2, true, true);
            int lastOrderId = orderDAO.getLastOrderId();
            txOId.setText(String.valueOf(lastOrderId + 1));
        } else if (event.getSource() == btnOUpdate) {
            if (selectedOrder != null) {
                txOId.setText(String.valueOf(selectedOrder.getOrderId()));
                txOTotalAmount.setText(String.valueOf(selectedOrder.getTotalAmount()));
                dpOOrderDate.setValue(selectedOrder.getOrderDate());
                mbOClient.setText(selectedOrder.getClient());
                mbOStatus.setText(selectedOrder.getStatus());
                enableForm(2, true, false);
            } else {
                DialogUtil.showError("Please select an order first!");
            }
        }
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == btnClient) {
            setActiveButton(btnClient);
            pnClient.toFront();
            refreshTable(3);
        } else if (event.getSource() == btnHome) {
            setActiveButton(btnHome);
            pnHome.toFront();
            refreshTable(1);
        } else if (event.getSource() == btnMechanic) {
            setActiveButton(btnMechanic);
            pnMechanic.toFront();
            refreshTable(5);
        } else if (event.getSource() == btnOrder) {
            setActiveButton(btnOrder);
            pnOrder.toFront();
            refreshTable(2);
            enableForm(2, false, true);
        } else if (event.getSource() == btnService) {
            setActiveButton(btnService);
            pnService.toFront();
            refreshTable(6);
        } else if (event.getSource() == btnSupplier) {
            setActiveButton(btnSupplier);
            pnSupplier.toFront();
            refreshTable(4);
        } else if (event.getSource() == btnUser) {
            setActiveButton(btnUser);
            pnUser.toFront();
            refreshTable(7);
        } else if (event.getSource() == btnLogout) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
                Parent root = fxmlLoader.load();
                Scene dashboardScene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.show();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void setActiveButton(FontAwesomeIconView button) {
        btnClient.setFill(Color.web("#432e54"));
        btnHome.setFill(Color.web("#432e54"));
        btnMechanic.setFill(Color.web("#432e54"));
        btnOrder.setFill(Color.web("#432e54"));
        btnService.setFill(Color.web("#432e54"));
        btnSupplier.setFill(Color.web("#432e54"));
        btnUser.setFill(Color.web("#432e54"));

        button.setFill(Color.web("#eef5ff"));
    }

    private void refreshTable(int table) {
        switch (table) {
            case 1:
                ObservableList<OrderDetailsDTO> report = FXCollections.observableArrayList(orderDAO.getOrderDetails());
                tblReport.setItems(report);
                break;
            case 2:
                ObservableList<OrderDTO> order = FXCollections.observableArrayList(orderDAO.getOrders());
                tblOrder.setItems(order);
                break;
            case 3:
                ObservableList<Client> client = FXCollections.observableArrayList(clientDAO.getAllClients());
                tblClient.setItems(client);
                break;
            case 4:
                ObservableList<Supplier> supplier = FXCollections.observableArrayList(supplierDAO.getAllSuppliers());
                tblSupplier.setItems(supplier);
                break;
            case 5:
                ObservableList<Mechanic> mechanic = FXCollections.observableArrayList(mechanicDAO.getAllMechanics());
                tblMechanic.setItems(mechanic);
                break;
            case 6:
                ObservableList<Service> service = FXCollections.observableArrayList(serviceDAO.getAllServices());
                tblService.setItems(service);
                break;
            case 7:
                ObservableList<User> user = FXCollections.observableArrayList(userDAO.getAllUsers());
                tblUser.setItems(user);
                break;
        }
    }

    private void enableForm(int page, boolean enable, boolean clear) {
        switch (page) {
            case 2:
                txOTotalAmount.setDisable(!enable);
                dpOOrderDate.setDisable(!enable);
                mbOClient.setDisable(!enable);
                mbOStatus.setDisable(!enable);
                btnOSave.setDisable(!enable);

                if (clear) {
                    txOId.clear();
                    txOTotalAmount.clear();
                    dpOOrderDate.setValue(null);
                    mbOClient.setText("");
                    mbOStatus.setText("");
                }
                break;
        }
    }

}
