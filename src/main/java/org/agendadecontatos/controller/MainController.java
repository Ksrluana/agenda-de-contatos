package org.agendadecontatos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.agendadecontatos.model.Contato;
import org.agendadecontatos.model.ContatoDAO;

public class MainController {

    @FXML private TextField inputNome;
    @FXML private TextField inputTelefone;
    @FXML private TextField inputEmail;
    @FXML private TableView<Contato> tabelaContatos;
    @FXML private TableColumn<Contato, String> colNome;
    @FXML private TableColumn<Contato, String> colTel;
    @FXML private TableColumn<Contato, String> colEmail;

    private ContatoDAO dao = new ContatoDAO();
    private ObservableList<Contato> listaContatos;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colTel.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefone()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));

        carregarContatos();

        tabelaContatos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                inputNome.setText(newSel.getNome());
                inputTelefone.setText(newSel.getTelefone());
                inputEmail.setText(newSel.getEmail());
            }
        });
    }

    private void carregarContatos() {
        listaContatos = FXCollections.observableArrayList(dao.listarContatos());
        tabelaContatos.setItems(listaContatos);
    }

    @FXML
    private void salvarContato() {
        if (inputNome.getText().isEmpty() || inputTelefone.getText().isEmpty() || inputEmail.getText().isEmpty()) return;

        Contato novo = new Contato(dao.gerarProximoId(),
                inputNome.getText(),
                inputTelefone.getText(),
                inputEmail.getText());
        dao.salvarContato(novo);
        limparCampos();
        carregarContatos();
    }

    @FXML
    private void editarContato() {
        Contato selecionado = tabelaContatos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        Contato editado = new Contato(selecionado.getId(),
                inputNome.getText(),
                inputTelefone.getText(),
                inputEmail.getText());
        dao.atualizarContato(editado);
        limparCampos();
        carregarContatos();
    }

    @FXML
    private void removerContato() {
        Contato selecionado = tabelaContatos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        dao.excluirContato(selecionado.getId());
        limparCampos();
        carregarContatos();
    }

    private void limparCampos() {
        inputNome.clear();
        inputTelefone.clear();
        inputEmail.clear();
        tabelaContatos.getSelectionModel().clearSelection();
    }
}
