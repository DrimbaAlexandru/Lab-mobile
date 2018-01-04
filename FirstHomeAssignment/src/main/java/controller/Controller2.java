package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cursa;
import model.Motociclist;
import service.GeneralService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Xoxii on 30-Mar-17.
 */
public class Controller2 implements Observer{
    private GeneralService service;
    private ObservableList<Cursa> obscursa = FXCollections.observableArrayList();
    private ObservableList<Motociclist> obsmotociclist = FXCollections.observableArrayList();

    public Controller2(GeneralService g) { this.service = g; }

    @FXML
    public TableView<Cursa> tabelCurse;
    @FXML
    public TableColumn<Cursa, String> columnCursa;
    @FXML
    public TableColumn<Cursa, String> columnCapacitateGenerala;
    @FXML
    public TableColumn<Cursa, String> columnNrParticipanti;
    @FXML
    public TableView<Motociclist> tabelMotociclisti;
    @FXML
    public TableColumn<Motociclist, String> columnIDMotociclist;
    @FXML
    public TableColumn<Motociclist, String> columnNume;
    @FXML
    public TableColumn<Motociclist, String> columnCapacitateMotor;
    @FXML
    public TableColumn<Motociclist, String> columnEchipa;

    public void initialize()
    {
        tabelCurse.setItems(obscursa);
        columnCursa.setCellValueFactory(new PropertyValueFactory<Cursa, String>("IDCursa"));
        columnCapacitateGenerala.setCellValueFactory(new PropertyValueFactory<Cursa, String>("Capacitate_Generala"));
        columnNrParticipanti.setCellValueFactory(new PropertyValueFactory<Cursa, String>("Nr_Participanti"));

        tabelMotociclisti.setItems(obsmotociclist);
        columnIDMotociclist.setCellValueFactory(new PropertyValueFactory<Motociclist, String>("idmotociclist"));
        columnNume.setCellValueFactory(new PropertyValueFactory<Motociclist, String>("numemotociclist"));
        columnCapacitateMotor.setCellValueFactory(new PropertyValueFactory<Motociclist, String>("capacitate_motor"));
        columnEchipa.setCellValueFactory(new PropertyValueFactory<Motociclist, String>("echipa"));
        update(null, null);
    }


    public void updateCursa(List<Cursa> curse)
    {
        obscursa.clear();
        for(Cursa c : curse)
            obscursa.add(c);
    }

    public void updateMotociclist(List<Motociclist> motociclisti)
    {
        obsmotociclist.clear();
        for (Motociclist m : motociclisti)
            obsmotociclist.add(m);
    }

    public void update(Observable obs, Object src)
    {
        updateCursa(service.findAllCursa());
        updateMotociclist(service.findAllMotociclist());
    }
}
