package br.edu.utfpr.template;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.dto.PaisDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;

@Log
public abstract class TemplatePaisDAO {

    abstract String ConnectionString();
    abstract String insertString();
    abstract String selectString();
    abstract List<Object> resultHandler(ResultSet result);
    abstract String deleteString();
    ;abstract String updateString();
    abstract PreparedStatement  deleteStatement( PreparedStatement st, int id);
    abstract PreparedStatement insertStatement( PreparedStatement st, Object obj);
    abstract PreparedStatement updateStatement( PreparedStatement st, Object obj);

    final boolean inserir(Object obj) {
        try ( Connection conn = DriverManager.getConnection(ConnectionString())) {

            PreparedStatement statement = conn.prepareStatement(insertString());
            statement = insertStatement(statement, obj);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    final List<Object> listarTodos() {

        List<Object> resultado = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection( ConnectionString())) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(selectString());

            resultado = resultHandler(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    final boolean excluir(int id) {

        try ( Connection conn = DriverManager.getConnection(ConnectionString())) {

            PreparedStatement statement = conn.prepareStatement(deleteString());
            statement = deleteStatement(statement, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    final boolean alterar(Object obj) {
        try ( Connection conn = DriverManager.getConnection(ConnectionString())) {

            PreparedStatement statement = conn.prepareStatement(updateString());
            statement = updateStatement( statement, obj);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //final Object listarPorId (int id) {
       // return this.listarTodos().stream().filter(p -> p.getId() == id).findAny().orElseThrow(RuntimeException::new);
    //}

}
