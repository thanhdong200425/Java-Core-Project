module com.do_an_main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.mail;




    opens com.do_an_main to javafx.fxml;
    exports com.do_an_main;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to javafx.fxml;
    exports Model;
    opens DAO to javafx.fxml;
    exports DAO;
    exports Controller.Custom;
    opens Controller.Custom to javafx.fxml;
    exports Controller.Rank;
    opens Controller.Rank to javafx.fxml;
    exports Controller.Login;
    opens Controller.Login to javafx.fxml;
    exports Controller.Result;
    opens Controller.Result to javafx.fxml;
    exports Controller.Statistical;
    opens Controller.Statistical to javafx.fxml;
}