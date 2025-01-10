package edu.umb.uaspbo.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.umb.uaspbo.MainApp;
import edu.umb.uaspbo.dto.OrderDTO;
import edu.umb.uaspbo.dto.OrderDetailsDTO;
import edu.umb.uaspbo.entity.*;
import edu.umb.uaspbo.repository.*;
import edu.umb.uaspbo.repository.impl.*;
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
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DashboardController {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ClientRepository clientRepository;
    private final SupplierRepository supplierRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public DashboardController() {
        final Connection connection = DBUtil.getConnection();
        this.orderRepository = new OrderRepositoryImpl(connection);
        this.orderDetailRepository = new OrderDetailRepositoryImpl(connection);
        this.clientRepository = new ClientRepositoryImpl(connection);
        this.supplierRepository = new SupplierRepositoryImpl(connection);
        this.mechanicRepository = new MechanicRepositoryImpl(connection);
        this.serviceRepository = new ServiceRepositoryImpl(connection);
        this.userRepository = new UserRepositoryImpl(connection);
    }

    public void setUser(User user) {
        if (!user.getRole().equals("admin")) {
            btnUser.setVisible(false);
            AnchorPane.setBottomAnchor(btnLogout, 55.0);
        }
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

    @FXML
    private Button btnCDelete;

    @FXML
    private Button btnCNew;

    @FXML
    private Button btnCSave;

    @FXML
    private Button btnCUpdate;

    @FXML
    private TextField txCAddress;

    @FXML
    private TextField txCEmail;

    @FXML
    private TextField txCId;

    @FXML
    private TextField txCName;

    @FXML
    private TextField txCPhone;

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

    @FXML
    private Button btnSDelete;

    @FXML
    private Button btnSNew;

    @FXML
    private Button btnSSave;

    @FXML
    private Button btnSUpdate;

    @FXML
    private TextField txSAddress;

    @FXML
    private TextField txSEmail;

    @FXML
    private TextField txSId;

    @FXML
    private TextField txSName;

    @FXML
    private TextField txSPhone;

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

    @FXML
    private Button btnMDelete;

    @FXML
    private Button btnMNew;

    @FXML
    private Button btnMSave;

    @FXML
    private Button btnMUpdate;

    @FXML
    private TextField txMId;

    @FXML
    private TextField txMName;

    @FXML
    private TextField txMPhone;

    @FXML
    private TextField txMSpecialization;


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

    @FXML
    private Button btnSeDelete;

    @FXML
    private Button btnSeNew;

    @FXML
    private Button btnSeSave;

    @FXML
    private Button btnSeUpdate;

    @FXML
    private TextField txSeDescription;

    @FXML
    private TextField txSeId;

    @FXML
    private TextField txSeName;

    @FXML
    private TextField txSePrice;

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
    private Button btnUDelete;

    @FXML
    private Button btnUNew;

    @FXML
    private Button btnUSave;

    @FXML
    private Button btnUUpdate;

    @FXML
    private TextField txUPassword;

    @FXML
    private TextField txUUsername;

    @FXML
    private TextField txUid;

    @FXML
    private MenuButton mbURole;

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
        colOId.setCellValueFactory(new PropertyValueFactory<>("id"));
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

        ObservableList<MenuItem> clientNames = FXCollections.observableArrayList(clientRepository.findAll()).stream()
                .map(client -> {
                    MenuItem item = new MenuItem(client.getName());
                    item.setOnAction(event -> mbOClient.setText(client.getName()));
                    return item;
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));

        mbOClient.getItems().addAll(clientNames);

        ObservableList<MenuItem> statuses = FXCollections.observableArrayList("Pending", "Confirmed", "Completed", "Canceled").stream()
                .map(status -> {
                    MenuItem item = new MenuItem(status);
                    item.setOnAction(event -> mbOStatus.setText(status));
                    return item;
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));

        mbOStatus.getItems().addAll(statuses);

        // Table Client
        colCId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblClient.setOnMouseClicked(event -> {
            Client client = tblClient.getSelectionModel().getSelectedItem();
            if (client != null) {
                selectedClient = client;
            }
        });

        // Table Supplier
        colSId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblSupplier.setOnMouseClicked(event -> {
            Supplier supplier = tblSupplier.getSelectionModel().getSelectedItem();
            if (supplier != null) {
                selectedSupplier = supplier;
            }
        });

        // Table Mechanic
        colMId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        tblMechanic.setOnMouseClicked(event -> {
            Mechanic mechanic = tblMechanic.getSelectionModel().getSelectedItem();
            if (mechanic != null) {
                selectedMechanic = mechanic;
            }
        });

        // Table Service
        colSeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblService.setOnMouseClicked(event -> {
            Service service = tblService.getSelectionModel().getSelectedItem();
            if (service != null) {
                selectedService = service;
            }
        });

        // Table User
        colUId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colURole.setCellValueFactory(new PropertyValueFactory<>("role"));

        tblUser.setOnMouseClicked(event -> {
            User user = tblUser.getSelectionModel().getSelectedItem();
            if (user != null) {
                selectedUser = user;
            }
        });

        ObservableList<MenuItem> roles = FXCollections.observableArrayList("admin", "user").stream()
                .map(role -> {
                    MenuItem item = new MenuItem(role);
                    item.setOnAction(event -> mbURole.setText(role));
                    return item;
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));

        mbURole.getItems().addAll(roles);

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
            txOId.setText("Auto Generated");
        } else if (event.getSource() == btnOUpdate) {
            if (selectedOrder != null) {
                txOId.setText(String.valueOf(selectedOrder.getId()));
                txOTotalAmount.setText(String.valueOf(selectedOrder.getTotalAmount()));
                dpOOrderDate.setValue(selectedOrder.getOrderDate().toLocalDate());
                mbOClient.setText(selectedOrder.getClient());
                mbOStatus.setText(selectedOrder.getStatus());
                enableForm(2, true, false);
            } else {
                DialogUtil.showError("Please select an order first!");
            }
        } else if (event.getSource() == btnOSave) {
            if (txOId.getText().isEmpty() || txOTotalAmount.getText().isEmpty() || dpOOrderDate.getValue() == null || mbOClient.getText().isEmpty() || mbOStatus.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            Order order = new Order();
            order.setTotalAmount(Double.parseDouble(txOTotalAmount.getText()));
            order.setOrderDate(dpOOrderDate.getValue());
            order.setEventDate(dpOOrderDate.getValue());
            order.setClientId(clientRepository.findByName(mbOClient.getText()).getId());
            order.setStatus(Order.OrderStatus.valueOf(mbOStatus.getText().toUpperCase()));
            if (txOId.getText().equals("Auto Generated")) {
                orderRepository.save(order);
            } else {
                order.setId(Integer.parseInt(txOId.getText()));
                orderRepository.update(order);
            }
            DialogUtil.showInfo("Order saved successfully!");
            refreshTable(2);
            enableForm(2, false, true);
        } else if (event.getSource() == btnODelete) {
            if (selectedOrder != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this order?");
                if (confirm) {
                    orderRepository.deleteById(selectedOrder.getId());
                    DialogUtil.showInfo("Order deleted successfully!");
                    refreshTable(2);
                }
            } else {
                DialogUtil.showError("Please select an order first!");
            }
        } else if (event.getSource() == btnCNew) {
            enableForm(3, true, true);
            txCId.setText("Auto Generated");
        } else if (event.getSource() == btnCUpdate) {
            if (selectedClient != null) {
                txCId.setText(String.valueOf(selectedClient.getId()));
                txCName.setText(selectedClient.getName());
                txCPhone.setText(selectedClient.getPhone());
                txCEmail.setText(selectedClient.getEmail());
                txCAddress.setText(selectedClient.getAddress());
                enableForm(3, true, false);
            } else {
                DialogUtil.showError("Please select a client first!");
            }
        } else if (event.getSource() == btnCSave) {
            if (txCId.getText().isEmpty() || txCName.getText().isEmpty() || txCPhone.getText().isEmpty() || txCEmail.getText().isEmpty() || txCAddress.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            Client client = new Client();
            client.setName(txCName.getText());
            client.setPhone(txCPhone.getText());
            client.setEmail(txCEmail.getText());
            client.setAddress(txCAddress.getText());
            if (txCId.getText().equals("Auto Generated")) {
                clientRepository.save(client);
            } else {
                client.setId(Integer.parseInt(txCId.getText()));
                clientRepository.update(client);
            }
            DialogUtil.showInfo("Client saved successfully!");
            refreshTable(3);
            enableForm(3, false, true);
        } else if (event.getSource() == btnCDelete) {
            if (selectedClient != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this client?");
                if (confirm) {
                    clientRepository.deleteById(selectedClient.getId());
                    DialogUtil.showInfo("Client deleted successfully!");
                    refreshTable(3);
                }
            } else {
                DialogUtil.showError("Please select a client first!");
            }
        } else if (event.getSource() == btnSNew) {
            enableForm(4, true, true);
            txSId.setText("Auto Generated");
        } else if (event.getSource() == btnSUpdate) {
            if (selectedSupplier != null) {
                txSId.setText(String.valueOf(selectedSupplier.getId()));
                txSName.setText(selectedSupplier.getName());
                txSPhone.setText(selectedSupplier.getPhone());
                txSEmail.setText(selectedSupplier.getEmail());
                txSAddress.setText(selectedSupplier.getAddress());
                enableForm(4, true, false);
            } else {
                DialogUtil.showError("Please select a supplier first!");
            }
        } else if (event.getSource() == btnSSave) {
            if (txSId.getText().isEmpty() || txSName.getText().isEmpty() || txSPhone.getText().isEmpty() || txSEmail.getText().isEmpty() || txSAddress.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            Supplier supplier = new Supplier();
            supplier.setName(txSName.getText());
            supplier.setPhone(txSPhone.getText());
            supplier.setEmail(txSEmail.getText());
            supplier.setAddress(txSAddress.getText());
            if (txSId.getText().equals("Auto Generated")) {
                supplierRepository.save(supplier);
            } else {
                supplier.setId(Integer.parseInt(txSId.getText()));
                supplierRepository.update(supplier);
            }
            DialogUtil.showInfo("Supplier saved successfully!");
            refreshTable(4);
            enableForm(4, false, true);
        } else if (event.getSource() == btnSDelete) {
            if (selectedSupplier != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this supplier?");
                if (confirm) {
                    supplierRepository.deleteById(selectedSupplier.getId());
                    DialogUtil.showInfo("Supplier deleted successfully!");
                    refreshTable(4);
                }
            } else {
                DialogUtil.showError("Please select a supplier first!");
            }
        } else if (event.getSource() == btnMNew) {
            enableForm(5, true, true);
            txMId.setText("Auto Generated");
        } else if (event.getSource() == btnMUpdate) {
            if (selectedMechanic != null) {
                txMId.setText(String.valueOf(selectedMechanic.getId()));
                txMName.setText(selectedMechanic.getName());
                txMPhone.setText(selectedMechanic.getPhone());
                txMSpecialization.setText(selectedMechanic.getSpecialization());
                enableForm(5, true, false);
            } else {
                DialogUtil.showError("Please select a mechanic first!");
            }
        } else if (event.getSource() == btnMSave) {
            if (txMId.getText().isEmpty() || txMName.getText().isEmpty() || txMPhone.getText().isEmpty() || txMSpecialization.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            Mechanic mechanic = new Mechanic();
            mechanic.setName(txMName.getText());
            mechanic.setPhone(txMPhone.getText());
            mechanic.setSpecialization(txMSpecialization.getText());
            if (txMId.getText().equals("Auto Generated")) {
                mechanicRepository.save(mechanic);
            } else {
                mechanic.setId(Integer.parseInt(txMId.getText()));
                mechanicRepository.update(mechanic);
            }
            DialogUtil.showInfo("Mechanic saved successfully!");
            refreshTable(5);
            enableForm(5, false, true);
        } else if (event.getSource() == btnMDelete) {
            if (selectedMechanic != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this mechanic?");
                if (confirm) {
                    mechanicRepository.deleteById(selectedMechanic.getId());
                    DialogUtil.showInfo("Mechanic deleted successfully!");
                    refreshTable(5);
                }
            } else {
                DialogUtil.showError("Please select a mechanic first!");
            }
        } else if (event.getSource() == btnSeNew) {
            enableForm(6, true, true);
            txSeId.setText("Auto Generated");
        } else if (event.getSource() == btnSeUpdate) {
            if (selectedService != null) {
                txSeId.setText(String.valueOf(selectedService.getId()));
                txSeName.setText(selectedService.getName());
                txSeDescription.setText(selectedService.getDescription());
                txSePrice.setText(String.valueOf(selectedService.getPrice()));
                enableForm(6, true, false);
            } else {
                DialogUtil.showError("Please select a service first!");
            }
        } else if (event.getSource() == btnSeSave) {
            if (txSeId.getText().isEmpty() || txSeName.getText().isEmpty() || txSeDescription.getText().isEmpty() || txSePrice.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            Service service = new Service();
            service.setName(txSeName.getText());
            service.setDescription(txSeDescription.getText());
            service.setPrice(Double.parseDouble(txSePrice.getText()));
            if (txSeId.getText().equals("Auto Generated")) {
                serviceRepository.save(service);
            } else {
                service.setId(Integer.parseInt(txSeId.getText()));
                serviceRepository.update(service);
            }
            DialogUtil.showInfo("Service saved successfully!");
            refreshTable(6);
            enableForm(6, false, true);
        } else if (event.getSource() == btnSeDelete) {
            if (selectedService != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this service?");
                if (confirm) {
                    serviceRepository.deleteById(selectedService.getId());
                    DialogUtil.showInfo("Service deleted successfully!");
                    refreshTable(6);
                }
            } else {
                DialogUtil.showError("Please select a service first!");
            }
        } else if (event.getSource() == btnUNew) {
            enableForm(7, true, true);
            txUid.setText("Auto Generated");
        } else if (event.getSource() == btnUUpdate) {
            if (selectedUser != null) {
                txUid.setText(String.valueOf(selectedUser.getId()));
                txUUsername.setText(selectedUser.getUsername());
                txUPassword.setText(selectedUser.getPassword());
                mbURole.setText(selectedUser.getRole());
                enableForm(7, true, false);
            } else {
                DialogUtil.showError("Please select a user first!");
            }
        } else if (event.getSource() == btnUSave) {
            if (txUid.getText().isEmpty() || txUUsername.getText().isEmpty() || txUPassword.getText().isEmpty() || mbURole.getText().isEmpty()) {
                DialogUtil.showError("Please fill all fields!");
                return;
            }
            User user = new User();
            user.setUsername(txUUsername.getText());
            user.setPassword(txUPassword.getText());
            user.setRole(mbURole.getText().toLowerCase());
            if (txUid.getText().equals("Auto Generated")) {
                userRepository.save(user);
            } else {
                user.setId(Integer.parseInt(txUid.getText()));
                userRepository.update(user);
            }
            DialogUtil.showInfo("User saved successfully!");
            refreshTable(7);
            enableForm(7, false, true);
        } else if (event.getSource() == btnUDelete) {
            if (selectedUser != null) {
                boolean confirm = DialogUtil.showConfirm("Are you sure you want to delete this user?");
                if (confirm) {
                    userRepository.deleteById(selectedUser.getId());
                    DialogUtil.showInfo("User deleted successfully!");
                    refreshTable(7);
                }
            } else {
                DialogUtil.showError("Please select a user first!");
            }
        }
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == btnClient) {
            setActiveButton(btnClient);
            pnClient.toFront();
            refreshTable(3);
            enableForm(3, false, true);
        } else if (event.getSource() == btnHome) {
            setActiveButton(btnHome);
            pnHome.toFront();
            refreshTable(1);
        } else if (event.getSource() == btnMechanic) {
            setActiveButton(btnMechanic);
            pnMechanic.toFront();
            refreshTable(5);
            enableForm(5, false, true);
        } else if (event.getSource() == btnOrder) {
            setActiveButton(btnOrder);
            pnOrder.toFront();
            refreshTable(2);
            enableForm(2, false, true);
        } else if (event.getSource() == btnService) {
            setActiveButton(btnService);
            pnService.toFront();
            refreshTable(6);
            enableForm(6, false, true);
        } else if (event.getSource() == btnSupplier) {
            setActiveButton(btnSupplier);
            pnSupplier.toFront();
            refreshTable(4);
            enableForm(4, false, true);
        } else if (event.getSource() == btnUser) {
            setActiveButton(btnUser);
            pnUser.toFront();
            refreshTable(7);
            enableForm(7, false, true);
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
                ObservableList<OrderDetailsDTO> report = FXCollections.observableArrayList(orderDetailRepository.findAllOrderDetails());
                tblReport.setItems(report);
                break;
            case 2:
                ObservableList<OrderDTO> order = FXCollections.observableArrayList(orderRepository.findAllOrders());
                tblOrder.setItems(order);
                selectedOrder = null;
                break;
            case 3:
                ObservableList<Client> client = FXCollections.observableArrayList(clientRepository.findAll());
                tblClient.setItems(client);
                selectedClient = null;
                break;
            case 4:
                ObservableList<Supplier> supplier = FXCollections.observableArrayList(supplierRepository.findAll());
                tblSupplier.setItems(supplier);
                selectedSupplier = null;
                break;
            case 5:
                ObservableList<Mechanic> mechanic = FXCollections.observableArrayList(mechanicRepository.findAll());
                tblMechanic.setItems(mechanic);
                selectedMechanic = null;
                break;
            case 6:
                ObservableList<Service> service = FXCollections.observableArrayList(serviceRepository.findAll());
                tblService.setItems(service);
                selectedService = null;
                break;
            case 7:
                ObservableList<User> user = FXCollections.observableArrayList(userRepository.findAll());
                tblUser.setItems(user);
                selectedUser = null;
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
            case 3:
                txCName.setDisable(!enable);
                txCPhone.setDisable(!enable);
                txCEmail.setDisable(!enable);
                txCAddress.setDisable(!enable);
                btnCSave.setDisable(!enable);

                if (clear) {
                    txCId.clear();
                    txCName.clear();
                    txCPhone.clear();
                    txCEmail.clear();
                    txCAddress.clear();
                }
                break;
            case 4:
                txSName.setDisable(!enable);
                txSPhone.setDisable(!enable);
                txSEmail.setDisable(!enable);
                txSAddress.setDisable(!enable);
                btnSSave.setDisable(!enable);

                if (clear) {
                    txSId.clear();
                    txSName.clear();
                    txSPhone.clear();
                    txSEmail.clear();
                    txSAddress.clear();
                }
                break;
            case 5:
                txMName.setDisable(!enable);
                txMPhone.setDisable(!enable);
                txMSpecialization.setDisable(!enable);
                btnMSave.setDisable(!enable);

                if (clear) {
                    txMId.clear();
                    txMName.clear();
                    txMPhone.clear();
                    txMSpecialization.clear();
                }
                break;
            case 6:
                txSeName.setDisable(!enable);
                txSeDescription.setDisable(!enable);
                txSePrice.setDisable(!enable);
                btnSeSave.setDisable(!enable);

                if (clear) {
                    txSeId.clear();
                    txSeName.clear();
                    txSeDescription.clear();
                    txSePrice.clear();
                }
                break;
            case 7:
                txUUsername.setDisable(!enable);
                txUPassword.setDisable(!enable);
                mbURole.setDisable(!enable);
                btnUSave.setDisable(!enable);

                if (clear) {
                    txUid.clear();
                    txUUsername.clear();
                    txUPassword.clear();
                    mbURole.setText("");
                }
                break;
            default:
                break;
        }
    }

}
