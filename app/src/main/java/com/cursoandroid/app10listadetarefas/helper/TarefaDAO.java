package com.cursoandroid.app10listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cursoandroid.app10listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private final SQLiteDatabase escrever;
    private final SQLiteDatabase ler;
    public TarefaDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            escrever.insert(DBHelper.TABELA_TAREFAS, null, cv);
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try {
            escrever.update(DBHelper.TABELA_TAREFAS, cv, "id=?",new String[]{tarefa.getId().toString()});
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(Tarefa tarefa) {
        try {
            escrever.delete(DBHelper.TABELA_TAREFAS, "id=?",new String[]{tarefa.getId().toString()});
        }catch (Exception e){
            Log.e("INFO DB", "Erro ao excluir tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefaList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = ler.rawQuery(query, null);
        while (c.moveToNext()){

            int indexId = c.getColumnIndex("id");
            int indexNome = c.getColumnIndex("nome");
            Tarefa tarefa = new Tarefa();
            tarefa.setId(c.getLong(indexId));
            tarefa.setNomeTarefa(c.getString(indexNome));

            tarefaList.add(tarefa);
        }
        c.close();
        return tarefaList;
    }
}
