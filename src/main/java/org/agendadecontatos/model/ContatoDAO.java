package org.agendadecontatos.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private static final String ARQUIVO = "src/main/java/org/agendadecontatos/utils/contatos.csv";

    public class TesteContato {
        public static void main(String[] args) {
            ContatoDAO dao = new ContatoDAO();
            Contato c = new Contato(dao.gerarProximoId(), "Luana", "62999999999", "luana@email.com");
            dao.salvarContato(c);
            System.out.println("Contato salvo com sucesso!");
        }
    }


    // Gera o próximo ID com base no maior ID existente no CSV
    public int gerarProximoId() {
        List<Contato> contatos = listarContatos();
        if (contatos.isEmpty()) {
            return 1;
        }
        int maiorId = 0;
        for (Contato c : contatos) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }
        return maiorId + 1;
    }

    // Salva um novo contato no arquivo CSV
    public void salvarContato(Contato contato) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO),
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(contato.getId() + "," + contato.getNome() + "," + contato.getTelefone() + "," + contato.getEmail());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lista todos os contatos do CSV
    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String telefone = dados[2];
                    String email = dados[3];
                    contatos.add(new Contato(id, nome, telefone, email));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe
        }
        return contatos;
    }

    // Atualiza um contato existente com base no ID
    public void atualizarContato(Contato contatoAtualizado) {
        List<Contato> contatos = listarContatos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO))) {
            for (Contato c : contatos) {
                if (c.getId() == contatoAtualizado.getId()) {
                    writer.write(contatoAtualizado.getId() + "," + contatoAtualizado.getNome() + "," + contatoAtualizado.getTelefone() + "," + contatoAtualizado.getEmail());
                } else {
                    writer.write(c.getId() + "," + c.getNome() + "," + c.getTelefone() + "," + c.getEmail());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exclui um contato pelo ID
    public void excluirContato(int id) {
        List<Contato> contatos = listarContatos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO))) {
            for (Contato c : contatos) {
                if (c.getId() != id) {
                    writer.write(c.getId() + "," + c.getNome() + "," + c.getTelefone() + "," + c.getEmail());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

